package com.nbug.depends.web.web.core.filter;


import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.utils.spring.SpringUtil;
import com.nbug.module.infra.api.attachment.AttachmentApi;

/**
 * response路径处理

 */
public class ResponseRouter {

    public String filter(String data, String path) {
        boolean result = un().contains(path);
        if (result) {
            return data;
        }

//        if (!path.contains("api/admin/") && !path.contains("api/front/")) {
//            return data;
//        }

        //根据需要处理返回值
        if (data.contains(Constants.UPLOAD_TYPE_IMAGE+"/") && !data.contains("data:image/png;base64")) {
            data = SpringUtil.getBean(AttachmentApi.class).prefixImage(data).getCheckedData();
        }

//        if (data.contains("file/")) { 附件也走xlwebimage 目录下
//            data = SpringUtil.getBean(SystemAttachmentService.class).prefixFile(data);
//        }

        return data;
    }

    public static String un() {
        return "";
    }
}
