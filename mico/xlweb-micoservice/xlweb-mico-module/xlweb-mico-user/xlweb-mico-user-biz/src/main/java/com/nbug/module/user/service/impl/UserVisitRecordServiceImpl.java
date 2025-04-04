package com.nbug.module.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nbug.mico.common.model.record.UserVisitRecord;
import com.nbug.module.user.dal.UserVisitRecordDao;
import com.nbug.module.user.service.UserVisitRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * UserVisitRecordServiceImpl 接口实现

 */
@Service
public class UserVisitRecordServiceImpl extends ServiceImpl<UserVisitRecordDao, UserVisitRecord> implements UserVisitRecordService {

    @Resource
    private UserVisitRecordDao dao;

    /**
     * 通过日期获取浏览量
     * @param date 日期
     * @return Integer
     */
    @Override
    public Integer getPageviewsByDate(String date) {
        QueryWrapper<UserVisitRecord> wrapper = new QueryWrapper<>();
        wrapper.select("id");
        wrapper.eq("date", date);
        return Math.toIntExact(dao.selectCount(wrapper));
    }

    /**
     * 通过时间段获取浏览量
     * @param startDate 日期
     * @param endDate 日期
     * @return Integer
     */
    @Override
    public Integer getPageviewsByPeriod(String startDate, String endDate) {
        QueryWrapper<UserVisitRecord> wrapper = new QueryWrapper<>();
        wrapper.select("id");
        wrapper.between("date", startDate, endDate);
        return Math.toIntExact(dao.selectCount(wrapper));
    }

    /**
     * 通过日期获取活跃用户数
     * @param date 日期
     * @return Integer
     */
    @Override
    public Integer getActiveUserNumByDate(String date) {
        return dao.getActiveUserNumByDate(date);
    }

    /**
     * 通过时间段获取活跃用户数
     * @param startDate 日期
     * @param endDate 日期
     * @return Integer
     */
    @Override
    public Integer getActiveUserNumByPeriod(String startDate, String endDate) {
        return dao.getActiveUserNumByPeriod(startDate, endDate);
    }
}

