package com.nbug.module.system.dal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nbug.mico.common.model.express.ShippingTemplatesFree;
import com.nbug.mico.common.request.ShippingTemplatesFreeRequest;

import java.util.List;

/**
 *  Mapper 接口
 
 */
public interface ShippingTemplatesFreeDao extends BaseMapper<ShippingTemplatesFree> {

    List<ShippingTemplatesFreeRequest> getListGroup(Integer tempId);
}
