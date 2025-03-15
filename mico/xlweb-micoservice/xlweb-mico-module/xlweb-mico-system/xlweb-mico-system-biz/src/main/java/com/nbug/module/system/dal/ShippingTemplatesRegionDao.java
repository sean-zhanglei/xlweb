package com.nbug.module.system.dal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nbug.mico.common.model.express.ShippingTemplatesRegion;
import com.nbug.mico.common.request.ShippingTemplatesRegionRequest;

import java.util.List;

/**
 *  Mapper 接口

 */
public interface ShippingTemplatesRegionDao extends BaseMapper<ShippingTemplatesRegion> {

    List<ShippingTemplatesRegionRequest> getListGroup(Integer tempId);
}
