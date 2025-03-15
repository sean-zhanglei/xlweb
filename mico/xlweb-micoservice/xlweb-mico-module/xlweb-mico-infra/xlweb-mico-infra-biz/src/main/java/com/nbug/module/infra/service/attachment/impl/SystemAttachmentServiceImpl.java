package com.nbug.module.infra.service.attachment.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.model.system.SystemAttachment;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.SystemAttachmentMoveRequest;
import com.nbug.mico.common.request.SystemAttachmentRequest;
import com.nbug.mico.common.utils.XlwebUtil;
import com.nbug.module.infra.dal.SystemAttachmentDao;
import com.nbug.module.infra.service.attachment.SystemAttachmentService;
import com.nbug.module.system.api.config.ConfigApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * SystemAttachmentServiceImpl 接口实现

 */
@Service
public class SystemAttachmentServiceImpl extends ServiceImpl<SystemAttachmentDao, SystemAttachment>
        implements SystemAttachmentService {

    @Resource
    private SystemAttachmentDao dao;

    @Autowired
    private ConfigApi configApi;

    /**
     * 同步到云服务， 更新图片上传类型
     * @param attId Integer 主键id
     * @param type int 图片上传类型 1本地 2七牛云 3OSS 4COS
     */
    @Override
    public void updateCloudType(Integer attId, int type) {
        SystemAttachment systemAttachment = new SystemAttachment();
        systemAttachment.setAttId(attId);
        systemAttachment.setImageType(type);
        updateById(systemAttachment);
    }

    /**
     * 附件分页
     * @param pid Integer pid
     * @param pageParamRequest PageParamRequest 分页参数
     * @return List<SystemAttachment>
     */
    @Override
    public List<SystemAttachment> getList(Integer pid, String attType, PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        LambdaQueryWrapper<SystemAttachment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SystemAttachment::getPid, pid);
        if(StringUtils.isNotEmpty(attType)){
            lambdaQueryWrapper.in(SystemAttachment::getAttType, StringUtils.split(attType,","));
        }
        lambdaQueryWrapper.orderByDesc(SystemAttachment::getAttId);
        return dao.selectList(lambdaQueryWrapper);
    }

    /**
     * 给图片加前缀
     * @param path String 路径
     * @return String
     */
    @Override
    public String prefixImage(String path) {
        // 如果那些域名不需要加，则跳过
        return path.replace(Constants.UPLOAD_TYPE_IMAGE+"/", getCdnUrl() + "/"+ Constants.UPLOAD_TYPE_IMAGE+"/");
    }

    /**
     * 给文件加前缀
     * @param path String 路径
     * @return String
     */
    @Override
    public String prefixFile(String path) {
        if (path.contains("file/excel")) {
            String cdnUrl = configApi.getValueByKey("local" + "UploadUrl").getCheckedData();
            return path.replace("file/", cdnUrl + "/file/");
        }
        return path.replace("file/", getCdnUrl() + "/file/");
    }

    /**
     * 获取cdn url
     * @return String
     */
    private String getCdnUrl() {
//        return asyncService.getCurrentBaseUrl();
        String uploadType = configApi.getValueByKeyException("uploadType").getCheckedData();
        //获取配置信息
        int type = Integer.parseInt(uploadType);
        String pre = "local";
        switch (type) {
            case 2:
                pre = "qn";
                break;
            case 3:
                pre = "al";
                break;
            case 4:
                pre = "tx";
                break;
            default:
                break;
        }
        return configApi.getValueByKey(pre+"UploadUrl").getCheckedData();
    }

    /**
     * 清除 cdn url， 在保存数据的时候使用
     * @param path String 文件路径
     * @return String
     */
    @Override
    public String clearPrefix(String path) {
        if(StringUtils.isBlank(path)){
            return path;
        }
        Constants.CND_URL = getCdnUrl();
        if(path.contains(getCdnUrl() + "/")){
            return path.replace(getCdnUrl() + "/", "");
        }

        return path;
    }

    /**
     * 新增附件
     * @param systemAttachmentRequest 新增参数
     */
    @Override
    public Boolean add(SystemAttachmentRequest systemAttachmentRequest) {
        SystemAttachment systemAttachment = new SystemAttachment();
        BeanUtils.copyProperties(systemAttachmentRequest, systemAttachment);
        return save(systemAttachment);
    }

    /**
     * 编辑附件
     * @param systemAttachmentRequest 更新参数
     */
    @Override
    public Boolean edit(SystemAttachmentRequest systemAttachmentRequest) {
        SystemAttachment systemAttachment = new SystemAttachment();
        BeanUtils.copyProperties(systemAttachmentRequest, systemAttachment);
        return updateById(systemAttachment);
    }

    /**
     * 更改图片目录
     * @param move 参数
     */
    @Override
    public Boolean updateAttrId(SystemAttachmentMoveRequest move) {
        LambdaUpdateWrapper<SystemAttachment> lup = new LambdaUpdateWrapper<>();
        lup.in(SystemAttachment::getAttId, XlwebUtil.stringToArray(move.getAttrId()));
        lup.set(SystemAttachment::getPid, move.getPid());
        return update(lup);
    }
}

