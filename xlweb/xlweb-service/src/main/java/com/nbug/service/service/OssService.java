package com.nbug.service.service;


import com.nbug.common.vo.CloudVo;

import java.io.File;

/**
 * OssService 接口

 */
public interface OssService {

    void upload(CloudVo cloudVo, String webPth, String localFile, File file);

}
