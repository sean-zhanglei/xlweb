package com.nbug.service.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nbug.common.request.PageParamRequest;
import com.nbug.common.exception.XlwebException;
import com.github.pagehelper.PageHelper;
import com.nbug.common.model.express.ShippingTemplates;
import com.nbug.common.request.ShippingTemplatesFreeRequest;
import com.nbug.common.request.ShippingTemplatesRegionRequest;
import com.nbug.common.request.ShippingTemplatesRequest;
import com.nbug.common.request.ShippingTemplatesSearchRequest;
import com.nbug.service.dao.ShippingTemplatesDao;
import com.nbug.service.service.ShippingTemplatesFreeService;
import com.nbug.service.service.ShippingTemplatesRegionService;
import com.nbug.service.service.ShippingTemplatesService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
* ShippingTemplatesServiceImpl 接口实现

*/
@Service
public class ShippingTemplatesServiceImpl extends ServiceImpl<ShippingTemplatesDao, ShippingTemplates> implements ShippingTemplatesService {

    @Resource
    private ShippingTemplatesDao dao;

    @Autowired
    private ShippingTemplatesRegionService shippingTemplatesRegionService;

    @Autowired
    private ShippingTemplatesFreeService shippingTemplatesFreeService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
    * 列表
    * @param request 请求参数
    * @param pageParamRequest 分页类参数
    * @author Mr.Zhang
    * @since 2020-04-17
    * @return List<ShippingTemplates>
    */
    @Override
    public List<ShippingTemplates> getList(ShippingTemplatesSearchRequest request, PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        LambdaQueryWrapper<ShippingTemplates> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isBlank(request.getKeywords())){
            lambdaQueryWrapper.like(ShippingTemplates::getName, request.getKeywords());
        }
        lambdaQueryWrapper.orderByDesc(ShippingTemplates::getSort).orderByDesc(ShippingTemplates::getId);
        return dao.selectList(lambdaQueryWrapper);
    }

    /**
     * 新增
     * @param request 新增参数
     * @author Mr.Zhang
     * @since 2020-04-17
     * @return bool
     */
    @Override
    public Boolean create(ShippingTemplatesRequest request) {
        // 判断模板名称是否重复
        if (isExistName(request.getName())) {
            throw new XlwebException("模板名称已存在,请更换模板名称!");
        }
        List<ShippingTemplatesRegionRequest> shippingTemplatesRegionRequestList = request.getShippingTemplatesRegionRequestList();
        if (CollUtil.isEmpty(shippingTemplatesRegionRequestList)) {
            throw new XlwebException("区域运费最少需要一条默认的全国区域");
        }

        ShippingTemplates shippingTemplates = new ShippingTemplates();
        shippingTemplates.setName(request.getName());
        shippingTemplates.setSort(request.getSort());
        shippingTemplates.setType(request.getType());
        shippingTemplates.setAppoint(request.getAppoint());

        save(shippingTemplates);

        //区域运费
        shippingTemplatesRegionService.saveAll(shippingTemplatesRegionRequestList, request.getType(), shippingTemplates.getId());


        List<ShippingTemplatesFreeRequest> shippingTemplatesFreeRequestList = request.getShippingTemplatesFreeRequestList();
        if(null != shippingTemplatesFreeRequestList && shippingTemplatesFreeRequestList.size() > 0 && request.getAppoint()){
            shippingTemplatesFreeService.saveAll(shippingTemplatesFreeRequestList, request.getType(), shippingTemplates.getId());
        }

        return true;
    }

    /**
     * 根据模板名称获取模板
     * @param name 模板名称
     * @return ShippingTemplates
     */
    private ShippingTemplates getByName(String name) {
        LambdaQueryWrapper<ShippingTemplates> lqw = new LambdaQueryWrapper<>();
        lqw.in(ShippingTemplates::getName, name);
        return dao.selectOne(lqw);
    }

    /**
     * 是否存在模板名称
     */
    private Boolean isExistName(String name) {
        ShippingTemplates templates = getByName(name);
        if (ObjectUtil.isNull(templates)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 新增
     * @param id Integer 模板id
     * @param request ShippingTemplatesRequest 新增参数
     */
    @Override
    public Boolean update(Integer id, ShippingTemplatesRequest request) {
        ShippingTemplates shippingTemplates = new ShippingTemplates();
        shippingTemplates.setId(id);
        shippingTemplates.setName(request.getName());
        shippingTemplates.setSort(request.getSort());
        shippingTemplates.setType(request.getType());
        shippingTemplates.setAppoint(request.getAppoint());


        updateById(shippingTemplates);

        //区域运费
        List<ShippingTemplatesRegionRequest> shippingTemplatesRegionRequestList = request.getShippingTemplatesRegionRequestList();

        if(shippingTemplatesRegionRequestList.size() < 1){
            throw new XlwebException("请设置区域配送信息！");
        }
        shippingTemplatesRegionService.saveAll(shippingTemplatesRegionRequestList, request.getType(), shippingTemplates.getId());

        List<ShippingTemplatesFreeRequest> shippingTemplatesFreeRequestList = request.getShippingTemplatesFreeRequestList();
        if(CollUtil.isNotEmpty(shippingTemplatesFreeRequestList) && request.getAppoint()){
            shippingTemplatesFreeService.saveAll(shippingTemplatesFreeRequestList, request.getType(), shippingTemplates.getId());
        }

        return true;
    }

    /**
     * 删除
     * @param id Integer
     * @return boolean
     */
    @Override
    public Boolean remove(Integer id) {
        return transactionTemplate.execute(e -> {
            shippingTemplatesRegionService.delete(id);
            shippingTemplatesFreeService.delete(id);
            removeById(id);
            return Boolean.TRUE;
        });
    }

    /**
     * 获取模板信息
     * @param id 模板id
     * @return ShippingTemplates
     */
    @Override
    public ShippingTemplates getInfo(Integer id) {
        ShippingTemplates template = getById(id);
        if (ObjectUtil.isNull(template)) {
            throw new XlwebException("模板不存在");
        }
        return template;
    }

}

