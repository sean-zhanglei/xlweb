package com.nbug.module.infra.util;


import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.constants.YlyConstants;
import com.nbug.mico.common.enums.EnumYly;
import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.request.YlyPrintRequest;
import com.nbug.mico.common.request.YlyPrintRequestGoods;
import com.nbug.mico.common.response.YlyAccessTokenResponse;
import com.nbug.mico.common.utils.redis.RedisUtil;
import com.nbug.module.infra.util.yly.RequestMethod;
import com.nbug.module.system.api.config.ConfigApi;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeUnit;


/** 易联云 工具类
 **/
@Component
public class YlyUtil {
    private static final Logger logger = LoggerFactory.getLogger(YlyUtil.class);

    private YlyAccessTokenResponse ylyAccessTokenResponse;

    @Autowired
    private ConfigApi configApi;

    @Autowired
    private RedisUtil redisUtil;

    // 易联云颁发给开发者的应用ID
    private static String client_id = "";
    // 易联云颁发给开发者的应用密钥
    private static String client_secret = "";
    // 易联云打印机设备唯一串码
    private static String machine_code = "";
    // 易联云打印机终端密钥
    private static String msign = "";
    // 是否开启打印
    private static String status = "";

    /**
     * 初始化易联云打印机并链接
     * 添加打印机
     * 参数：* @param machine_code 易联云打印机终端号
     *      * @param msign 易联云打印机终端密钥
     *      * @param access_token 授权的token 必要参数，有效时间35天
     */
    public void instant() {
        try {
            // 获取Access Token
            boolean exists = redisUtil.exists(YlyConstants.YLY_REDIS_TOKEN);
            if(exists) {
                Object o = redisUtil.get(YlyConstants.YLY_REDIS_TOKEN);
                ylyAccessTokenResponse = JSON.parseObject(o.toString(), YlyAccessTokenResponse.class);
            }
            if(ObjectUtil.isNotNull(ylyAccessTokenResponse) && StringUtils.isNotBlank(ylyAccessTokenResponse.getBody().getAccess_token())){
                return;
            }

            client_id = configApi.getValueByKey(YlyConstants.YLY_PRINT_APP_ID).getCheckedData();
            client_secret = configApi.getValueByKey(YlyConstants.YLY_PRINT_APP_SECRET).getCheckedData();
            machine_code = configApi.getValueByKey(YlyConstants.YLY_PRINT_APP_MACHINE_CODE).getCheckedData();
            msign = configApi.getValueByKey(YlyConstants.YLY_PRINT_APP_MACHINE_MSIGN).getCheckedData();
            status = configApi.getValueByKey(YlyConstants.YLY_PRINT_STATUS).getCheckedData();
            if(StringUtils.isBlank(client_id) || StringUtils.isBlank(client_secret)
                    || StringUtils.isBlank(machine_code) || StringUtils.isBlank(msign)){
                throw new XlwebException("易联云配置数据不完整");
            }
            if(StringUtils.isBlank(status) || "false".equals(status)){
                return;
            }
            // 初始化易联云
            RequestMethod.init(client_id,client_secret);

            ylyAccessTokenResponse = JSON.parseObject(RequestMethod.getAccessToken(),YlyAccessTokenResponse.class);
            redisUtil.set(YlyConstants.YLY_REDIS_TOKEN,JSON.toJSONString(ylyAccessTokenResponse),30L, TimeUnit.DAYS);

            logger.info("获取的易联云AccessToken:"+JSON.toJSONString(ylyAccessTokenResponse));
            String addedPrint = RequestMethod.getInstance().addPrinter(machine_code, msign, ylyAccessTokenResponse.getBody().getAccess_token());
            logger.info("添加打印机结果:"+addedPrint);

            // 设置语音aid = 0
            ylyVoice();
            // 设置声音
            ylySetSound(EnumYly.VOLUME_RESPONSE_TYPE_HORN.getCode(),
                    EnumYly.VOLUME_RESPONSE_VOICE3.getCode());

            ylyIcon();
        } catch (Exception e){
            ylyAccessTokenResponse = null;
            redisUtil.delete(YlyConstants.YLY_REDIS_TOKEN);
            logger.error("添加易联云打印机失败: {}", e.getMessage());
            logger.error(String.format("易联云 配置参数 client_id=%s client_secret=%s machine_code=%s msign=%s",client_id,client_secret,machine_code,msign));
        }

    }

