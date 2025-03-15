package com.nbug.module.infra.service.excel.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageInfo;
import com.nbug.mico.common.config.XlwebConfig;
import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreBargainSearchRequest;
import com.nbug.mico.common.request.StoreCombinationSearchRequest;
import com.nbug.mico.common.request.StoreOrderSearchRequest;
import com.nbug.mico.common.request.StoreProductSearchRequest;
import com.nbug.mico.common.response.StoreBargainResponse;
import com.nbug.mico.common.response.StoreCombinationResponse;
import com.nbug.mico.common.response.StoreOrderDetailResponse;
import com.nbug.mico.common.response.StoreProductResponse;
import com.nbug.mico.common.utils.ExportUtil;
import com.nbug.mico.common.utils.XlwebUtil;
import com.nbug.mico.common.utils.date.DateUtil;
import com.nbug.mico.common.vo.BargainProductExcelVo;
import com.nbug.mico.common.vo.CombinationProductExcelVo;
import com.nbug.mico.common.vo.OrderExcelVo;
import com.nbug.mico.common.vo.ProductExcelVo;
import com.nbug.module.infra.service.excel.ExcelService;
import com.nbug.module.store.api.storeBargain.StoreBargainApi;
import com.nbug.module.store.api.storeCombination.StoreCombinationApi;
import com.nbug.module.store.api.storeOrder.StoreOrderApi;
import com.nbug.module.store.api.storeProduct.StoreProductApi;
import com.nbug.module.system.api.category.CategoryApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
*  ExcelServiceImpl 接口实现

