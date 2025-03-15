package com.nbug.module.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbug.mico.common.model.user.UserExperienceRecord;
import com.nbug.mico.common.request.PageParamRequest;

import java.util.List;

/**
 * 用户经验记录服务接口

 */
public interface UserExperienceRecordService extends IService<UserExperienceRecord> {

    /**
     * 获取用户经验列表（移动端）
     * @param userId 用户id
     * @param pageParamRequest 分页参数
     * @return List
     */
    List<UserExperienceRecord> getH5List(Integer userId, PageParamRequest pageParamRequest);

    /**
     * 通过订单编号获取记录
     * @param orderNo 订单编号
     * @param uid uid
     * @return UserExperienceRecord
     */
    UserExperienceRecord getByOrderNoAndUid(String orderNo, Integer uid);
}
