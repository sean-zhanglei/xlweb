package com.nbug.module.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.nbug.mico.common.constants.SysGroupDataConstants;
import com.nbug.mico.common.model.system.SystemGroupData;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.SystemFormItemCheckRequest;
import com.nbug.mico.common.request.SystemGroupDataRequest;
import com.nbug.mico.common.request.SystemGroupDataSearchRequest;
import com.nbug.mico.common.utils.XlwebUtil;
import com.nbug.module.infra.api.attachment.AttachmentApi;
import com.nbug.module.system.dal.SystemGroupDataDao;
import com.nbug.module.system.service.SystemFormTempService;
import com.nbug.module.system.service.SystemGroupDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * SystemGroupDataServiceImpl 接口实现

 */
@Service
public class SystemGroupDataServiceImpl extends ServiceImpl<SystemGroupDataDao, SystemGroupData> implements SystemGroupDataService {

    @Resource
    private SystemGroupDataDao dao;

    @Autowired
    private SystemFormTempService systemFormTempService;

    @Autowired
    private AttachmentApi attachmentApi;

    /**
    * 列表
    * @param request 请求参数
    * @param pageParamRequest 分页类参数
    * @return List<SystemGroupData>
    */
    @Override
    public List<SystemGroupData> getList(SystemGroupDataSearchRequest request, PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());

