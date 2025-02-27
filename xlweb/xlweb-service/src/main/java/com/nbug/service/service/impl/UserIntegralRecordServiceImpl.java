package com.nbug.service.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nbug.common.page.CommonPage;
import com.nbug.common.request.AdminIntegralSearchRequest;
import com.nbug.common.request.PageParamRequest;
import com.nbug.common.constants.Constants;
import com.nbug.common.constants.IntegralRecordConstants;
import com.nbug.common.exception.XlwebException;
import com.nbug.common.response.UserIntegralRecordResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nbug.common.utils.DateUtil;
import com.nbug.common.vo.MyRecord;
import com.nbug.common.vo.dateLimitUtilVo;
import com.nbug.common.model.user.User;
import com.nbug.common.model.user.UserIntegralRecord;
import com.nbug.service.dao.UserIntegralRecordDao;
import com.nbug.service.service.UserIntegralRecordService;
import com.nbug.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
 * 用户积分记录Service实现类

 */
@Service
public class UserIntegralRecordServiceImpl extends ServiceImpl<UserIntegralRecordDao, UserIntegralRecord> implements UserIntegralRecordService {

    private static final Logger logger = LoggerFactory.getLogger(UserIntegralRecordServiceImpl.class);

    @Resource
    private UserIntegralRecordDao dao;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * 根据订单编号、uid获取记录列表
     * @param orderNo 订单编号
     * @param uid 用户uid
     * @return 记录列表
     */
    @Override
    public List<UserIntegralRecord> findListByOrderIdAndUid(String orderNo, Integer uid) {
        LambdaQueryWrapper<UserIntegralRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(UserIntegralRecord::getUid, uid);
        lqw.eq(UserIntegralRecord::getLinkId, orderNo);
        lqw.in(UserIntegralRecord::getStatus, IntegralRecordConstants.INTEGRAL_RECORD_STATUS_CREATE, IntegralRecordConstants.INTEGRAL_RECORD_STATUS_FROZEN, IntegralRecordConstants.INTEGRAL_RECORD_STATUS_COMPLETE);
        List<UserIntegralRecord> recordList = dao.selectList(lqw);
        if (CollUtil.isEmpty(recordList)) {
            return recordList;
        }
        for (int i = 0; i < recordList.size();) {
            UserIntegralRecord record = recordList.get(i);
            if (record.getType().equals(IntegralRecordConstants.INTEGRAL_RECORD_TYPE_ADD)) {
                if (record.getStatus().equals(IntegralRecordConstants.INTEGRAL_RECORD_STATUS_COMPLETE)) {
                    recordList.remove(i);
                    continue;
                }
            }
            i++;
        }
        return recordList;
    }

    /**
     * 积分解冻
     */
    @Override
    public void integralThaw() {
        // 查询需要解冻的积分
        List<UserIntegralRecord> thawList = findThawList();
        if (CollUtil.isEmpty(thawList)) {
            return;
        }
        for (UserIntegralRecord record : thawList) {
            // 查询对应的用户
            User user = userService.getById(record.getUid());
            if (ObjectUtil.isNull(user)) {
                continue ;
            }
            record.setStatus(IntegralRecordConstants.INTEGRAL_RECORD_STATUS_COMPLETE);
            // 计算积分余额
            Integer balance = user.getIntegral() + record.getIntegral();
            record.setBalance(balance);
            record.setUpdateTime(cn.hutool.core.date.DateUtil.date());

            // 解冻
            Boolean execute = transactionTemplate.execute(e -> {
                updateById(record);
                userService.operationIntegral(record.getUid(), record.getIntegral(), user.getIntegral(), "add");
                return Boolean.TRUE;
            });
            if (!execute) {
                logger.error(StrUtil.format("积分解冻处理—解冻出错，记录id = {}", record.getId()));
            }
        }
    }

