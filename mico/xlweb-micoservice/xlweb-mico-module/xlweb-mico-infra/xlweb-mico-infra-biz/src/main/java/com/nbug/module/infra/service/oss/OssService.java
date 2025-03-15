package com.nbug.module.infra.service.oss;


import com.nbug.mico.common.vo.CloudVo;

import java.io.File;

/**
 * OssService 接口

 */
public interface OssService {

    void upload(CloudVo cloudVo, String webPth, String localFile, File file);

}
