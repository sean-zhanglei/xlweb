package com.nbug.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbug.common.request.PageParamRequest;
import com.nbug.common.model.express.ShippingTemplates;
import com.nbug.common.request.ShippingTemplatesRequest;
import com.nbug.common.request.ShippingTemplatesSearchRequest;

import java.util.List;

/**
* ShippingTemplatesService 接口

*/
public interface ShippingTemplatesService extends IService<ShippingTemplates> {

    List<ShippingTemplates> getList(ShippingTemplatesSearchRequest request, PageParamRequest pageParamRequest);

    /**
     * 新增运费模板
     * @param request 请求参数
     * @return 新增结果
     */
    Boolean create(ShippingTemplatesRequest request);

    Boolean update(Integer id, ShippingTemplatesRequest request);

    /**
     * 删除模板
     * @param id 模板id
     * @return Boolean
     */
    Boolean remove(Integer id);

    /**
     * 获取模板信息
     * @param id 模板id
     * @return ShippingTemplates
     */
    ShippingTemplates getInfo(Integer id);
}
