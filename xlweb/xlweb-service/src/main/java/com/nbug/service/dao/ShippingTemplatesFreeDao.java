package com.nbug.service.dao;

import com.nbug.common.model.express.ShippingTemplatesFree;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nbug.common.request.ShippingTemplatesFreeRequest;

import java.util.List;

/**
 *  Mapper 接口
 
 */
public interface ShippingTemplatesFreeDao extends BaseMapper<ShippingTemplatesFree> {

    List<ShippingTemplatesFreeRequest> getListGroup(Integer tempId);
}