    /**
     * PC后台列表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     * @return 记录列表
     */
    @Override
    public PageInfo<UserIntegralRecordResponse> findAdminList(AdminIntegralSearchRequest request, PageParamRequest pageParamRequest) {
        Page<UserIntegralRecordResponse> page = PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        LambdaQueryWrapper<UserIntegralRecord> lqw = Wrappers.lambdaQuery();
        lqw.select(UserIntegralRecord::getId, UserIntegralRecord::getTitle, UserIntegralRecord::getBalance, UserIntegralRecord::getIntegral,
                UserIntegralRecord::getMark, UserIntegralRecord::getUid, UserIntegralRecord::getUpdateTime);
        lqw.eq(UserIntegralRecord::getStatus, IntegralRecordConstants.INTEGRAL_RECORD_STATUS_COMPLETE);
        if (ObjectUtil.isNotNull(request.getUid())) {
            lqw.eq(UserIntegralRecord::getUid, request.getUid());
        }
        if (StrUtil.isNotBlank(request.getKeywords())) {
            List<Integer> idList = userService.findIdListLikeName(request.getKeywords());
            if (CollUtil.isNotEmpty(idList)) {
                lqw.in(UserIntegralRecord::getUid, idList);
            } else {
                return CommonPage.copyPageInfo(page, CollUtil.newArrayList());
            }
        }
        //时间范围
        if (StrUtil.isNotBlank(request.getDateLimit())) {
            dateLimitUtilVo dateLimit = DateUtil.getDateLimit(request.getDateLimit());
            //判断时间
            int compareDateResult = DateUtil.compareDate(dateLimit.getEndTime(), dateLimit.getStartTime(), Constants.DATE_FORMAT);
            if (compareDateResult == -1) {
                throw new XlwebException("开始时间不能大于结束时间！");
            }

            lqw.between(UserIntegralRecord::getUpdateTime, dateLimit.getStartTime(), dateLimit.getEndTime());
        }
        lqw.orderByDesc(UserIntegralRecord::getUpdateTime);
        List<UserIntegralRecord> list = dao.selectList(lqw);
        if (CollUtil.isEmpty(list)) {
            return CommonPage.copyPageInfo(page, CollUtil.newArrayList());
        }
        List<UserIntegralRecordResponse> responseList = list.stream().map(i -> {
            UserIntegralRecordResponse response = new UserIntegralRecordResponse();
            BeanUtils.copyProperties(i, response);
            // 获取用户昵称
            User user = userService.getById(i.getUid());
            if (ObjectUtil.isNotNull(user)) {
                response.setNickName(user.getNickname());
            } else {
                response.setNickName("");
            }
            return response;
        }).collect(Collectors.toList());
        return CommonPage.copyPageInfo(page, responseList);
    }

    /**
     * 根据类型条件计算积分总数
     * @param uid 用户uid
     * @param type 类型：1-增加，2-扣减
     * @param date 日期
     * @param linkType 关联类型
     * @return 积分总数
     */
    @Override
    public Integer getSumIntegral(Integer uid, Integer type, String date, String linkType) {
        QueryWrapper<UserIntegralRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("sum(integral) as integral");
        queryWrapper.eq("uid", uid);
        queryWrapper.eq("type", type);
        if (StrUtil.isNotBlank(linkType)) {
            queryWrapper.eq("link_type", linkType);
        }
        queryWrapper.eq("status", IntegralRecordConstants.INTEGRAL_RECORD_STATUS_COMPLETE);
        if (StrUtil.isNotBlank(date)) {
            dateLimitUtilVo dateLimit = DateUtil.getDateLimit(date);
            queryWrapper.between("update_time", dateLimit.getStartTime(), dateLimit.getEndTime());
        }
        UserIntegralRecord integralRecord = dao.selectOne(queryWrapper);
        if (ObjectUtil.isNull(integralRecord) || ObjectUtil.isNull(integralRecord.getIntegral())) {
            return 0;
        }
        return integralRecord.getIntegral();
    }

