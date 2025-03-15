package com.nbug.module.system.module.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nbug.common.model.system.SystemStore;
import com.nbug.common.request.StoreNearRequest;
import com.nbug.common.vo.SystemStoreNearVo;

import java.util.List;

/**
 * 门店自提 Mapper 接口

 */
public interface SystemStoreDao extends BaseMapper<SystemStore> {

    List<SystemStoreNearVo> getNearList(StoreNearRequest request);
}

