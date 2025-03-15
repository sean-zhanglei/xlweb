package com.nbug.module.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.constants.PayConstants;
import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.model.finance.UserRecharge;
import com.nbug.mico.common.model.order.StoreOrder;
import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.model.user.UserBill;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.request.FundsMonitorRequest;
import com.nbug.mico.common.request.FundsMonitorSearchRequest;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreOrderRefundRequest;
import com.nbug.mico.common.response.MonitorResponse;
import com.nbug.mico.common.response.UserBillResponse;
import com.nbug.mico.common.utils.date.DateUtil;
import com.nbug.mico.common.vo.MyRecord;
import com.nbug.mico.common.vo.dateLimitUtilVo;
import com.nbug.module.store.api.storeOrder.StoreOrderApi;
import com.nbug.module.user.dal.UserBillDao;
import com.nbug.module.user.dal.UserRechargeDao;
import com.nbug.module.user.service.UserBillService;
import com.nbug.module.user.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * UserBillServiceImpl 接口实现

 */
@Service
public class UserBillServiceImpl extends ServiceImpl<UserBillDao, UserBill> implements UserBillService {

    private static final Logger logger = LoggerFactory.getLogger(UserBillServiceImpl.class);

    @Resource
    private UserBillDao dao;

    @Resource
    private UserService userService;

    @Resource
    private StoreOrderApi storeOrderApi;

    @Resource
    private UserRechargeDao userRechargeDao;

    /**
    * 列表
    * @param request 请求参数
    * @param pageParamRequest 分页类参数
    * @return List<UserBill>
    */
    @Override
    public List<UserBill> getList(FundsMonitorSearchRequest request, PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        QueryWrapper<UserBill> queryWrapper = new QueryWrapper<>();
        getMonthSql(request, queryWrapper);

        //排序
        if (request.getSort() == null) {
            queryWrapper.orderByDesc("create_time");
        }else{
            if ("asc".equals(request.getSort())) {
                queryWrapper.orderByAsc("number");
            }else{
                queryWrapper.orderByDesc("number");
            }
        }

        // 查询类型
        if (StringUtils.isNotBlank(request.getCategory())) {
            queryWrapper.eq("category", request.getCategory());
        }
        if (ObjectUtil.isNotNull(request.getPm())) {
            queryWrapper.eq("pm", request.getPm());
        }

        return dao.selectList(queryWrapper);
    }