        //带 SystemGroupData 类的多条件查询
        LambdaQueryWrapper<SystemGroupData> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        SystemGroupData model = new SystemGroupData();
        BeanUtils.copyProperties(request, model);
        lambdaQueryWrapper.setEntity(model);
        lambdaQueryWrapper.orderByAsc(SystemGroupData::getSort).orderByAsc(SystemGroupData::getId);
        return dao.selectList(lambdaQueryWrapper);
    }

    /**
     * 保存数据
     * @param systemGroupDataRequest SystemGroupDataRequest 数据保存
     * @return Boolean
     */
    @Override
    public Boolean create(SystemGroupDataRequest systemGroupDataRequest) {
        //检测form表单，并且返回需要添加的数据
        systemFormTempService.checkForm(systemGroupDataRequest.getForm());

        SystemGroupData systemGroupData = new SystemGroupData();
        systemGroupData.setGid(systemGroupDataRequest.getGid());

        String value = JSONObject.toJSONString(systemGroupDataRequest.getForm());
        value = attachmentApi.clearPrefix(value).getCheckedData();
        systemGroupData.setValue(value);
        systemGroupData.setSort(systemGroupDataRequest.getForm().getSort());
        systemGroupData.setStatus(systemGroupDataRequest.getForm().getStatus());
        return save(systemGroupData);
    }

    /**
     * 修改组合数据详情表
     * @param id integer id
     * @param request 修改参数
     * @return bool
     */
    @Override
    public Boolean update(Integer id, SystemGroupDataRequest request) {
        //检测form表单，并且返回需要添加的数据
        systemFormTempService.checkForm(request.getForm());

        SystemGroupData systemGroupData = new SystemGroupData();
        systemGroupData.setId(id);
        systemGroupData.setGid(request.getGid());

        String value = JSONObject.toJSONString(request.getForm());
        value = attachmentApi.clearPrefix(value).getCheckedData();
        systemGroupData.setValue(value);
        systemGroupData.setSort(request.getForm().getSort());
        systemGroupData.setStatus(request.getForm().getStatus());
        return updateById(systemGroupData);
    }

    /**
     * 通过gid获取列表 推荐二开使用
     * @param gid Integer group id
     * @return List<T>
     */
    @Override
    public <T> List<T> getListByGid(Integer gid, Class<T> cls) {
        SystemGroupDataSearchRequest systemGroupDataSearchRequest = new SystemGroupDataSearchRequest();
        systemGroupDataSearchRequest.setGid(gid);
        systemGroupDataSearchRequest.setStatus(true);
        List<SystemGroupData> list = getList(systemGroupDataSearchRequest, new PageParamRequest());

        List<T> arrayList = new ArrayList<>();
        if (list.size() < 1) {
            return null;
        }

        for (SystemGroupData systemGroupData : list) {
            JSONObject jsonObject = JSONObject.parseObject(systemGroupData.getValue());
            List<SystemFormItemCheckRequest> systemFormItemCheckRequestList = XlwebUtil.jsonToListClass(jsonObject.getString("fields"), SystemFormItemCheckRequest.class);
            if (systemFormItemCheckRequestList.size() < 1) {
                continue;
            }
            HashMap<String, Object> map = new HashMap<>();
            T t;
            for (SystemFormItemCheckRequest systemFormItemCheckRequest : systemFormItemCheckRequestList) {
                map.put(systemFormItemCheckRequest.getName(), systemFormItemCheckRequest.getValue());
            }
            map.put("id", systemGroupData.getId());
            t = XlwebUtil.mapToObj(map, cls);
            arrayList.add(t);
        }

        return arrayList;
    }

    /**
      * 通过gid获取列表
      * @param gid Integer group id
      * @author Mr.Zhang
      * @since 2020-05-15
      * @return List<HashMap<String, Object>>
      */
    @Override
    public List<HashMap<String, Object>> getListMapByGid(Integer gid) {
        SystemGroupDataSearchRequest systemGroupDataSearchRequest = new SystemGroupDataSearchRequest();
        systemGroupDataSearchRequest.setGid(gid);
        systemGroupDataSearchRequest.setStatus(true);
        List<SystemGroupData> list = getList(systemGroupDataSearchRequest, new PageParamRequest());

        List<HashMap<String, Object>> arrayList = new ArrayList<>();
        if (list.size() < 1) {
            return null;
        }

        for (SystemGroupData systemGroupData : list) {
            JSONObject jsonObject = JSONObject.parseObject(systemGroupData.getValue());
            List<SystemFormItemCheckRequest> systemFormItemCheckRequestList = XlwebUtil.jsonToListClass(jsonObject.getString("fields"), SystemFormItemCheckRequest.class);
            if (systemFormItemCheckRequestList.size() < 1) {
                continue;
            }
            HashMap<String, Object> map = new HashMap<>();

            for (SystemFormItemCheckRequest systemFormItemCheckRequest : systemFormItemCheckRequestList) {
                map.put(systemFormItemCheckRequest.getName(), systemFormItemCheckRequest.getValue());
            }
            map.put("id", systemGroupData.getId());
            arrayList.add(map);
        }

        return arrayList;
    }

    /**
     * 通过gid获取列表
     * @param groupDataId Integer group id
     * @return <T>
     */
    public <T> T getNormalInfo(Integer groupDataId, Class<T> cls) {
        SystemGroupData systemGroupData = getById(groupDataId);
        if (null == systemGroupData || !systemGroupData.getStatus()) {
            return null;
        }

        JSONObject jsonObject = JSONObject.parseObject(systemGroupData.getValue());
        List<SystemFormItemCheckRequest> systemFormItemCheckRequestList = XlwebUtil.jsonToListClass(jsonObject.getString("fields"), SystemFormItemCheckRequest.class);
        if (systemFormItemCheckRequestList.size() < 1) {
            return null;
        }
        HashMap<String, Object> map = new HashMap<>();
        T t;
        for (SystemFormItemCheckRequest systemFormItemCheckRequest : systemFormItemCheckRequestList) {
            map.put(systemFormItemCheckRequest.getName(), systemFormItemCheckRequest.getValue());
        }
        map.put("id", systemGroupData.getId());
        t = XlwebUtil.mapToObj(map, cls);

        return t;
    }

    /**
     * 获取个人中心菜单
     * @return HashMap<String, Object>
     */
    @Override
    public HashMap<String, Object> getMenuUser() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("routine_my_menus", getListMapByGid(SysGroupDataConstants.GROUP_DATA_ID_USER_CENTER_MENU));
        map.put("routine_my_banner", getListMapByGid(SysGroupDataConstants.GROUP_DATA_ID_USER_CENTER_BANNER));
        return map;
    }

    /**
     * 获取列表通过gid
     * @param gid gid
     * @return 列表
     */
    @Override
    public List<SystemGroupData> findListByGid(Integer gid) {
        LambdaQueryWrapper<SystemGroupData> lqw = Wrappers.lambdaQuery();
        lqw.eq(SystemGroupData::getGid, gid);
        lqw.orderByAsc(SystemGroupData::getSort).orderByAsc(SystemGroupData::getId);
        return dao.selectList(lqw);
    }

    /**
     * 删除通过gid
     * @param gid gid
     * @return Boolean
     */
    @Override
    public Boolean deleteByGid(Integer gid) {
        LambdaUpdateWrapper<SystemGroupData> luw = Wrappers.lambdaUpdate();
        luw.eq(SystemGroupData::getGid, gid);
        int delete = dao.delete(luw);
        if (delete > 0) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}

