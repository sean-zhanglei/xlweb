package com.nbug.module.system.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.utils.RestTemplateUtil;
import com.nbug.mico.common.utils.redis.RedisUtil;
import com.nbug.mico.common.vo.LogisticsQueryVo;
import com.nbug.mico.common.vo.LogisticsResultListVo;
import com.nbug.mico.common.vo.LogisticsResultVo;
import com.nbug.module.system.api.config.ConfigApi;
import com.nbug.module.system.service.LogisticService;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
* ExpressServiceImpl 接口实现

*/
@Data
@Service
public class LogisticsServiceImpl implements LogisticService {

    @Autowired
    private ConfigApi configApi;

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Autowired
    private RedisUtil redisUtil;

    private String redisKey = Constants.LOGISTICS_KEY;
    private Long redisCacheSeconds = 1800L;

    private String expressNo;


    /** 快递
     * @param expressNo String 物流单号
     * @param type String 快递公司字母简写：不知道可不填 95%能自动识别，填写查询速度会更快 https://market.aliyun.com/products/56928004/cmapi021863.html#sku=yuncode15863000015
     * @param com 快递公司编号
     * @author Mr.Zhang
     * @since 2020-06-10
     * @return Express
     */
    @Override
    public LogisticsResultVo info(String expressNo, String type, String com, String phone) {
        LogisticsResultVo resultVo = new LogisticsResultVo();
        setExpressNo(expressNo);
        JSONObject result = getCache();
        if (ObjectUtil.isNotNull(result)) {
            return JSONObject.toJavaObject(result, LogisticsResultVo.class);
        }
        String logisticsType = configApi.getValueByKeyException("logistics_type").getCheckedData();
        if ("2".equals(logisticsType)) {// 阿里云查询
            String appCode = configApi.getValueByKey(Constants.CONFIG_KEY_LOGISTICS_APP_CODE).getCheckedData();

            // 顺丰请输入单号 : 收件人或寄件人手机号后四位。例如：123456789:1234
            if (StrUtil.isNotBlank(com) && "shunfengkuaiyun".equals(com)) {
                expressNo = expressNo.concat(":").concat(StrUtil.sub(phone, 7, -1));
            }
            String url = Constants.LOGISTICS_API_URL + "?no=" + expressNo;
            if(StringUtils.isNotBlank(type)){
                url += "&type=" + type;
            }

            HashMap<String, String> header = new HashMap<>();
            header.put("Authorization", "APPCODE " + appCode);

            JSONObject data = restTemplateUtil.getData(url, header);
            checkResult(data);
            //把数据解析成对象返回到前端
            result = data.getJSONObject("result");
            saveCache(result);
            resultVo = JSONObject.toJavaObject(result, LogisticsResultVo.class);
        }
        return resultVo;
    }

    /**
     * vo转公共返回vo
     */
    private LogisticsResultVo queryToResultVo(LogisticsQueryVo queryVo) {
        LogisticsResultVo resultVo = new LogisticsResultVo();
        resultVo.setNumber(queryVo.getNum());
        resultVo.setExpName(queryVo.getCom());
        resultVo.setIsSign(queryVo.getIscheck());
        resultVo.setDeliveryStatus(queryVo.getStatus());

        if (CollUtil.isNotEmpty(queryVo.getContent())) {
            List<LogisticsResultListVo> list = CollUtil.newArrayList();
            queryVo.getContent().forEach(i -> {
                LogisticsResultListVo listVo = new LogisticsResultListVo();
                listVo.setTime(i.getTime());
                listVo.setStatus(i.getStatus());
                list.add(listVo);
            });
            resultVo.setList(list);
        }
        return resultVo;
    }

    /** 获取快递缓存
     * @author Mr.Zhang
     * @since 2020-07-06
     * @return JSONObject
     */
    private JSONObject getCache() {
        Object data = redisUtil.get(getRedisKey() + getExpressNo());
        if(null != data){
         return JSONObject.parseObject(data.toString());
        }
        return null;
    }

    /** 获取快递缓存
     * @param data JSONObject 需要保存的数据
     * @author Mr.Zhang
     * @since 2020-07-06
     */
    private void saveCache(JSONObject data) {
        redisUtil.set(getRedisKey() + getExpressNo(), data.toJSONString(), getRedisCacheSeconds(), TimeUnit.SECONDS);
    }

    /** 获取快递缓存
     * @param data JSONObject 检测返回的结果
     * @author Mr.Zhang
     * @since 2020-07-06
     */
    private void checkResult(JSONObject data) {
        if (!"0".equals(data.getString("status"))){
            throw new XlwebException(data.getString("msg"));
        }
    }
}