    /**
     * H5用户积分列表
     * @param uid 用户uid
     * @param pageParamRequest 分页参数
     * @return 记录列表
     */
    @Override
    public List<UserIntegralRecord> findUserIntegralRecordList(Integer uid, PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        LambdaQueryWrapper<UserIntegralRecord> lqw = Wrappers.lambdaQuery();
        lqw.select(UserIntegralRecord::getId, UserIntegralRecord::getTitle, UserIntegralRecord::getType, UserIntegralRecord::getIntegral, UserIntegralRecord::getUpdateTime);
        lqw.eq(UserIntegralRecord::getUid, uid);
        lqw.eq(UserIntegralRecord::getStatus, IntegralRecordConstants.INTEGRAL_RECORD_STATUS_COMPLETE);
        lqw.orderByDesc(UserIntegralRecord::getUpdateTime);
        return dao.selectList(lqw);
    }

    /**
     * 获取用户冻结的积分
     * @param uid 用户uid
     * @return 积分数量
     */
    @Override
    public Integer getFrozenIntegralByUid(Integer uid) {
        QueryWrapper<UserIntegralRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("sum(integral) as integral");
        queryWrapper.eq("uid", uid);
        queryWrapper.eq("type", IntegralRecordConstants.INTEGRAL_RECORD_TYPE_ADD);
        queryWrapper.eq("link_type", IntegralRecordConstants.INTEGRAL_RECORD_LINK_TYPE_ORDER);
        queryWrapper.eq("status", IntegralRecordConstants.INTEGRAL_RECORD_STATUS_FROZEN);
        UserIntegralRecord integralRecord = dao.selectOne(queryWrapper);
        if (ObjectUtil.isNull(integralRecord) || ObjectUtil.isNull(integralRecord.getIntegral())) {
            return 0;
        }
        return integralRecord.getIntegral();
    }

    /**
     * 获取需要解冻的记录列表
     * @return 记录列表
     */
    private List<UserIntegralRecord> findThawList() {
        LambdaQueryWrapper<UserIntegralRecord> lqw = Wrappers.lambdaQuery();
        lqw.le(UserIntegralRecord::getThawTime, System.currentTimeMillis());
        lqw.eq(UserIntegralRecord::getLinkType, IntegralRecordConstants.INTEGRAL_RECORD_LINK_TYPE_ORDER);
        lqw.eq(UserIntegralRecord::getType, IntegralRecordConstants.INTEGRAL_RECORD_TYPE_ADD);
        lqw.eq(UserIntegralRecord::getStatus, IntegralRecordConstants.INTEGRAL_RECORD_STATUS_FROZEN);
        return dao.selectList(lqw);
    }

