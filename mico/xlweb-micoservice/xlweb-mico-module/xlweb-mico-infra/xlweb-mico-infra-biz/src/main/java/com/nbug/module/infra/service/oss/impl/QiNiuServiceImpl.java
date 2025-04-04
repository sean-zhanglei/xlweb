package com.nbug.module.infra.service.oss.impl;

import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.vo.CloudVo;
import com.nbug.module.infra.service.attachment.SystemAttachmentService;
import com.nbug.module.infra.service.oss.QiNiuService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;


/**
 * AsyncServiceImpl 同步到云服务

 */
@Service
public class QiNiuServiceImpl implements QiNiuService {
    private static final Logger logger = LoggerFactory.getLogger(QiNiuServiceImpl.class);

    @Lazy
    @Autowired
    private SystemAttachmentService systemAttachmentService;

    /**
     * 同步到七牛云
     * @param cloudVo CloudVo
     * @author Mr.Zhang
     * @since 2020-05-06
     */
    @Async
    @Override
    public void upload(UploadManager uploadManager, CloudVo cloudVo, String upToken, String webPth, String localFile, Integer id) {
        try {
            logger.info("上传文件" + id + "开始：" + localFile);

            File file = new File(localFile);
            if(!file.exists()){
                logger.info("上传文件"+ id + localFile + "不存在：");
                return;
            }

            Response put = uploadManager.put(localFile, webPth, upToken);
            put.close();
            logger.info("上传文件" + id + " -- 结束：" + put.address);
            //更新数据库
            systemAttachmentService.updateCloudType(id, 2);
        } catch (QiniuException ex) {
            throw new XlwebException(ex.getMessage());
        }
    }

    @Override
    public void uploadFile(UploadManager uploadManager, CloudVo cloudVo, String upToken, String webPth, String localFile, File file) {
        try {
            logger.info("上传文件开始：" + localFile);

//            File file = new File(localFile);
            if(!file.exists()){
                logger.info("上传文件" + localFile + "不存在：");
                return;
            }

//            Response put = uploadManager.put(localFile, webPth, upToken);
            Response put = uploadManager.put(file, webPth, upToken);
            put.close();
            logger.info("上传文件 -- 结束：" + put.address);
        } catch (QiniuException ex) {
            throw new XlwebException(ex.getMessage());
        }
    }
}

