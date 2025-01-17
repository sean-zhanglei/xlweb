package com.nbug.service.dao;

import com.nbug.common.model.express.ShippingTemplatesRegion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nbug.common.request.ShippingTemplatesRegionRequest;

import java.util.List;

/**
 *  Mapper 接口

 */
public interface ShippingTemplatesRegionDao extends BaseMapper<ShippingTemplatesRegion> {

    List<ShippingTemplatesRegionRequest> getListGroup(Integer tempId);
}