    @Override
    public MyRecord getIntegralBasic(String time) {
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

            // 当前有效用户的积分总和
            QueryWrapper<User> queryUserWrapper = new QueryWrapper<>();
            queryUserWrapper.select("IFNULL(sum(integral), 0) as integral");
            queryUserWrapper.eq("status", 1);
            int nowPoint = userService.getOne(queryUserWrapper).getIntegral();

            // 指定时间段内的所有积分收入
            QueryWrapper<UserIntegralRecord> queryAddWrapper = new QueryWrapper<>();
            queryAddWrapper.select("IFNULL(sum(integral), 0) as integral");
            queryAddWrapper.eq("type", IntegralRecordConstants.INTEGRAL_RECORD_TYPE_ADD);
            queryAddWrapper.ne("status", IntegralRecordConstants.INTEGRAL_RECORD_STATUS_INVALIDATION);
            queryAddWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
            int allPoint = dao.selectOne(queryAddWrapper).getIntegral();

            // 指定时间段内的所有积分支出
            QueryWrapper<UserIntegralRecord> queryReduceWrapper = new QueryWrapper<>();
            queryReduceWrapper.select("IFNULL(sum(integral), 0) as integral");
            queryReduceWrapper.eq("type", IntegralRecordConstants.INTEGRAL_RECORD_TYPE_SUB);
            queryReduceWrapper.ne("status", IntegralRecordConstants.INTEGRAL_RECORD_STATUS_INVALIDATION);
            queryReduceWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
            int payPoint = dao.selectOne(queryReduceWrapper).getIntegral();

            MyRecord myRecord = new MyRecord();
            myRecord.set("now_point", nowPoint);
            myRecord.set("all_point", allPoint);
            myRecord.set("pay_point", payPoint);
            return myRecord;
        } catch (Exception ex) {
            logger.error("获取积分统计顶部数据失败", ex);
            throw new XlwebException("获取积分统计顶部数据失败");
        }
    }

    @Override
    public MyRecord getIntegralTrend(String time) {
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
            logger.error("获取积分统计折线图数据失败", ex);
            throw new XlwebException("获取积分统计折线图数据失败");
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
        QueryWrapper<UserIntegralRecord> queryAddWrapper = new QueryWrapper<>();
        queryAddWrapper.select("IFNULL(sum(integral), 0) as integral, DATE_FORMAT(update_time, '"+ timeType +"') as days");
        queryAddWrapper.eq("type", IntegralRecordConstants.INTEGRAL_RECORD_TYPE_ADD);
        queryAddWrapper.ne("status", IntegralRecordConstants.INTEGRAL_RECORD_STATUS_INVALIDATION);
        queryAddWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
        queryAddWrapper.groupBy("days");

        List<Map<String, Object>> pointAdd = dao.selectMaps(queryAddWrapper);
        QueryWrapper<UserIntegralRecord> queryReduceWrapper = new QueryWrapper<>();
        queryReduceWrapper.select("IFNULL(sum(integral), 0) as integral, DATE_FORMAT(update_time, '"+ timeType +"') as days");
        queryReduceWrapper.eq("type", IntegralRecordConstants.INTEGRAL_RECORD_TYPE_SUB);
        queryReduceWrapper.ne("status", IntegralRecordConstants.INTEGRAL_RECORD_STATUS_INVALIDATION);
        queryAddWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
        queryReduceWrapper.groupBy("days");
        List<Map<String, Object>> pointSub = dao.selectMaps(queryReduceWrapper);

        Map<String, Float> pointAddMap = pointAdd.stream()
                .collect(Collectors.toMap(
                        item -> (String) item.get("days"),
                        item -> Float.valueOf(((BigDecimal)item.get("integral")).toPlainString())
                ));

        Map<String, Float> pointSubMap = pointSub.stream()
                .collect(Collectors.toMap(
                        item -> (String) item.get("days"),
                        item -> Float.valueOf(((BigDecimal)item.get("integral")).toPlainString())
                ));

        Map<String, List<Float>> data = new HashMap<>();
        data.put("积分积累", new ArrayList<>());
        data.put("积分消耗", new ArrayList<>());

        for (String item : xAxis) {
            data.get("积分积累").add(pointAddMap.getOrDefault(item, 0.0f));
            data.get("积分消耗").add(pointSubMap.getOrDefault(item, 0.0f));
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
    public MyRecord getIntegralChannel(String time) {
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

            List<String> bingXdata = Arrays.asList("订单赠送", "商品赠送", "后台赠送", "签到获得");
            List<String> color = Arrays.asList("#64a1f4", "#3edeb5", "#70869f", "#ffc653");
            List<String> data = Arrays.asList("order", "product", "system", "sign");
            List<Map<String, Object>> bingData = new ArrayList<>();

            for (int key = 0; key < data.size(); key++) {
                String item = data.get(key);
                // 增加的积分
                SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
                SimpleDateFormat sdfEndFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
                QueryWrapper<UserIntegralRecord> queryAddWrapper = new QueryWrapper<>();
                queryAddWrapper.select("IFNULL(sum(integral), 0) as integral");
                queryAddWrapper.eq("link_type", item);
                queryAddWrapper.ne("status", IntegralRecordConstants.INTEGRAL_RECORD_STATUS_INVALIDATION);
                queryAddWrapper.eq("type", IntegralRecordConstants.INTEGRAL_RECORD_TYPE_ADD);
                queryAddWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
                int addValue = dao.selectOne(queryAddWrapper).getIntegral();

                Map<String, Object> recordData = new HashMap<>();
                recordData.put("name", bingXdata.get(key));
                recordData.put("value", addValue);
                Map<String, Object> itemStyle = new HashMap<>();
                itemStyle.put("color", color.get(key));
                recordData.put("itemStyle", itemStyle);
                bingData.add(recordData);
            }

            List<Map<String, Object>> list = new ArrayList<>();
            int count = bingData.stream().mapToInt(item -> (Integer) item.get("value")).sum();

            for (Map<String, Object> item : bingData) {
                int value = (Integer) item.get("value");
                double percent = count != 0 ?
                        new BigDecimal(value)
                                .divide(new BigDecimal(count), 4, BigDecimal.ROUND_HALF_UP)
                                .multiply(new BigDecimal(100))
                                .setScale(1, BigDecimal.ROUND_HALF_UP)
                                .doubleValue() : 0;
                Map<String, Object> recordData = new HashMap<>();
                recordData.put("name", item.get("name"));
                recordData.put("value", value);
                recordData.put("percent", percent);
                list.add(recordData);
            }

            list.sort(Comparator.comparingInt(item -> -(Integer) item.get("value")));

            MyRecord myRecord = new MyRecord();
            myRecord.set("bing_xdata", bingXdata);
            myRecord.set("bing_data", bingData);
            myRecord.set("list", list);

            return myRecord;
        } catch (Exception ex) {
            logger.error("获取积分来源分析数据失败", ex);
            throw new XlwebException("获取积分来源分析数据失败");
        }
    }

    @Override
    public MyRecord getIntegralType(String time) {
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

            List<String> bingXdata = Arrays.asList("订单抵扣", "后台减少");
            List<String> color = Arrays.asList("#64a1f4", "#70869f");
            List<String> data = Arrays.asList("order", "system");
            List<Map<String, Object>> bingData = new ArrayList<>();

            for (int key = 0; key < data.size(); key++) {
                String item = data.get(key);
                // 消耗的积分
                SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
                SimpleDateFormat sdfEndFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
                QueryWrapper<UserIntegralRecord> queryReduceWrapper = new QueryWrapper<>();
                queryReduceWrapper.select("IFNULL(sum(integral), 0) as integral");
                queryReduceWrapper.eq("link_type", item);
                queryReduceWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
                queryReduceWrapper.ne("status", IntegralRecordConstants.INTEGRAL_RECORD_STATUS_INVALIDATION);
                queryReduceWrapper.eq("type", IntegralRecordConstants.INTEGRAL_RECORD_TYPE_SUB);
                int addValue = dao.selectOne(queryReduceWrapper).getIntegral();

                Map<String, Object> recordData = new HashMap<>();
                recordData.put("name", bingXdata.get(key));
                recordData.put("value", addValue);
                Map<String, Object> itemStyle = new HashMap<>();
                itemStyle.put("color", color.get(key));
                recordData.put("itemStyle", itemStyle);
                bingData.add(recordData);
            }

            List<Map<String, Object>> list = new ArrayList<>();
            int count = bingData.stream().mapToInt(item -> (Integer) item.get("value")).sum();

            for (Map<String, Object> item : bingData) {
                int value = (Integer) item.get("value");
                double percent = count != 0 ?
                        new BigDecimal(value)
                                .divide(new BigDecimal(count), 4, BigDecimal.ROUND_HALF_UP)
                                .multiply(new BigDecimal(100))
                                .setScale(1, BigDecimal.ROUND_HALF_UP)
                                .doubleValue() : 0;
                Map<String, Object> recordData = new HashMap<>();
                recordData.put("name", item.get("name"));
                recordData.put("value", value);
                recordData.put("percent", percent);
                list.add(recordData);
            }

            list.sort(Comparator.comparingInt(item -> -(Integer) item.get("value")));

            MyRecord myRecord = new MyRecord();
            myRecord.set("bing_xdata", bingXdata);
            myRecord.set("bing_data", bingData);
            myRecord.set("list", list);

            return myRecord;
        } catch (Exception ex) {
            logger.error("获取积分消耗分析数据失败", ex);
            throw new XlwebException("获取积分消耗分析数据失败");
        }
    }

}