    /**
     * 设置内置语音接口 设置了易联云也不会播放 暂时停用
     *        注意: 仅支持K4-WA、K4-GAD、K4-WGEAD、k6型号（除k6-wh外）
     *        RequestMethod.getInstance().printerSetVoice(String access_token,String machine_code,String content,String is_file,String aid,String origin_id)
     *        参数：* @param access_token 授权的token 必要参数
     *             * @param machine_code 易联云打印机终端号
     *             * @param content 播报内容 , 音量(1~9) , 声音类型(0,1,3,4) 组成json ! 示例 ["测试",9,0] 或者是在线语音链接! 语音内容请小于24kb
     *             * @param is_file true or false , 判断content是否为在线语音链接，格式MP3
     *             * @param aid 0~9 , 定义需设置的语音编号,若不提交,默认升序
     *             * @param origin_id 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母 ，且在同一个client_id下唯一。详见商户订单号
     */
    public void ylyVoice() throws Exception {
        instant();
        RequestMethod.getInstance().printerSetVoice(
                ylyAccessTokenResponse.getBody().getAccess_token(),
                machine_code,"[\"来新单了\",9,0]","false",
                "0");
        logger.info("设置语音成功");
    }

    /**
     * 设置logo接口
     * @throws Exception
     */
    public void ylyIcon() throws Exception {
        instant();
        String logoUrl = configApi.getValueByKeyException(Constants.CONFIG_KEY_SITE_URL).getCheckedData() + "/" + configApi.getValueByKeyException("mobile_login_logo").getCheckedData();
        RequestMethod.getInstance().printSetIcon(
                ylyAccessTokenResponse.getBody().getAccess_token(),
                machine_code, logoUrl);
        logger.info("设置logo成功");
    }


    /**
     * 声音调节接口
     *        RequestMethod.getInstance().printSetSound(String access_token,String machine_code,String response_type,String voice)
     *        参数：* @param access_token 授权的token 必要参数
     *             * @param machine_code 易联云打印机终端号
     *             * @param response_type 蜂鸣器:buzzer,喇叭:horn
     *             * @param voice [0,1,2,3] 4种音量设置
     */
    public void ylySetSound(String responseType,String volume) throws Exception {
        instant();
        RequestMethod.getInstance().printSetSound(ylyAccessTokenResponse.getBody().getAccess_token(),
                machine_code,responseType,volume);
        logger.info("设置音量成功");
    }

    /**
     * 取消所有未打印订单
     * RequestMethod.getInstance().printCancelAll(String access_token,String machine_code)
     * 参数：* @param access_token 授权的token 必要参数
     * * @param machine_code 易联云打印机终端号
     */
    /*public void ylyCancelAll() throws Exception {
        instant();
        String cancelAllPrint = RequestMethod.getInstance().printCancelAll(ylyAccessTokenResponse.getBody().getAccess_token(), machine_code);
        logger.info("取消掉所有打印订单"+cancelAllPrint);
    }*/


    /**
     * 文本打印
     * 参数：* @param access_token 授权的token 必要参数
     *      * @param machine_code 易联云打印机终端号
     *      * @param content 打印内容(需要urlencode)，排版指令详见打印机指令
     *      * @param origin_id 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母 ，且在同一个client_id下唯一。详见商户订单号
     *   String printContent = "一段美好的文字";
     * @throws Exception 打印方式
     */
    public void ylyPrint(YlyPrintRequest ylyPrintRequest) throws Exception {
        instant();
        String orderDetailUrlH5 = configApi.getValueByKeyException(Constants.CONFIG_KEY_SITE_URL).getCheckedData() + "/#/pages/order_details/index?order_id=" + ylyPrintRequest.getOrderNo();
        String printSb = "<ML>1</ML><VN>0</VN>\n" +
                "<FH><FB><center>"+ylyPrintRequest.getBusinessName()+"</center></FB></FH>" +
                "********************************<FH>" +
                "订单编号：" + ylyPrintRequest.getOrderNo()+"\n"+
                "日   期：" + ylyPrintRequest.getDate()+"\n"+
                "姓   名：" + ylyPrintRequest.getName()+"\n"+
                "电   话：" + ylyPrintRequest.getPhone()+"\n"+
                "地   址：" + ylyPrintRequest.getAddress()+"\n"+
                "配送方式：" + (ylyPrintRequest.getShippingType() == 1 ? "商家配送" : "门店自提") +"\n"+
                (ylyPrintRequest.getShippingType() == 1 ? "配送时间：" : "自提时间：") + (ylyPrintRequest.getShippingType() == 1 ? ylyPrintRequest.getDeliveryTime() : ylyPrintRequest.getPickupTime()) + "\n"+
                "订单备注："+ ylyPrintRequest.getNote()+"</FH>\n" +
                "********************************\n" +
                "<FH>" +
                "商品名称 单价 数量 金额\n" +
                "" + ylyPrintFormatGoodsList(ylyPrintRequest.getGoods())+
                "</FH>" +
                "********************************\n" +
                "<FH>" +
                "<LR>合计：¥"+ ylyPrintRequest.getAmount()+"元，优惠：¥"+ylyPrintRequest.getDiscount()+"元</LR>" +
                "<LR>邮费：¥"+ylyPrintRequest.getPostal()+"元，抵扣：¥"+ylyPrintRequest.getDeduction()+"元</LR>" +
                "</FH>" +
                "<FH><right>实际支付：¥"+ylyPrintRequest.getPayMoney()+"元</right></FH>" +
//                "<FH>" +
//                "********************************\n" +
//                "</FH>" +
//                "<center>扫码查看订单详情</center>" +
//                "<center><QR>"+orderDetailUrlH5+"</QR></center>" +
                "<FB><FB><center>完</center></FB></FB>";
        RequestMethod.getInstance().printIndex(ylyAccessTokenResponse.getBody().getAccess_token(),machine_code,
                URLEncoder.encode(printSb, "utf-8"), ylyPrintRequest.getOrderNo());
    }

