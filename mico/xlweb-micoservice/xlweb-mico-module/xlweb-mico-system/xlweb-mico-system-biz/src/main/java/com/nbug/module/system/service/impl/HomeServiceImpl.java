package com.nbug.module.system.service.impl;

import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.model.order.StoreOrder;
import com.nbug.mico.common.response.HomeRateResponse;
import com.nbug.mico.common.utils.date.DateUtil;
import com.nbug.module.store.api.storeOrder.StoreOrderApi;
import com.nbug.module.system.service.HomeService;
import com.nbug.module.user.api.user.UserApi;
import com.nbug.module.user.api.userVisitRecord.UserVisitRecordApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户表 服务实现类

 */
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private StoreOrderApi storeOrderApi;

    @Autowired
    private UserApi userApi;

    @Autowired
    private UserVisitRecordApi userVisitRecordApi;

    /**
     * 用户曲线图
     * @author Mr.Zhang
     * @since 2020-05-16
     */
    @Override
    public Map<Object, Object> chartUser() {
        return dataFormat(userApi.getAddUserCountGroupDate(Constants.SEARCH_DATE_LATELY_30).getCheckedData(), Constants.SEARCH_DATE_LATELY_30);
    }

    /**
     * 订单量趋势
     * @author Mr.Zhang
     * @since 2020-05-16
     */
    @Override
    public Map<String, Object> chartOrder() {
        Map<String, Object> map = new HashMap<>();

        List<StoreOrder> list = storeOrderApi.getOrderGroupByDate(Constants.SEARCH_DATE_LATELY_30, Constants.NUM_TEN).getCheckedData();

        map.put("quality",
                dataFormat(getOrderCountGroupByDate(list), Constants.SEARCH_DATE_LATELY_30)
        );
        map.put("price",
                dataFormat(getOrderPriceGroupByDate(list), Constants.SEARCH_DATE_LATELY_30)
        );
        return map;
    }

    /**
     * 按开始结束时间查询每日新增订单数量
     * @param list List<StoreOrder> 时间范围
     * @author Mr.Zhang
     * @since 2020-05-16
     * @return HashMap<String, Object>
     */
    private Map<Object, Object> getOrderCountGroupByDate(List<StoreOrder> list) {
        Map<Object, Object> map = new HashMap<>();

        if (list.size() < 1) {
            return map;
        }

        for (StoreOrder storeOrder : list) {
            map.put(storeOrder.getOrderId(), storeOrder.getId());
        }

        return map;
    }

    /**
     * 按开始结束时间查询每日新增订单销售额
     * @param list List<StoreOrder> 时间范围
     * @author Mr.Zhang
     * @since 2020-05-16
     * @return HashMap<String, Object>
     */
    private Map<Object, Object> getOrderPriceGroupByDate(List<StoreOrder> list) {
        Map<Object, Object> map = new HashMap<>();

        if (list.size() < 1) {
            return map;
        }

        for (StoreOrder storeOrder : list) {
            map.put(storeOrder.getOrderId(), storeOrder.getPayPrice());
        }

        return map;
    }

    /**
     * 日期和数量格式化
     * @author Mr.Zhang
     * @since 2020-05-16
     * @return Map<String, Integer>
     */
    private Map<Object, Object> dataFormat(Map<Object, Object> countGroupDate, String dateLimit) {
        Map<Object, Object> map = new LinkedHashMap<>();
        List<String> listDate = DateUtil.getListDate(dateLimit);

        String[] weekList = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

        int i = 0;
        for (String date : listDate) {
            Object count = 0;
            if (countGroupDate.containsKey(date)) {
                count = countGroupDate.get(date);
            }
            String key;

            //周格式化
            switch(dateLimit) {
                //格式化周
                case Constants.SEARCH_DATE_WEEK:
                case Constants.SEARCH_DATE_PRE_WEEK:
                    key = weekList[i];
                    break;
                    //格式化月
                case Constants.SEARCH_DATE_PRE_MONTH:
                case Constants.SEARCH_DATE_MONTH:
                    key = i + 1 + "";
                    break;
                    //默认显示两位日期
                default:
                    key = date.substring(5, 10);
            }
            map.put(key, count);
            i++;
        }
        return map;
    }

    /**
     * 日期和数量格式化
     * @author Mr.Zhang
     * @since 2020-05-16
     * @return Map<String, Integer>
     */
    private Map<Object, Object> dataFormatYear(Map<Object, Object> countGroupDate, String dateLimit) {
        Map<Object, Object> map = new LinkedHashMap<>();
        List<Object> listDate = new ArrayList<>();
        String year = "";
        if (dateLimit.equals(Constants.SEARCH_DATE_YEAR)) {
            year = DateUtil.nowYear();
        }

        if (dateLimit.equals(Constants.SEARCH_DATE_PRE_YEAR)) {
            year = DateUtil.lastYear();
        }

        //处理年
        //12个月份数据
        for (int i = 1; i <= 12; i++) {
            String month = i + "";
            if (i < 10) {
                month = "0" + i;
            }
            listDate.add(year + "-" + month);
        }

        String[] monthList = new String[]{"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月" , "十一月", "十二月" };

        int i = 0;
        for (Object date : listDate) {
            Object count = 0;
            if (countGroupDate.containsKey(date)) {
                count = countGroupDate.get(date);
            }
            map.put(monthList[i], count);
            i++;
        }
        return map;
    }

    /**
     * 用户购买统计
     * @author Mr.Zhang
     * @since 2020-05-16
     */
    @Override
    public Map<String, Integer> chartUserBuy() {
        Map<String, Integer> map = new HashMap<>();
        //未消费用户
        map.put("zero", userApi.getCountByPayCount(Constants.NUM_ZERO, Constants.NUM_ZERO).getCheckedData());

        //消费一次用户
        map.put("one", userApi.getCountByPayCount(Constants.NUM_ONE, Constants.NUM_ONE).getCheckedData());

        //留存客户
        map.put("history", userApi.getCountByPayCount(Constants.NUM_TWO, Constants.NUM_THREE).getCheckedData());

        //回流客户
        map.put("back", userApi.getCountByPayCount(Constants.NUM_ONE, Constants.EXPORT_MAX_LIMIT).getCheckedData());

        return map;
    }

    /**
     * 周订单量趋势
     * @author Mr.Zhang
     * @since 2020-05-16
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> chartOrderInWeek() {
        return returnOrderDate(Constants.SEARCH_DATE_WEEK, Constants.SEARCH_DATE_PRE_WEEK, Constants.NUM_TEN);


    }

    /**
     * 月订单量趋势
     * @author Mr.Zhang
     * @since 2020-05-16
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> chartOrderInMonth() {
        return returnOrderDate(Constants.SEARCH_DATE_MONTH, Constants.SEARCH_DATE_PRE_MONTH, Constants.NUM_TEN);
    }

    /**
     * 年订单量趋势
     * @author Mr.Zhang
     * @since 2020-05-16
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> chartOrderInYear() {
        Map<String, Object> map = new HashMap<>();

        //查询本年订单量
        List<StoreOrder> list = storeOrderApi.getOrderGroupByDate(Constants.SEARCH_DATE_YEAR, Constants.NUM_SEVEN).getCheckedData();

        map.put("quality",
                dataFormatYear(getOrderCountGroupByDate(list), Constants.SEARCH_DATE_YEAR)
        );
        map.put("price",
                dataFormatYear(getOrderPriceGroupByDate(list), Constants.SEARCH_DATE_YEAR)
        );

        //查询上年订单量
        List<StoreOrder> preList = storeOrderApi.getOrderGroupByDate(Constants.SEARCH_DATE_PRE_YEAR, Constants.NUM_SEVEN).getCheckedData();

        map.put("preQuality",
                dataFormatYear(getOrderCountGroupByDate(preList), Constants.SEARCH_DATE_PRE_YEAR)
        );
        map.put("prePrice",
                dataFormatYear(getOrderPriceGroupByDate(preList), Constants.SEARCH_DATE_PRE_YEAR)
        );

        return map;
    }

    /**
     * 首页数据
     * 今日/昨日
     * 销售额
     * 用户访问量
     * 订单量
     * 新增用户
     * @return HomeRateResponse
     */
    @Override
    public HomeRateResponse indexDate() {
        String today = cn.hutool.core.date.DateUtil.date().toString("yyyy-MM-dd");
        String yesterday = cn.hutool.core.date.DateUtil.yesterday().toString("yyyy-MM-dd");
        HomeRateResponse response = new HomeRateResponse();
        response.setSales(storeOrderApi.getPayOrderAmountByDate(today).getCheckedData());
        response.setYesterdaySales(storeOrderApi.getPayOrderAmountByDate(yesterday).getCheckedData());
        response.setPageviews(userVisitRecordApi.getPageviewsByDate(today).getCheckedData());
        response.setYesterdayPageviews(userVisitRecordApi.getPageviewsByDate(yesterday).getCheckedData());
        response.setOrderNum(storeOrderApi.getOrderNumByDate(today).getCheckedData());
        response.setYesterdayOrderNum(storeOrderApi.getOrderNumByDate(yesterday).getCheckedData());
        response.setNewUserNum(userApi.getRegisterNumByDate(today).getCheckedData());
        response.setYesterdayNewUserNum(userApi.getRegisterNumByDate(yesterday).getCheckedData());
        return response;
    }

    /**
     * 组装订单统计返回数据
     * @author Mr.Zhang
     * @since 2020-05-16
     * @return Map<String, Object>
     */
    private Map<String, Object> returnOrderDate(String dateLimit, String preDateLimit, int leftTime) {
        Map<String, Object> map = new HashMap<>();

        //查询本周周订单量
        List<StoreOrder> list = storeOrderApi.getOrderGroupByDate(dateLimit, leftTime).getCheckedData();

        map.put("quality",
                dataFormat(getOrderCountGroupByDate(list), dateLimit)
        );
        map.put("price",
                dataFormat(getOrderPriceGroupByDate(list), dateLimit)
        );

        //查询上周周订单量
        List<StoreOrder> preList = storeOrderApi.getOrderGroupByDate(preDateLimit, leftTime).getCheckedData();

        map.put("preQuality",
                dataFormat(getOrderCountGroupByDate(preList), preDateLimit)
        );
        map.put("prePrice",
                dataFormat(getOrderPriceGroupByDate(preList), preDateLimit)
        );

        return map;
    }

}
