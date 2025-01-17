package com.nbug.service.dao;

import com.nbug.common.request.StoreNearRequest;
import com.nbug.common.vo.SystemStoreNearVo;
import com.nbug.common.model.system.SystemStore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 门店自提 Mapper 接口

 */
public interface SystemStoreDao extends BaseMapper<SystemStore> {

    List<SystemStoreNearVo> getNearList(StoreNearRequest request);
}

