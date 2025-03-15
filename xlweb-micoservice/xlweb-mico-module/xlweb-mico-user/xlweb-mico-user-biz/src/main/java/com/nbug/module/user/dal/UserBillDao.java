package com.nbug.module.user.dal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nbug.mico.common.model.user.UserBill;
import com.nbug.mico.common.response.UserBillResponse;

import java.util.List;
import java.util.Map;

/**
 * 用户账单表 Mapper 接口

 */
public interface UserBillDao extends BaseMapper<UserBill> {

    List<UserBillResponse> fundMonitoring(Map<String, Object> map);
}
