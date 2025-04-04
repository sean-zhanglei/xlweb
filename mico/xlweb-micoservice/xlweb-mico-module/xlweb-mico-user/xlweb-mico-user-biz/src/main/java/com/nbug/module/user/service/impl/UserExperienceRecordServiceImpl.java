package com.nbug.module.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.nbug.mico.common.constants.ExperienceRecordConstants;
import com.nbug.mico.common.model.user.UserExperienceRecord;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.module.user.dal.UserExperienceRecordDao;
import com.nbug.module.user.service.UserExperienceRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户经验记录服务接口实现类

 */
@Service
public class UserExperienceRecordServiceImpl extends ServiceImpl<UserExperienceRecordDao, UserExperienceRecord> implements UserExperienceRecordService {

    @Resource
    private UserExperienceRecordDao dao;

    /**
     * 获取用户经验列表（移动端）
     * @param userId 用户id
     * @param pageParamRequest 分页参数
     * @return List
     */
    @Override
    public List<UserExperienceRecord> getH5List(Integer userId, PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        LambdaQueryWrapper<UserExperienceRecord> lqw = Wrappers.lambdaQuery();
        lqw.select(UserExperienceRecord::getId, UserExperienceRecord::getTitle, UserExperienceRecord::getType, UserExperienceRecord::getExperience, UserExperienceRecord::getCreateTime);
        lqw.eq(UserExperienceRecord::getUid, userId);
        lqw.orderByDesc(UserExperienceRecord::getId);
        return dao.selectList(lqw);
    }

    /**
     * 通过订单编号获取记录
     * @param orderNo 订单编号
     * @param uid uid
     * @return UserExperienceRecord
     */
    @Override
    public UserExperienceRecord getByOrderNoAndUid(String orderNo, Integer uid) {
        LambdaQueryWrapper<UserExperienceRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(UserExperienceRecord::getLinkId, orderNo);
        lqw.eq(UserExperienceRecord::getLinkType, ExperienceRecordConstants.EXPERIENCE_RECORD_LINK_TYPE_ORDER);
        lqw.eq(UserExperienceRecord::getUid, uid);
        return dao.selectOne(lqw);
    }
}

