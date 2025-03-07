package com.nbug.service.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nbug.common.request.PageParamRequest;
import com.nbug.common.model.express.Express;
import com.nbug.common.request.ExpressSearchRequest;
import com.nbug.common.request.ExpressUpdateRequest;
import com.nbug.common.request.ExpressUpdateShowRequest;

import java.util.List;

/**
*  ExpressService 接口

*/
public interface ExpressService extends IService<Express> {

    /**
    * 列表
    * @param request 搜索条件
    * @param pageParamRequest 分页类参数
    * @return List<Express>
    */
    List<Express> getList(ExpressSearchRequest request, PageParamRequest pageParamRequest);

    /**
     * 编辑
     */
    Boolean updateExpress(ExpressUpdateRequest expressRequest);

    /**
     * 修改显示状态
     */
    Boolean updateExpressShow(ExpressUpdateShowRequest expressRequest);

    /**
     * 查询全部快递公司
     * @param type 类型：normal-普通，elec-电子面单
     */
    List<Express> findAll(String type);

    /**
     * 查询快递公司面单模板
     * @param com 快递公司编号
     */
    JSONObject template(String com);

    /**
     * 查询快递公司
     * @param code 快递公司编号
     * @return Express
     */
    Express getByCode(String code);

    /**
     * 通过物流公司名称获取
     * @param name 物流公司名称
     */
    Express getByName(String name);

    /**
     * 获取快递公司详情
     * @param id 快递公司id
     */
    Express getInfo(Integer id);
}
