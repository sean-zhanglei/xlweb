package com.nbug.module.infra.service.wechat;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface WechatMediaService {

    /**
     * 微信上传素材
     * @param file 文件
     * @param type 类型
     * @return Map
     * @throws Exception
     */
    Map<String, String> upload(MultipartFile file, String type);
}