*/
@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private StoreProductApi storeProductApi;

    @Autowired
    private CategoryApi categoryApi;

    @Autowired
    private StoreBargainApi storeBargainApi;

    @Autowired
    private StoreCombinationApi storeCombinationApi;

    @Autowired
    private StoreOrderApi storeOrderApi;

    @Autowired
    private XlwebConfig xlwebConfig;

    /**
     * 导出砍价商品
     * @param request 请求参数
     * @return 导出地址
     */
    @Override
    public String exportBargainProduct(StoreBargainSearchRequest request) {
        PageParamRequest pageParamRequest = new PageParamRequest();
        pageParamRequest.setPage(Constants.DEFAULT_PAGE);
        pageParamRequest.setLimit(Constants.EXPORT_MAX_LIMIT);
        PageInfo<StoreBargainResponse> page = storeBargainApi.getList(request, pageParamRequest).getCheckedData();
        if (CollUtil.isEmpty(page.getList())) throw new XlwebException("没有可导出的数据!");
        List<StoreBargainResponse> list = page.getList();
        List<BargainProductExcelVo> voList = list.stream().map(temp -> {
            BargainProductExcelVo vo = new BargainProductExcelVo();
            BeanUtils.copyProperties(temp, vo);
            vo.setPrice("￥".concat(temp.getPrice().toString()));
            vo.setStatus(temp.getStatus() ? "开启" : "关闭");
            vo.setStartTime(temp.getStartTime());
            vo.setStopTime(temp.getStopTime());
            vo.setAddTime(temp.getAddTime());
            return vo;
        }).collect(Collectors.toList());

        // 上传设置
        ExportUtil.setUpload(xlwebConfig.getImagePath(), Constants.UPLOAD_MODEL_PATH_EXCEL, Constants.UPLOAD_TYPE_FILE);

        // 文件名
        String fileName = "砍价".concat(DateUtil.nowDateTime(Constants.DATE_TIME_FORMAT_NUM)).concat(XlwebUtil.randomCount(111111111, 999999999).toString()).concat(".xlsx");

        //自定义标题别名
        LinkedHashMap<String, String> aliasMap = new LinkedHashMap<>();
        aliasMap.put("title", "砍价活动名称");
        aliasMap.put("info", "砍价活动简介");
        aliasMap.put("price", "砍价金额");
        aliasMap.put("bargainNum", "用户每次砍价的次数");
        aliasMap.put("status", "砍价状态");
        aliasMap.put("startTime", "砍价开启时间");
        aliasMap.put("stopTime", "砍价结束时间");
        aliasMap.put("sales", "销量");
        aliasMap.put("quotaShow", "库存");
        aliasMap.put("giveIntegral", "返多少积分");
        aliasMap.put("addTime", "添加时间");

        return ExportUtil.exportExecl(fileName, "砍价商品导出", voList, aliasMap);
    }

    /**
     * 导出拼团商品
     * @param request 请求参数
     * @return 导出地址
     */
    @Override
    public String exportCombinationProduct(StoreCombinationSearchRequest request) {
        PageParamRequest pageParamRequest = new PageParamRequest();
        pageParamRequest.setPage(Constants.DEFAULT_PAGE);
        pageParamRequest.setLimit(Constants.EXPORT_MAX_LIMIT);
        PageInfo<StoreCombinationResponse> page = storeCombinationApi.getList(request, pageParamRequest).getCheckedData();
        if (CollUtil.isEmpty(page.getList())) throw new XlwebException("没有可导出的数据!");
        List<StoreCombinationResponse> list = page.getList();
        List<CombinationProductExcelVo> voList = list.stream().map(temp -> {
            CombinationProductExcelVo vo = new CombinationProductExcelVo();
            BeanUtils.copyProperties(temp, vo);
            vo.setIsShow(temp.getIsShow() ? "开启" : "关闭");
            vo.setStopTime(DateUtil.timestamp2DateStr(temp.getStopTime(), Constants.DATE_FORMAT_DATE));
            return vo;
        }).collect(Collectors.toList());

        // 上传设置
        ExportUtil.setUpload(xlwebConfig.getImagePath(), Constants.UPLOAD_MODEL_PATH_EXCEL, Constants.UPLOAD_TYPE_FILE);

        // 文件名
        String fileName = "拼团".concat(DateUtil.nowDateTime(Constants.DATE_TIME_FORMAT_NUM)).concat(XlwebUtil.randomCount(111111111, 999999999).toString()).concat(".xlsx");

        //自定义标题别名
        LinkedHashMap<String, String> aliasMap = new LinkedHashMap<>();
        aliasMap.put("id", "编号");
        aliasMap.put("title", "拼团名称");
        aliasMap.put("otPrice", "原价");
        aliasMap.put("price", "拼团价");
        aliasMap.put("quotaShow", "库存");
        aliasMap.put("countPeople", "拼团人数");
        aliasMap.put("countPeopleAll", "参与人数");
        aliasMap.put("countPeoplePink", "成团数量");
        aliasMap.put("sales", "销量");
        aliasMap.put("isShow", "商品状态");
        aliasMap.put("stopTime", "拼团结束时间");

        return ExportUtil.exportExecl(fileName, "拼团商品导出", voList, aliasMap);
    }

    /**
     * 商品导出
     * @param request 请求参数
     * @return 导出地址
     */
    @Override
    public String exportProduct(StoreProductSearchRequest request) {
        PageParamRequest pageParamRequest = new PageParamRequest();
        pageParamRequest.setPage(Constants.DEFAULT_PAGE);
        pageParamRequest.setLimit(Constants.EXPORT_MAX_LIMIT);
        PageInfo<StoreProductResponse> storeProductResponsePageInfo = storeProductApi.getAdminList(request, pageParamRequest).getCheckedData();
        List<StoreProductResponse> list = storeProductResponsePageInfo.getList();
        if(list.size() < 1){
            throw new XlwebException("没有可导出的数据！");
        }

        //产品分类id
        List<String> cateIdListStr = list.stream().map(StoreProductResponse::getCateId).distinct().collect(Collectors.toList());

        HashMap<Integer, String> categoryNameList = new HashMap<Integer, String>();
        if(cateIdListStr.size() > 0){
            String join = StringUtils.join(cateIdListStr, ",");
            List<Integer> cateIdList = XlwebUtil.stringToArray(join);
            categoryNameList = categoryApi.getListInId(cateIdList).getCheckedData();
        }
        List<ProductExcelVo> voList = CollUtil.newArrayList();
        for (StoreProductResponse product : list ) {
            ProductExcelVo vo = new ProductExcelVo();
            vo.setStoreName(product.getStoreName());
            vo.setStoreInfo(product.getStoreInfo());
            vo.setCateName(XlwebUtil.getValueByIndex(categoryNameList, product.getCateId()));
            vo.setPrice("￥" + product.getPrice());
            vo.setStock(product.getStock().toString());
            vo.setSales(product.getSales().toString());
            vo.setBrowse(product.getBrowse().toString());
            voList.add(vo);
        }

        /**
         * ===============================
         * 以下为存储部分
         * ===============================
         */
        // 上传设置
        ExportUtil.setUpload(xlwebConfig.getImagePath(), Constants.UPLOAD_MODEL_PATH_EXCEL, Constants.UPLOAD_TYPE_FILE);

        // 文件名
        String fileName = "商品导出_".concat(DateUtil.nowDateTime(Constants.DATE_TIME_FORMAT_NUM)).concat(XlwebUtil.randomCount(111111111, 999999999).toString()).concat(".xlsx");

        //自定义标题别名
        LinkedHashMap<String, String> aliasMap = new LinkedHashMap<>();
        aliasMap.put("storeName", "商品名称");
        aliasMap.put("storeInfo", "商品简介");
        aliasMap.put("cateName", "商品分类");
        aliasMap.put("price", "价格");
        aliasMap.put("stock", "库存");
        aliasMap.put("sales", "销量");
        aliasMap.put("browse", "浏览量");

        return ExportUtil.exportExecl(fileName, "商品导出", voList, aliasMap);
    }

    /**
     * 订单导出
     *
     * @param request  查询条件
     * @return 文件名称
     */
    @Override
    public String exportOrder(StoreOrderSearchRequest request) {
        PageParamRequest pageParamRequest = new PageParamRequest();
        pageParamRequest.setPage(Constants.DEFAULT_PAGE);
        pageParamRequest.setLimit(Constants.EXPORT_MAX_LIMIT);
        CommonPage<StoreOrderDetailResponse> adminList = storeOrderApi.getAdminList(request, pageParamRequest).getCheckedData();
        List<StoreOrderDetailResponse> list = adminList.getList();
        if(list.size() < 1){
            throw new XlwebException("没有可导出的数据！");
        }

        List<OrderExcelVo> voList = CollUtil.newArrayList();
        for (StoreOrderDetailResponse order: list ) {
            OrderExcelVo vo = new OrderExcelVo();
            vo.setCreateTime(DateUtil.dateToStr(order.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            vo.setOrderId(order.getOrderId());
            vo.setOrderType(order.getOrderType());
            vo.setPayPrice(order.getPayPrice().toString());
            vo.setPayTypeStr(order.getPayTypeStr());
            vo.setProductName(order.getProductList().stream().map(item-> item.getInfo().getProductName()).collect(Collectors.joining(",")));
            vo.setRealName(order.getRealName());
            vo.setStatusStr(order.getStatusStr().get("value"));
            voList.add(vo);
        }

        /*
          ===============================
          以下为存储部分
          ===============================
         */
        // 上传设置
        ExportUtil.setUpload(xlwebConfig.getImagePath(), Constants.UPLOAD_MODEL_PATH_EXCEL, Constants.UPLOAD_TYPE_FILE);

        // 文件名
        String fileName = "订单导出_".concat(DateUtil.nowDateTime(Constants.DATE_TIME_FORMAT_NUM)).concat(XlwebUtil.randomCount(111111111, 999999999).toString()).concat(".xlsx");

        //自定义标题别名
        LinkedHashMap<String, String> aliasMap = new LinkedHashMap<>();
        aliasMap.put("orderId", "订单号");
        aliasMap.put("payPrice", "实际支付金额");
//        aliasMap.put("payType", "支付方式");
        aliasMap.put("createTime", "创建时间");
//        aliasMap.put("status", "订单状态");
        aliasMap.put("productName", "商品信息");
        aliasMap.put("statusStr", "订单状态");
        aliasMap.put("payTypeStr", "支付方式");
//        aliasMap.put("isDel", "是否删除");
//        aliasMap.put("refundReasonWapImg", "退款图片");
//        aliasMap.put("refundReasonWapExplain", "退款用户说明");
//        aliasMap.put("refundReasonTime", "退款时间");
//        aliasMap.put("refundReasonWap", "前台退款原因");
//        aliasMap.put("refundReason", "不退款的理由");
//        aliasMap.put("refundPrice", "退款金额");
//        aliasMap.put("refundStatus", "退款状态状态，0 未退款 1 申请中 2 已退款");
//        aliasMap.put("verifyCode", "核销码");
        aliasMap.put("orderType", "订单类型");
//        aliasMap.put("remark", "订单管理员备注");
        aliasMap.put("realName", "用户姓名");
//        aliasMap.put("paid", "支付状态");
//        aliasMap.put("type", "订单类型:0-普通订单，1-视频号订单");
//        aliasMap.put("isAlterPrice", "是否改价,0-否，1-是");

        return ExportUtil.exportExecl(fileName, "订单导出", voList, aliasMap);

    }
}