    @Override
    public MyRecord getBalanceList(FundsMonitorSearchRequest request, PageParamRequest pageParamRequest) {
        try {
            MyRecord record = new MyRecord();
            PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
            QueryWrapper<UserBill> queryWrapper = new QueryWrapper<>();

            String[] times = request.getDateLimit().split("-");
            if (times.length == 2) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                SimpleDateFormat sdfDataLimit = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = sdf.parse(times[0]);
                Date endDate = sdf.parse(times[1]);
                Calendar cal =  Calendar.getInstance();
                cal.setTime(endDate);
                cal.add(Calendar.DAY_OF_MONTH, -1);
                endDate = cal.getTime();
                request.setDateLimit(sdfDataLimit.format(startDate) + "," + sdfDataLimit.format(endDate));
            }
            getMonthSql(request, queryWrapper);

            if (ObjectUtil.isNotNull(request.getPm())) {
                queryWrapper.eq("pm", request.getPm());
            }
            //排序
            if (request.getSort() == null) {
                queryWrapper.orderByDesc("create_time");
            } else {
                if ("asc".equals(request.getSort())) {
                    queryWrapper.orderByAsc("number");
                } else {
                    queryWrapper.orderByDesc("number");
                }
            }

            Map<String, String> status = new HashMap<>();
            status.put(Constants.USER_BILL_TYPE_SYSTEM_ADD, "系统增加");
            status.put(Constants.USER_BILL_TYPE_PAY_RECHARGE, "充值支付");
            status.put(Constants.USER_BILL_TYPE_TRANSFER_IN, "佣金转入余额");
            status.put(Constants.USER_BILL_TYPE_PAY_PRODUCT_REFUND, "商品退款");
            status.put(Constants.USER_BILL_TYPE_PAY_ORDER, "订单支付");
            status.put(Constants.USER_BILL_TYPE_SYSTEM_SUB, "系统减少");

            status.put(Constants.USER_BILL_TYPE_TRANSFER_IN, "佣金转入余额");
            status.put(Constants.USER_BILL_TYPE_BROKERAGE, "推广佣金");
            record.set("status", status);

            List<UserBill> list = dao.selectList(queryWrapper);
            if (CollectionUtils.isNotEmpty(list)) {
                // 关联用户
                List<Integer> uids = list.stream()
                        .map(item -> item.getUid())
                        .collect(Collectors.toList());
                Map<Integer, User> nicknameArr = userService.getMapListInUid(uids);

                List<Map<String, Object>> result = new ArrayList<>();
                // 关联订单
                for (UserBill item : list) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("id", item.getId());
                    data.put("number", item.getNumber());
                    data.put("mark", item.getMark());
                    data.put("pm", item.getPm());
                    data.put("nickname", nicknameArr.get(item.getUid()).getNickname());

                    String type = item.getType();
                    String relation;
                    if (Constants.USER_BILL_TYPE_PAY_ORDER.equals(type) || Constants.USER_BILL_TYPE_PAY_PRODUCT_REFUND.equals(type)) {
                        relation = storeOrderApi.getById(Integer.valueOf(item.getLinkId())).getCheckedData().getOrderId();
                    } else if (Constants.USER_BILL_TYPE_PAY_RECHARGE.equals(type)) {
                        relation = item.getLinkId();
                    } else {
                        relation = status.get(type);
                    }
                    data.put("relation", relation);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    data.put("add_time", sdf.format(item.getCreateTime()));
                    data.put("type_name", status.get(type));

                    result.add(data);
                }
                int count = Math.toIntExact(dao.selectCount(queryWrapper));
                record.set("count", count);
                record.set("list", result);
            } else {
                record.set("list", new ArrayList<>());
                record.set("count", 0);
            }
            return record;
        } catch (Exception ex) {
            logger.error("获取余额记录数据失败", ex);
            throw new XlwebException("获取余额记录数据失败");
        }
    }


    private void getMonthSql(FundsMonitorSearchRequest request, QueryWrapper<UserBill> queryWrapper) {
        queryWrapper.gt("status", 0); // -1无效
        if (!StringUtils.isBlank(request.getKeywords())) {
            queryWrapper.and(i -> i.
                    or().eq("id", request.getKeywords()).   //用户账单id
                    or().eq("uid", request.getKeywords()). //用户uid
                    or().eq("link_id", request.getKeywords()). //关联id
                    or().like("title", request.getKeywords()) //账单标题
            );
        }

        //时间范围
        if (StringUtils.isNotBlank(request.getDateLimit())) {
            dateLimitUtilVo dateLimit = DateUtil.getDateLimit(request.getDateLimit());
            //判断时间
            int compareDateResult = DateUtil.compareDate(dateLimit.getEndTime(), dateLimit.getStartTime(), Constants.DATE_FORMAT);
            if (compareDateResult == -1) {
                throw new XlwebException("开始时间不能大于结束时间！");
            }

            queryWrapper.between("create_time", dateLimit.getStartTime(), dateLimit.getEndTime());

            //资金范围
            if (request.getMax() != null && request.getMin() != null) {
                //判断时间
                if (request.getMax().compareTo(request.getMin()) < 0) {
                    throw new XlwebException("最大金额不能小于最小金额！");
                }
                queryWrapper.between("number", request.getMin(), request.getMax());
            }
        }


        //关联id
        if (StringUtils.isNotBlank(request.getLinkId())) {
            if ("gt".equals(request.getLinkId())) {
                queryWrapper.ne("link_id", 0);
            }else{
                queryWrapper.eq("link_id", request.getLinkId());
            }
        }

        //用户id集合
        if (null != request.getUserIdList() && request.getUserIdList().size() > 0) {
            queryWrapper.in("uid", request.getUserIdList());
        } else if (ObjectUtil.isNotNull(request.getUid())) {
            queryWrapper.eq("uid", request.getUid());
        }



        if (StringUtils.isNotBlank(request.getCategory())) {
            queryWrapper.eq("category", request.getCategory());
        }

        if (StringUtils.isNotBlank(request.getType())) {
            queryWrapper.eq("type", request.getType());
        }
    }

    /**
     * 新增/消耗  总金额
     * @param pm Integer 0 = 支出 1 = 获得
     * @param userId Integer 用户uid
     * @param category String 类型
     * @param date String 时间范围
     * @param type String 小类型
     * @return UserBill
     */
    @Override
    public BigDecimal getSumBigDecimal(Integer pm, Integer userId, String category, String date, String type) {
        QueryWrapper<UserBill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category", category).
                eq("status", 1);
        if (ObjectUtil.isNotNull(userId)) {
            queryWrapper.eq("uid", userId);
        }
        if (null != pm) {
            queryWrapper.eq("pm", pm);
        }
        if (null != type) {
            queryWrapper.eq("type", type);
        }
        if (null != date) {
            dateLimitUtilVo dateLimit = DateUtil.getDateLimit(date);
            queryWrapper.between("create_time", dateLimit.getStartTime(), dateLimit.getEndTime());
        }
        List<UserBill> userBills = dao.selectList(queryWrapper);
        if (CollUtil.isEmpty(userBills)) {
            return BigDecimal.ZERO;
        }
        return userBills.stream().map(UserBill::getNumber).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 保存退款日志
     * @return boolean
     */
    @Override
    public Boolean saveRefundBill(StoreOrderRefundRequest request, User user) {
        UserBill userBill = new UserBill();
        userBill.setTitle("商品退款");
        userBill.setUid(user.getUid());
        userBill.setCategory(Constants.USER_BILL_CATEGORY_MONEY);
        userBill.setType(Constants.USER_BILL_TYPE_PAY_PRODUCT_REFUND);
        userBill.setNumber(request.getAmount());
        userBill.setLinkId(request.getOrderId().toString());
        userBill.setBalance(user.getNowMoney().add(request.getAmount()));
        userBill.setMark("订单退款到余额" + request.getAmount() + "元");
        userBill.setPm(1);
        return save(userBill);
    }

    /**
     * 资金监控
     * @param request 查询参数
     * @param pageParamRequest 分页参数
     * @return PageInfo
     */
    @Override
    public PageInfo<MonitorResponse> fundMonitoring(FundsMonitorRequest request, PageParamRequest pageParamRequest) {
        Page<UserBill> billPage = PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        Map<String, Object> map = new HashMap<>();
        if (StrUtil.isNotBlank(request.getKeywords())) {
            map.put("keywords", StrUtil.format("%{}%", request.getKeywords()));
        }
        //时间范围
        if (StrUtil.isNotBlank(request.getDateLimit())) {
            dateLimitUtilVo dateLimit = DateUtil.getDateLimit(request.getDateLimit());
            map.put("startTime", dateLimit.getStartTime());
            map.put("endTime", dateLimit.getEndTime());
        }
        // 明细类型筛选
        if (StrUtil.isNotBlank(request.getTitle())) {
            switch (request.getTitle()) {
                case "recharge" :
                    map.put("title", "充值支付");
                    break;
                case "admin" :
                    map.put("title", "后台操作");
                    break;
                case "productRefund" :
                    map.put("title", "商品退款");
                    break;
                case "payProduct" :
                    map.put("title", "购买商品");
                    break;
            }

        }
        List<UserBillResponse> userBillResponses = dao.fundMonitoring(map);
        if (CollUtil.isEmpty(userBillResponses)) {
            return CommonPage.copyPageInfo(billPage, CollUtil.newArrayList());
        }
        List<MonitorResponse> responseList = userBillResponses.stream().map(e -> {
            MonitorResponse monitorResponse = new MonitorResponse();
            BeanUtils.copyProperties(e, monitorResponse);
            return monitorResponse;
        }).collect(Collectors.toList());
        return CommonPage.copyPageInfo(billPage, responseList);
    }

    /**
     * 用户账单记录（现金）
     * @param uid 用户uid
     * @param type 记录类型：all-全部，expenditure-支出，income-收入
     * @return PageInfo
     */
    @Override
    public PageInfo<UserBill> nowMoneyBillRecord(Integer uid, String type, PageParamRequest pageRequest) {
        Page<UserBill> billPage = PageHelper.startPage(pageRequest.getPage(), pageRequest.getLimit());
        LambdaQueryWrapper<UserBill> lqw = Wrappers.lambdaQuery();
        lqw.select(UserBill::getTitle, UserBill::getNumber, UserBill::getBalance, UserBill::getMark, UserBill::getCreateTime, UserBill::getPm);
        lqw.eq(UserBill::getUid, uid);
        lqw.eq(UserBill::getCategory, Constants.USER_BILL_CATEGORY_MONEY);
        switch (type) {
            case "all":
                break;
            case "expenditure":
                lqw.eq(UserBill::getPm, 0);
                break;
            case "income":
                lqw.eq(UserBill::getPm, 1);
                break;
        }
        lqw.eq(UserBill::getStatus, 1);
        lqw.orderByDesc(UserBill::getId);
        List<UserBill> billList = dao.selectList(lqw);
        return CommonPage.copyPageInfo(billPage, billList);
    }

    @Override
    public MyRecord getBalanceBasic(String time) {
        try {
            String[] times = time.split("-");
            if (times.length != 2) {
                throw new IllegalArgumentException("请选择时间");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date startDate = sdf.parse(times[0]);
            Date endDate = sdf.parse(times[1]);
            Calendar cal =  Calendar.getInstance();
            cal.setTime(endDate);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            endDate = cal.getTime();
            SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
            SimpleDateFormat sdfEndFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");

            // 当前有效用户的余额总和
            QueryWrapper<User> queryUserWrapper = new QueryWrapper<>();
            queryUserWrapper.select("IFNULL(sum(now_money), 0) as nowMoney");
            queryUserWrapper.eq("status", 1);
            BigDecimal nowPoint = userService.getOne(queryUserWrapper).getNowMoney();

            // 指定时间段内的所有余额收入
            QueryWrapper<UserBill> queryAddWrapper = new QueryWrapper<>();
            queryAddWrapper.select("IFNULL(sum(number), 0) as number");
            queryAddWrapper.eq("category", Constants.USER_BILL_CATEGORY_MONEY);
            queryAddWrapper.eq("status", 1);
            queryAddWrapper.eq("pm", 1);
            queryAddWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));

            BigDecimal allPoint = dao.selectOne(queryAddWrapper).getNumber();

            // 指定时间段内的所有余额支出
            QueryWrapper<UserBill> queryReduceWrapper = new QueryWrapper<>();
            queryReduceWrapper.select("IFNULL(sum(number), 0) as number");
            queryReduceWrapper.eq("category", Constants.USER_BILL_CATEGORY_MONEY);
            queryReduceWrapper.eq("status", 1);
            queryReduceWrapper.eq("pm", 0);
            queryAddWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
            BigDecimal payPoint = dao.selectOne(queryReduceWrapper).getNumber();

            MyRecord myRecord = new MyRecord();
            myRecord.set("now_balance", nowPoint);
            myRecord.set("add_balance", allPoint);
            myRecord.set("sub_balance", payPoint);
            return myRecord;
        } catch (Exception ex) {
            logger.error("获取余额统计数量数据失败", ex);
            throw new XlwebException("获取余额统计数量数据失败");
        }
    }

    @Override
    public MyRecord getBalanceTrend(String time) {
        try {
            String[] times = time.split("-");
            if (times.length != 2) {
                throw new IllegalArgumentException("请选择时间");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date startDate = sdf.parse(times[0]);
            Date endDate = sdf.parse(times[1]);
            Calendar cal =  Calendar.getInstance();
            cal.setTime(endDate);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            endDate = cal.getTime();

            long dayCount = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000) + 1;
            MyRecord record;
            if (dayCount == 1) {
                record = trend(times, 0);
            } else if (dayCount > 1 && dayCount <= 31) {
                record = trend(times, 1);
            } else if (dayCount > 31 && dayCount <= 92) {
                record = trend(times, 3);
            } else {
                record = trend(times, 30);
            }
            return record;
        } catch (Exception ex) {
            logger.error("获取余额统计折线图数据失败", ex);
            throw new XlwebException("获取余额统计折线图数据失败");
        }
    }

    public MyRecord trend(String[] time, int num) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate = sdf.parse(time[0]);
        Date endDate = sdf.parse(time[1]);

        List<String> xAxis = new ArrayList<>();
        String timeType = "%Y-%m";
        if (num == 0) {
            xAxis = Arrays.asList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
            timeType = "%H";
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);

            while (!cal.getTime().after(endDate)) {
                if (num == 30) {
                    xAxis.add(new SimpleDateFormat("yyyy-MM").format(cal.getTime()));
                    cal.add(Calendar.MONTH, 1);
                    timeType = "%Y-%m";
                } else {
                    xAxis.add(new SimpleDateFormat("MM-dd").format(cal.getTime()));
                    cal.add(Calendar.DAY_OF_MONTH, num);
                    timeType = "%m-%d";
                }
            }
        }

        SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat sdfEndFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        QueryWrapper<UserBill> queryAddWrapper = new QueryWrapper<>();
        queryAddWrapper.select("IFNULL(sum(number), 0) as number, DATE_FORMAT(update_time, '"+ timeType +"') as days");
        queryAddWrapper.eq("category", Constants.USER_BILL_CATEGORY_MONEY);
        queryAddWrapper.eq("status", 1);
        queryAddWrapper.eq("pm", 1);
        queryAddWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
        queryAddWrapper.groupBy("days");

        List<Map<String, Object>> pointAdd = dao.selectMaps(queryAddWrapper);
        QueryWrapper<UserBill> queryReduceWrapper = new QueryWrapper<>();
        queryReduceWrapper.select("IFNULL(sum(number), 0) as number, DATE_FORMAT(update_time, '"+ timeType +"') as days");
        queryReduceWrapper.eq("category", Constants.USER_BILL_CATEGORY_MONEY);
        queryReduceWrapper.eq("status", 1);
        queryReduceWrapper.eq("pm", 0);
        queryReduceWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
        queryReduceWrapper.groupBy("days");
        List<Map<String, Object>> pointSub = dao.selectMaps(queryReduceWrapper);

        Map<String, Float> pointAddMap = pointAdd.stream()
                .collect(Collectors.toMap(
                        item -> (String) item.get("days"),
                        item -> Float.valueOf(((BigDecimal)item.get("number")).toPlainString())
                ));

        Map<String, Float> pointSubMap = pointSub.stream()
                .collect(Collectors.toMap(
                        item -> (String) item.get("days"),
                        item -> Float.valueOf(((BigDecimal)item.get("number")).toPlainString())
                ));

        Map<String, List<Float>> data = new HashMap<>();
        data.put("余额积累", new ArrayList<>());
        data.put("余额消耗", new ArrayList<>());

        for (String item : xAxis) {
            data.get("余额积累").add(pointAddMap.getOrDefault(item, 0.0f));
            data.get("余额消耗").add(pointSubMap.getOrDefault(item, 0.0f));
        }

        List<Map<String, Object>> series = new ArrayList<>();
        for (Map.Entry<String, List<Float>> entry : data.entrySet()) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", entry.getKey());
            item.put("data", entry.getValue());
            item.put("type", "line");
            series.add(item);
        }
        MyRecord myRecord = new MyRecord();
        myRecord.set("xAxis", xAxis);
        myRecord.set("series", series);

        return myRecord;
    }

    @Override
    public MyRecord getBalanceChannel(String time) {
        try {
            String[] times = time.split("-");
            if (times.length != 2) {
                throw new IllegalArgumentException("请选择时间");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date startDate = sdf.parse(times[0]);
            Date endDate = sdf.parse(times[1]);
            Calendar cal =  Calendar.getInstance();
            cal.setTime(endDate);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            endDate = cal.getTime();

            List<String> bingXdata = Arrays.asList("系统增加", "用户充值", "佣金提现","商品退款");
            List<String> color = Arrays.asList("#64a1f4", "#3edeb5", "#70869f", "#fc7d6a");
            List<String> data = Arrays.asList(Constants.USER_BILL_TYPE_SYSTEM_ADD, Constants.USER_BILL_TYPE_PAY_RECHARGE, Constants.USER_BILL_TYPE_TRANSFER_IN, Constants.USER_BILL_TYPE_PAY_PRODUCT_REFUND);
            List<Map<String, Object>> bingData = new ArrayList<>();

            for (int key = 0; key < data.size(); key++) {
                String item = data.get(key);
                // 增加的余额
                SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
                SimpleDateFormat sdfEndFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
                QueryWrapper<UserBill> queryAddWrapper = new QueryWrapper<>();
                queryAddWrapper.select("IFNULL(sum(number), 0) as number");
                queryAddWrapper.eq("category", Constants.USER_BILL_CATEGORY_MONEY);
                queryAddWrapper.eq("status", 1);
                queryAddWrapper.in("type", item);
                queryAddWrapper.eq("pm", 1);
                queryAddWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
                BigDecimal addValue = dao.selectOne(queryAddWrapper).getNumber();

                Map<String, Object> recordData = new HashMap<>();
                recordData.put("name", bingXdata.get(key));
                recordData.put("value", addValue);
                Map<String, Object> itemStyle = new HashMap<>();
                itemStyle.put("color", color.get(key));
                recordData.put("itemStyle", itemStyle);
                bingData.add(recordData);
            }

            List<Map<String, Object>> list = new ArrayList<>();
            BigDecimal count = bingData.stream().map(item -> (BigDecimal) item.get("value")).reduce(BigDecimal.ZERO, BigDecimal::add);

            for (Map<String, Object> item : bingData) {
                BigDecimal value = (BigDecimal) item.get("value");
                double percent = count.intValue() != 0 ?
                        value
                                .divide(count, 4, BigDecimal.ROUND_HALF_UP)
                                .multiply(new BigDecimal(100))
                                .setScale(1, BigDecimal.ROUND_HALF_UP)
                                .doubleValue() : 0;
                Map<String, Object> recordData = new HashMap<>();
                recordData.put("name", item.get("name"));
                recordData.put("value", value);
                recordData.put("percent", percent);
                list.add(recordData);
            }

            list.sort(Comparator.comparing(item -> (BigDecimal)item.get("value")));

            MyRecord myRecord = new MyRecord();
            myRecord.set("bing_xdata", bingXdata);
            myRecord.set("bing_data", bingData);
            myRecord.set("list", list);

            return myRecord;
        } catch (Exception ex) {
            logger.error("获取余额来源分析数据失败", ex);
            throw new XlwebException("获取余额来源分析数据失败");
        }
    }

    @Override
    public MyRecord getBalanceType(String time) {
        try {
            String[] times = time.split("-");
            if (times.length != 2) {
                throw new IllegalArgumentException("请选择时间");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date startDate = sdf.parse(times[0]);
            Date endDate = sdf.parse(times[1]);
            Calendar cal =  Calendar.getInstance();
            cal.setTime(endDate);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            endDate = cal.getTime();
            List<String> bingXdata = Arrays.asList("购买商品", "系统减少");
            List<String> color = Arrays.asList("#64a1f4", "#70869f");
            List<String> data = Arrays.asList(Constants.USER_BILL_TYPE_PAY_ORDER, Constants.USER_BILL_TYPE_SYSTEM_SUB);
            List<Map<String, Object>> bingData = new ArrayList<>();

            for (int key = 0; key < data.size(); key++) {
                String item = data.get(key);
                // 消耗的余额
                SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
                SimpleDateFormat sdfEndFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
                QueryWrapper<UserBill> queryReduceWrapper = new QueryWrapper<>();
                queryReduceWrapper.select("IFNULL(sum(number), 0) as number");
                queryReduceWrapper.eq("category", Constants.USER_BILL_CATEGORY_MONEY);
                queryReduceWrapper.eq("status", 1);
                queryReduceWrapper.in("type", item);
                queryReduceWrapper.eq("pm", 0);
                queryReduceWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
                BigDecimal addValue = dao.selectOne(queryReduceWrapper).getNumber();

                Map<String, Object> recordData = new HashMap<>();
                recordData.put("name", bingXdata.get(key));
                recordData.put("value", addValue);
                Map<String, Object> itemStyle = new HashMap<>();
                itemStyle.put("color", color.get(key));
                recordData.put("itemStyle", itemStyle);
                bingData.add(recordData);
            }

            List<Map<String, Object>> list = new ArrayList<>();
            BigDecimal count = bingData.stream().map(item -> (BigDecimal) item.get("value")).reduce(BigDecimal.ZERO, BigDecimal::add);

            for (Map<String, Object> item : bingData) {
                BigDecimal value = (BigDecimal) item.get("value");
                double percent = count.intValue() != 0 ?
                        value
                                .divide(count, 4, BigDecimal.ROUND_HALF_UP)
                                .multiply(new BigDecimal(100))
                                .setScale(1, BigDecimal.ROUND_HALF_UP)
                                .doubleValue() : 0;
                Map<String, Object> recordData = new HashMap<>();
                recordData.put("name", item.get("name"));
                recordData.put("value", value);
                recordData.put("percent", percent);
                list.add(recordData);
            }

            list.sort(Comparator.comparing(item -> (BigDecimal)item.get("value")));

            MyRecord myRecord = new MyRecord();
            myRecord.set("bing_xdata", bingXdata);
            myRecord.set("bing_data", bingData);
            myRecord.set("list", list);

            return myRecord;
        } catch (Exception ex) {
            logger.error("获取余额类型分析数据失败", ex);
            throw new XlwebException("获取余额类型分析数据失败");
        }
    }

    @Override
    public MyRecord getFlowRecord(String type, String time, PageParamRequest pageParamRequest) {
        try {
            MyRecord myRecord = new MyRecord();
            QueryWrapper<UserBill> queryWrapper = new QueryWrapper<>();

            String timeType = "%Y-%m-%d";
            switch (type) {
                case "day":
                    timeType = "%Y-%m-%d";
                    break;
                case "week":
                    timeType = "%u";
                    break;
                case "month":
                    timeType = "%Y-%m";
                    break;
            }
            queryWrapper.select("update_time, DATE_FORMAT(update_time, '"+ timeType +"') AS days,IFNULL(SUM(CASE WHEN pm = 1 THEN number ELSE 0 END), 0) AS income_price,IFNULL(SUM(CASE WHEN pm = 0 THEN number ELSE 0 END),0) AS exp_price");
            queryWrapper.eq("category", Constants.USER_BILL_CATEGORY_MONEY);
            queryWrapper.eq("status", 1);
            String[] times = time.split("-");
            if (times.length == 2) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
                SimpleDateFormat sdfEndFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
                Date startDate = sdf.parse(times[0]);
                Date endDate = sdf.parse(times[1]);
                Calendar cal =  Calendar.getInstance();
                cal.setTime(endDate);
                cal.add(Calendar.DAY_OF_MONTH, -1);
                endDate = cal.getTime();
                queryWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
            }
            queryWrapper.groupBy("days");

            List<Map<String, Object>> listAll = dao.selectMaps(queryWrapper);
            int count = listAll.size();

            PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
            List<Map<String, Object>> list = dao.selectMaps(queryWrapper);

            for (Map<String, Object> item : list) {
                String days = (String) item.get("days");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date updateTime = sdf.parse(item.get("update_time").toString());

                QueryWrapper<UserBill> queryAllWrapper = new QueryWrapper<>();
                queryAllWrapper.eq("category", Constants.USER_BILL_CATEGORY_MONEY);
                queryAllWrapper.eq("status", 1);
                SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
                SimpleDateFormat sdfEndFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
                if ("day".equals(type)) {
                    SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd");
                    Date curDate = sdfStart.parse(days);

                    queryAllWrapper.between("update_time", sdfFormat.format(curDate), sdfEndFormat.format(curDate));
                    List<Integer> ids = dao.selectList(queryAllWrapper).stream().map(UserBill::getId).collect(Collectors.toList());
                    item.put("ids", ids);
                } else if ("week".equals(type)) {
                    SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
                    String year = sdfYear.format(updateTime);

                    Date startDate = getStartDateOfWeek(Integer.parseInt(year), Integer.parseInt(days));
                    Date endDate = getEndDateOfWeek(Integer.parseInt(year), Integer.parseInt(days));
                    queryAllWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
                    List<Integer> ids = dao.selectList(queryAllWrapper).stream().map(UserBill::getId).collect(Collectors.toList());
                    item.put("ids", ids);
                } else if ("month".equals(type)) {
                    SimpleDateFormat sdfMonthStart = new SimpleDateFormat("yyyy-MM-01 00:00:00");
                    SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
                    SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
                    String year = sdfYear.format(updateTime);
                    String month = sdfMonth.format(updateTime);

                    Date startDate = sdf.parse(sdfMonthStart.format(updateTime));
                    Date endDate = getMonthEndDay(Integer.parseInt(year), Integer.parseInt(month) - 1);
                    queryAllWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
                    List<Integer> ids = dao.selectList(queryAllWrapper).stream().map(UserBill::getId).collect(Collectors.toList());
                    item.put("ids", ids);
                }
            }

            SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfWeek = new SimpleDateFormat("MM");
            SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");

            int i = 1;
            for (Map<String, Object> item : list) {
                item.put("id", i++);
                item.put("entry_price", ((BigDecimal) item.get("income_price")).subtract((BigDecimal) item.get("exp_price")).setScale(2, RoundingMode.HALF_UP).doubleValue());
                String addTimeStr = item.get("update_time").toString();
                Date addTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(addTimeStr);

                switch (type) {
                    case "day":
                        item.put("title", "日账单");
                        item.put("add_time", sdfDay.format(addTime));
                        break;
                    case "week":
                        item.put("title", "周账单");
                        item.put("add_time", "第" + item.get("days") + "周(" + sdfWeek.format(addTime) + "月)");
                        break;
                    case "month":
                        item.put("title", "月账单");
                        item.put("add_time", sdfMonth.format(addTime));
                        break;
                }
            }
            myRecord.set("list", list);
            myRecord.set("count", count);
            return myRecord;
        } catch (Exception ex) {
            logger.error("获取账单记录列表数据失败", ex);
            throw new XlwebException("获取账单记录列表数据失败");
        }
    }

    /**
     * 获取每月的最后一天
     * @param year
     * @param month
     * @return
     */
    public Date getMonthEndDay(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 获取每周开始的时间
     * @param year
     * @param week
     * @return
     */
    public Date getStartDateOfWeek(int year, int week) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        // 将日期设置为该周的第一天（通常是周日，不同地区可能不同）
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return calendar.getTime();
    }

    /**
     * 获取每周结束的时间
     * @param year
     * @param week
     * @return
     */
    public Date getEndDateOfWeek(int year, int week) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        // 将日期设置为该周的最后一天（通常是周六，不同地区可能不同）
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        // 加上一天，因为默认时间是 00:00:00，需要包含周六的全部时间
        calendar.add(Calendar.DATE, 6);
        return calendar.getTime();
    }

    @Override
    public MyRecord getFlowList(List<Integer> ids, String keywords, PageParamRequest pageParamRequest) {
        try {
            MyRecord myRecord = new MyRecord();
            Map<String, String> status = new HashMap<>();
            status.put(Constants.USER_BILL_TYPE_SYSTEM_ADD, "系统增加");
            status.put(Constants.USER_BILL_TYPE_PAY_RECHARGE, "充值支付");
            status.put(Constants.USER_BILL_TYPE_TRANSFER_IN, "佣金转入余额");
            status.put(Constants.USER_BILL_TYPE_PAY_PRODUCT_REFUND, "商品退款");
            status.put(Constants.USER_BILL_TYPE_PAY_ORDER, "订单支付");
            status.put(Constants.USER_BILL_TYPE_SYSTEM_SUB, "系统减少");

            status.put(Constants.USER_BILL_TYPE_TRANSFER_IN, "佣金转入余额");
            status.put(Constants.USER_BILL_TYPE_BROKERAGE, "推广佣金");

            Map<String, String> payType = new HashMap<>();
            // 支付方式
            payType.put(PayConstants.PAY_TYPE_WE_CHAT, "微信支付");
            payType.put(PayConstants.PAY_TYPE_YUE, "余额支付");
            payType.put(PayConstants.PAY_TYPE_ALI_PAY, "支付宝");
            payType.put(PayConstants.PAY_TYPE_OFFLINE, "线下支付");

            Map<String, String> payChannel = new HashMap<>();
            // 支付渠道
            payChannel.put(PayConstants.PAY_CHANNEL_WE_CHAT_H5, "H5唤起微信支付");
            payChannel.put(PayConstants.PAY_CHANNEL_WE_CHAT_PROGRAM, "小程序");// 充值支付-小程序
            payChannel.put(PayConstants.PAY_CHANNEL_WE_CHAT_PUBLIC, "公众号");
            payChannel.put(PayConstants.PAY_CHANNEL_WE_CHAT_APP_ANDROID, "微信App支付android");
            payChannel.put(PayConstants.PAY_CHANNEL_WE_CHAT_APP_IOS, "微信App支付ios");
            payChannel.put(PayConstants.PAY_CHANNEL_ALI_PAY, "支付宝支付");
            payChannel.put(PayConstants.PAY_CHANNEL_ALI_APP_PAY, "微信App支付ios");

            QueryWrapper<UserBill> queryAllWrapper = new QueryWrapper<>();
            queryAllWrapper.eq("category", Constants.USER_BILL_CATEGORY_MONEY);
            queryAllWrapper.eq("status", 1);
            if (StringUtils.isNotEmpty(keywords)) {
                List<Integer> uids = userService.findIdListLikeName(keywords);
                if (CollectionUtils.isNotEmpty(uids)) {
                    queryAllWrapper.in("uid", uids);
                }
            }
            queryAllWrapper.in("id", ids);
            dao.selectList(queryAllWrapper);

            List<Map<String, Object>> listAll = dao.selectMaps(queryAllWrapper);
            int count = listAll.size();

            PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
            List<Map<String, Object>> list = dao.selectMaps(queryAllWrapper);

            List<Map<String, Object>> result = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(list)) {
                List<Integer> uids = list.stream()
                        .map(item -> Integer.valueOf(Long.toString((Long) item.get("uid"))))
                        .collect(Collectors.toList());
                Map<Integer, User> nicknameArr = userService.getMapListInUid(uids);
                for (Map<String, Object> item : list) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("id", item.get("id"));
                    data.put("number", item.get("number"));
                    data.put("mark", item.get("mark"));
                    data.put("pm", item.get("pm"));
                    data.put("nickname", nicknameArr.get(Integer.valueOf(Long.toString((Long) item.get("uid")))).getNickname());

                    String type = String.valueOf(item.get("type"));
                    String relation;
                    String payTypeName;
                    if (Constants.USER_BILL_TYPE_PAY_ORDER.equals(type) || Constants.USER_BILL_TYPE_PAY_PRODUCT_REFUND.equals(type)) {
                        StoreOrder storeOrder = storeOrderApi.getById(Integer.valueOf(String.valueOf(item.get("link_id")))).getCheckedData();

                        payTypeName = storeOrder.getPayType();
                        data.put("pay_type_name", payType.get(payTypeName));
                        relation  = storeOrder.getOrderId();
                    } else if (Constants.USER_BILL_TYPE_PAY_RECHARGE.equals(type)) {
                        QueryWrapper<UserRecharge> userRechargeQueryWrapper = new QueryWrapper<>();
                        userRechargeQueryWrapper.eq("order_id", item.get("link_id"));
                        UserRecharge userRecharge = userRechargeDao.selectOne(userRechargeQueryWrapper);
                        // 充值来源 | public =  微信公众号, weixinh5 =微信H5支付, routine = 小程序，weixinAppIos-微信appios支付，
                        // weixinAppAndroid-微信app安卓支付,alipay-支付包支付，appAliPay-App支付宝支付")
                        payTypeName = userRecharge.getRechargeType();
                        data.put("pay_type_name", payChannel.get(payTypeName));
                        relation = userRecharge.getOrderId();
                    } else {
                        // 推广、系统操作等
                        data.put("pay_type_name", "");
                        relation = status.get(type);
                    }
                    data.put("order_id", relation);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    data.put("update_time", sdf.format(item.get("update_time")));
                    data.put("type_name", status.get(type));
                    result.add(data);
                }
            }

            myRecord.set("count", count);
            myRecord.set("list", result);
            return myRecord;
        }catch (Exception ex) {
            logger.error("获取资金流水统计数据失败", ex);
            throw new XlwebException("获取资金流水统计数据失败");
        }
    }

    @Override
    public List<Map<String, Object>> listMaps(String timeType, Date startDate, Date endDate) {
        SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat sdfEndFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");

        QueryWrapper<UserBill> queryYueIncomeWrapper = new QueryWrapper<>();
        queryYueIncomeWrapper.select("IFNULL(sum(number), 0) as number, DATE_FORMAT(update_time, '"+ timeType +"') as days");
        queryYueIncomeWrapper.eq("category", Constants.USER_BILL_CATEGORY_MONEY);
        queryYueIncomeWrapper.eq("status", 1);
        queryYueIncomeWrapper.in("type", Arrays.asList(Constants.USER_BILL_TYPE_SYSTEM_ADD, Constants.USER_BILL_TYPE_PAY_RECHARGE));
        queryYueIncomeWrapper.eq("pm", 1);
        queryYueIncomeWrapper.groupBy("days");
        queryYueIncomeWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
        List<Map<String, Object>> result = listMaps(queryYueIncomeWrapper);
        return result;
    }



}

