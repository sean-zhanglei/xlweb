package com.nbug.module.infra.service.oss;

import com.nbug.mico.common.vo.CloudVo;
import com.qcloud.cos.COSClient;

import java.io.File;

/**
 * CosService 接口

 */
public interface CosService {

    void uploadFile(CloudVo cloudVo, String webPth, String localFile, Integer id, COSClient cosClient);

    void uploadFile(CloudVo cloudVo, String webPth, String localFile, File file, COSClient cosClient);
}
