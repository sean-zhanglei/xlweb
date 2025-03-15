package com.nbug.module.system.dal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nbug.mico.common.model.system.SystemStore;
import com.nbug.mico.common.request.StoreNearRequest;
import com.nbug.mico.common.vo.SystemStoreNearVo;

import java.util.List;

/**
 * 门店自提 Mapper 接口

 */
public interface SystemStoreDao extends BaseMapper<SystemStore> {

    List<SystemStoreNearVo> getNearList(StoreNearRequest request);
}