    /**
     * 付款成后打印易联云订单
     */
    public boolean checkYlyPrintAfterPaySuccess(){
        String printAuto = configApi.getValueByKey(YlyConstants.YLY_PRINT_AUTO_STATUS).getCheckedData();
        return !StringUtils.isBlank(printAuto) && "'0'".equals(printAuto);
    }

    /**
     * 检查是否开启打印
     */
    public boolean checkYlyPrintStatus(){
        String printAuto = configApi.getValueByKey(YlyConstants.YLY_PRINT_STATUS).getCheckedData();
        return !StringUtils.isBlank(printAuto) && "'0'".equals(printAuto);
    }

    /**
     * 格式化商品详情打印格式
     * @param goods 待格式化的商品详情
     * @return 格式化后的商品详情
     */
    public String ylyPrintFormatGoodsList(List<YlyPrintRequestGoods> goods){
        StringBuilder printGoodsString = new StringBuilder();
        for (YlyPrintRequestGoods goood : goods) {
            printGoodsString.append(goood.getGoodsName());
            printGoodsString.append(" ").append(goood.getUnitPrice());
            printGoodsString.append(" ").append(goood.getNum());
            printGoodsString.append(" ").append(goood.getMoney()).append("\n");
        }
        return printGoodsString.toString();
    }

    public static void main(String[] args) throws Exception {
//        YlyUtil ylyUtil = new YlyUtil();
//        ylyUtil.instant();
//        ylyUtil.ylyVoice();
//        // 响应类型 蜂鸣器:buzzer,喇叭:horn
//        // 音量大小 【1234】
//        ylyUtil.ylySetSound(EnumYly.VOLUME_RESPONSE_TYPE_HORN.getCode(),
//                EnumYly.VOLUME_RESPONSE_VOICE3.getCode());
//        ylyUtil.ylyVoice();


        // 根据商品对象打印商品信息
//        List<YlyPrintRequestGoods> goods = new ArrayList<>();
//        YlyPrintRequestGoods g1 = new YlyPrintRequestGoods("红轴的机械键盘","110","1","110");
//        YlyPrintRequestGoods g2 = new YlyPrintRequestGoods("新版的Iphone18 工程机 侧面带滑轮的那种","9999","1","9999");
//        goods.add(g1);
//        goods.add(g2);
//        YlyPrintRequest ylyPrintRequest = new YlyPrintRequest();
//        ylyPrintRequest.setBusinessName("XLWEB Java Order");
//        ylyPrintRequest.setOrderNo("Order110");
//        ylyPrintRequest.setDate("20211127");
////        ylyPrintRequest.setTime("12:00:00");
//        ylyPrintRequest.setName("大粽子");
//        ylyPrintRequest.setPhone("18292417675");
//        ylyPrintRequest.setAddress("陕西省 西安市 雁塔区 春林东街");
//        ylyPrintRequest.setNote("死鬼 来的是否先打电话");
//
//        ylyPrintRequest.setGoods(goods);
//        ylyPrintRequest.setAmount("10109");
//        ylyPrintRequest.setDiscount("100");
//        ylyPrintRequest.setPostal("0");
//        ylyPrintRequest.setDeduction("9");
//        ylyPrintRequest.setPayMoney("10000");
//        // 执行打印
//        ylyUtil.ylyPrint(ylyPrintRequest);

        // 取消多有待打印订单 根据需求调用
//        ylyUtil.ylyCancelAll();

    }
}
