package com.nbug.module.infra.service.attachment.impl;

import cn.hutool.core.util.StrUtil;
import com.nbug.mico.common.config.XlwebConfig;
import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.model.system.SystemAttachment;
import com.nbug.mico.common.utils.UploadUtil;
import com.nbug.mico.common.utils.XlwebUtil;
import com.nbug.mico.common.utils.date.DateUtil;
import com.nbug.mico.common.vo.CloudVo;
import com.nbug.mico.common.vo.FileResultVo;
import com.nbug.mico.common.vo.UploadCommonVo;
import com.nbug.module.infra.service.attachment.SystemAttachmentService;
import com.nbug.module.infra.service.attachment.UploadService;
import com.nbug.module.infra.service.oss.CosService;
import com.nbug.module.infra.service.oss.OssService;
import com.nbug.module.infra.service.oss.QiNiuService;
import com.nbug.module.system.api.config.ConfigApi;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * UploadServiceImpl 接口实现

 */
@Service
public class UploadServiceImpl implements UploadService {

    private static final Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);

    @Autowired
    private ConfigApi configApi;

    @Autowired
    private SystemAttachmentService systemAttachmentService;

    @Autowired
    private QiNiuService qiNiuService;

    @Autowired
    private OssService ossService;

    @Autowired
    private CosService cosService;

    @Autowired
    XlwebConfig xlwebConfig;

    /**
     * 图片上传
     * @param multipartFile 文件
     * @param model 模块 用户user,商品product,微信wechat,news文章
     * @param pid 分类ID 0编辑器,1商品图片,2拼团图片,3砍价图片,4秒杀图片,5文章图片,6组合数据图,7前台用户,8微信系列
     * @return FileResultVo
     */
    @Override
    public FileResultVo imageUpload(MultipartFile multipartFile, String model, Integer pid) throws IOException {
        if (null == multipartFile || multipartFile.isEmpty()) {
            throw new XlwebException("上传的文件对象不存在...");
        }

//        String rootPath = (configApi.getValueByKey(Constants.UPLOAD_ROOT_PATH_CONFIG_KEY) + "/").replace(" ", "").replace("//", "/");
        String rootPath = xlwebConfig.getImagePath().replace(" ", "").replace("//", "/");
        UploadUtil.setModelPath(model);
        String modelPath = "public/" + model + "/";
        String extStr = configApi.getValueByKey(Constants.UPLOAD_IMAGE_EXT_STR_CONFIG_KEY).getCheckedData();
        int size = Integer.parseInt(configApi.getValueByKey(Constants.UPLOAD_IMAGE_MAX_SIZE_CONFIG_KEY).getCheckedData());
        String type = Constants.UPLOAD_TYPE_IMAGE + "/";

        UploadCommonVo uploadCommonVo = new UploadCommonVo();
        uploadCommonVo.setRootPath(rootPath);
        uploadCommonVo.setModelPath(modelPath);
        uploadCommonVo.setExtStr(extStr);
        uploadCommonVo.setSize(size);
        uploadCommonVo.setType(type);

        // 文件名
        String fileName = multipartFile.getOriginalFilename();
        System.out.println("fileName = " + fileName);
        // 文件后缀名
        String extName = FilenameUtils.getExtension(fileName);
        if (StringUtils.isEmpty(extName)) {
            throw new RuntimeException("文件类型未定义不能上传...");
        }

        if (fileName.length() > 99) {
            fileName = StrUtil.subPre(fileName, 90).concat(".").concat(extName);
        }

        // 文件大小验证
        // 文件分隔符转化为当前系统的格式
        float fileSize = (float)multipartFile.getSize() / 1024 / 1024;
        String fs = String.format("%.2f", fileSize);
        if( fileSize > uploadCommonVo.getSize()){
            throw new XlwebException("最大允许上传" + uploadCommonVo.getSize() + " MB的文件, 当前文件大小为 " + fs + " MB");
        }

        // 判断文件的后缀名是否符合规则
        if (StringUtils.isNotEmpty(uploadCommonVo.getExtStr())) {
            // 切割文件扩展名
            List<String> extensionList = XlwebUtil.stringToArrayStr(uploadCommonVo.getExtStr());
            if (extensionList.size() > 0) {
                //判断
                if (!extensionList.contains(extName)) {
                    throw new XlwebException("上传文件的类型只能是：" + uploadCommonVo.getExtStr());
                }
            } else {
                throw new XlwebException("上传文件的类型只能是：" + uploadCommonVo.getExtStr());
            }
        }

        // 变更文件名
        String newFileName = UploadUtil.fileName(extName);
        // 创建目标文件的名称，规则：  子目录/年/月/日.后缀名
        // 文件分隔符转化为当前系统的格式
        // 文件分隔符转化为当前系统的格式
        String webPath = uploadCommonVo.getType() + uploadCommonVo.getModelPath() + DateUtil.nowDate(Constants.DATE_FORMAT_DATE).replace("-", "/") + "/";
        String destPath = FilenameUtils.separatorsToSystem(uploadCommonVo.getRootPath() + webPath) + newFileName;
        // 创建文件
        File file = UploadUtil.createFile(destPath);

        // 拼装返回的数据
        FileResultVo resultFile = new FileResultVo();
        resultFile.setFileSize(multipartFile.getSize());
        resultFile.setFileName(fileName);
        resultFile.setExtName(extName);
//        resultFile.setServerPath(destPath);
        resultFile.setUrl(webPath + newFileName);
        resultFile.setType(multipartFile.getContentType());

        //图片上传类型 1本地 2七牛云 3OSS 4COS, 默认本地
        String uploadType = configApi.getValueByKeyException("uploadType").getCheckedData();
        Integer uploadTypeInt = Integer.parseInt(uploadType);
        String pre;
        CloudVo cloudVo = new CloudVo();

        resultFile.setType(resultFile.getType().replace("image/", ""));
        SystemAttachment systemAttachment = new SystemAttachment();
        systemAttachment.setName(resultFile.getFileName());
//        systemAttachment.setAttDir(resultFile.getUrl());
        systemAttachment.setSattDir(resultFile.getUrl());
        systemAttachment.setAttSize(resultFile.getFileSize().toString());
        systemAttachment.setAttType(resultFile.getType());
        systemAttachment.setImageType(1);   //图片上传类型 1本地 2七牛云 3OSS 4COS, 默认本地
//        systemAttachment.setAttDir(resultFile.getServerPath()); // 服务器上存储的绝对地址， 上传到云的时候使用
        systemAttachment.setPid(pid);

        if (uploadTypeInt.equals(1)) {
            // 保存文件
            multipartFile.transferTo(file);
            systemAttachmentService.save(systemAttachment);
            return resultFile;
        }
        // 判断是否保存本地
        String fileIsSave = configApi.getValueByKeyException("file_is_save").getCheckedData();
        multipartFile.transferTo(file);
        switch (uploadTypeInt) {
            case 1:
                // 保存文件
//                multipartFile.transferTo(file);
                break;
            case 2:
                systemAttachment.setImageType(2);
                pre = "qn";
                cloudVo.setDomain(configApi.getValueByKeyException(pre+"UploadUrl").getCheckedData());
                cloudVo.setAccessKey(configApi.getValueByKeyException(pre+"AccessKey").getCheckedData());
                cloudVo.setSecretKey(configApi.getValueByKeyException(pre+"SecretKey").getCheckedData());
                cloudVo.setBucketName(configApi.getValueByKeyException(pre+"StorageName").getCheckedData());
                cloudVo.setRegion(configApi.getValueByKeyException(pre+"StorageRegion").getCheckedData());

                try{
                    // 构造一个带指定Zone对象的配置类, 默认华东
                    Configuration cfg = new Configuration(Region.huadong());
                    if("huabei".equals(cloudVo.getRegion())){
                        cfg = new Configuration(Region.huabei());
                    }
                    if("huanan".equals(cloudVo.getRegion())){
                        cfg = new Configuration(Region.huanan());
                    }
                    if("beimei".equals(cloudVo.getRegion())){
                        cfg = new Configuration(Region.beimei());
                    }
                    if("dongnanya".equals(cloudVo.getRegion())){
                        cfg = new Configuration(Region.xinjiapo());
                    }

                    // 其他参数参考类注释
                    UploadManager uploadManager = new UploadManager(cfg);
                    // 生成上传凭证，然后准备上传
                    Auth auth = Auth.create(cloudVo.getAccessKey(), cloudVo.getSecretKey());
                    String upToken = auth.uploadToken(cloudVo.getBucketName());

                    String webPathQn = xlwebConfig.getImagePath();
                    logger.info("AsyncServiceImpl.qCloud.id " + systemAttachment.getAttId());
//                    qiNiuService.uploadFile(uploadManager, cloudVo, upToken,
//                            systemAttachment.getSattDir(), webPathQn + "/" + systemAttachment.getSattDir(), systemAttachment.getAttId());   //异步处理
                    qiNiuService.uploadFile(uploadManager, cloudVo, upToken,
                            systemAttachment.getSattDir(), webPathQn + "/" + systemAttachment.getSattDir(), file);   //异步处理
                }catch (Exception e){
                    logger.error("AsyncServiceImpl.qCloud.fail " + e.getMessage());
                }
                break;
            case 3:
                systemAttachment.setImageType(3);
                pre = "al";
                cloudVo.setDomain(configApi.getValueByKeyException(pre+"UploadUrl").getCheckedData());
                cloudVo.setAccessKey(configApi.getValueByKeyException(pre+"AccessKey").getCheckedData());
                cloudVo.setSecretKey(configApi.getValueByKeyException(pre+"SecretKey").getCheckedData());
                cloudVo.setBucketName(configApi.getValueByKeyException(pre+"StorageName").getCheckedData());
                cloudVo.setRegion(configApi.getValueByKeyException(pre+"StorageRegion").getCheckedData());
                try{
                    String webPathAl = xlwebConfig.getImagePath();
                    logger.info("AsyncServiceImpl.oss.id " + systemAttachment.getAttId());
                    ossService.upload(cloudVo, systemAttachment.getSattDir(),  webPathAl + "/" + systemAttachment.getSattDir(),
                            file);
                }catch (Exception e){
                    logger.error("AsyncServiceImpl.oss fail " + e.getMessage());
                }
                break;
            case 4:
                systemAttachment.setImageType(4);
                pre = "tx";
                cloudVo.setDomain(configApi.getValueByKeyException(pre+"UploadUrl").getCheckedData());
                cloudVo.setAccessKey(configApi.getValueByKeyException(pre+"AccessKey").getCheckedData());
                cloudVo.setSecretKey(configApi.getValueByKeyException(pre+"SecretKey").getCheckedData());
                cloudVo.setBucketName(configApi.getValueByKeyException(pre+"StorageName").getCheckedData());
                cloudVo.setRegion(configApi.getValueByKeyException(pre+"StorageRegion").getCheckedData());
                // 1 初始化用户身份信息(secretId, secretKey)
                COSCredentials cred = new BasicCOSCredentials(cloudVo.getAccessKey(), cloudVo.getSecretKey());
                // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
                ClientConfig clientConfig = new ClientConfig(new com.qcloud.cos.region.Region(cloudVo.getRegion()));
                // 3 生成 cos 客户端。
                COSClient cosClient = new COSClient(cred, clientConfig);

                try{
                    String webPathTx = xlwebConfig.getImagePath();
                    logger.info("AsyncServiceImpl.cos.id " + systemAttachment.getAttId());
                    cosService.uploadFile(cloudVo, systemAttachment.getSattDir(), webPathTx + "/" + systemAttachment.getSattDir(), systemAttachment.getAttId(), cosClient);
                }catch (Exception e){
                    logger.error("AsyncServiceImpl.cos.fail " + e.getMessage());
                }finally {
                    cosClient.shutdown();
                }
                break;
        }
        systemAttachmentService.save(systemAttachment);
        if (!"1".equals(fileIsSave)) {
            // 删除本地文件
            file.delete();
        }
        return resultFile;
    }

    /**
     * 文件长传
     * @param multipartFile 文件
     * @param model 模块 用户user,商品product,微信wechat,news文章
     * @param pid 分类ID 0编辑器,1商品图片,2拼团图片,3砍价图片,4秒杀图片,5文章图片,6组合数据图,7前台用户,8微信系列
     * @return FileResultVo
     * @throws IOException
     */
    @Override
    public FileResultVo fileUpload(MultipartFile multipartFile, String model, Integer pid) throws IOException {
        String rootPath = (xlwebConfig.getImagePath() + "/").replace(" ", "").replace("//", "/");
        UploadUtil.setModelPath(model);
        String modelPath = "public/" + model + "/";
        String extStr = configApi.getValueByKey(Constants.UPLOAD_FILE_EXT_STR_CONFIG_KEY).getCheckedData();
        int size = Integer.parseInt(configApi.getValueByKey(Constants.UPLOAD_FILE_MAX_SIZE_CONFIG_KEY).getCheckedData());
        String type = Constants.UPLOAD_TYPE_FILE + "/";

        UploadCommonVo uploadCommonVo = new UploadCommonVo();
        uploadCommonVo.setRootPath(rootPath);
        uploadCommonVo.setModelPath(modelPath);
        uploadCommonVo.setExtStr(extStr);
        uploadCommonVo.setSize(size);
        uploadCommonVo.setType(type);

        if (null == multipartFile || multipartFile.isEmpty()) {
            throw new XlwebException("上传的文件对象不存在...");
        }
        // 文件名
        String fileName = multipartFile.getOriginalFilename();
        System.out.println("fileName = " + fileName);
        // 文件后缀名
        String extName = FilenameUtils.getExtension(fileName);
        if (StringUtils.isEmpty(extName)) {
            throw new RuntimeException("文件类型未定义不能上传...");
        }

        if (fileName.length() > 99) {
            fileName = StrUtil.subPre(fileName, 90).concat(".").concat(extName);
        }

        //文件大小验证
        // 文件分隔符转化为当前系统的格式
        float fileSize = (float)multipartFile.getSize() / 1024 / 1024;
        String fs = String.format("%.2f", fileSize);
        if( fileSize > uploadCommonVo.getSize()){
            throw new XlwebException("最大允许上传" + uploadCommonVo.getSize() + " MB的文件, 当前文件大小为 " + fs + " MB");
        }

        // 判断文件的后缀名是否符合规则
//        isContains(extName);
        if (StringUtils.isNotEmpty(uploadCommonVo.getExtStr())) {
            // 切割文件扩展名
            List<String> extensionList = XlwebUtil.stringToArrayStr(uploadCommonVo.getExtStr());

            if (extensionList.size() > 0) {
                //判断
                if (!extensionList.contains(extName)) {
                    throw new XlwebException("上传文件的类型只能是：" + uploadCommonVo.getExtStr());
                }
            } else {
                throw new XlwebException("上传文件的类型只能是：" + uploadCommonVo.getExtStr());
            }
        }

        //文件名
        String newFileName = UploadUtil.fileName(extName);
        // 创建目标文件的名称，规则请看destPath方法
        //规则：  子目录/年/月/日.后缀名
        // 文件分隔符转化为当前系统的格式
        // 文件分隔符转化为当前系统的格式
        String webPath = uploadCommonVo.getType() + uploadCommonVo.getModelPath() + DateUtil.nowDate(Constants.DATE_FORMAT_DATE).replace("-", "/") + "/";
        String destPath = FilenameUtils.separatorsToSystem(uploadCommonVo.getRootPath() + webPath) + newFileName;
        // 创建文件
        File file = UploadUtil.createFile(destPath);

        // 拼装返回的数据
        FileResultVo resultFile = new FileResultVo();
        resultFile.setFileSize(multipartFile.getSize());
        resultFile.setFileName(fileName);
        resultFile.setExtName(extName);
//        resultFile.setServerPath(destPath);
        resultFile.setUrl(webPath + newFileName);
        resultFile.setType(multipartFile.getContentType());

        //图片上传类型 1本地 2七牛云 3OSS 4COS, 默认本地
        String uploadType = configApi.getValueByKeyException("uploadType").getCheckedData();
        Integer uploadTypeInt = Integer.parseInt(uploadType);
        String pre;
        CloudVo cloudVo = new CloudVo();

        resultFile.setType(resultFile.getType().replace("file/", ""));
        SystemAttachment systemAttachment = new SystemAttachment();
        systemAttachment.setName(resultFile.getFileName());
        systemAttachment.setSattDir(resultFile.getUrl());
        systemAttachment.setAttSize(resultFile.getFileSize().toString());
        systemAttachment.setAttType(resultFile.getType());
        systemAttachment.setImageType(1);   //图片上传类型 1本地 2七牛云 3OSS 4COS, 默认本地，任务轮询数据库放入云服务
        systemAttachment.setPid(pid);

        if (uploadTypeInt.equals(1)) {
            // 保存文件
            multipartFile.transferTo(file);
            systemAttachmentService.save(systemAttachment);
            return resultFile;
        }
        // 判断是否保存本地
        String fileIsSave = configApi.getValueByKeyException("file_is_save").getCheckedData();
        if ("1".equals(fileIsSave)) {
            multipartFile.transferTo(file);
        }

        switch (uploadTypeInt) {
            case 1:
                // 保存文件
                multipartFile.transferTo(file);
                break;
            case 2:
                systemAttachment.setImageType(2);
                pre = "qn";
                cloudVo.setDomain(configApi.getValueByKeyException(pre+"UploadUrl").getCheckedData());
                cloudVo.setAccessKey(configApi.getValueByKeyException(pre+"AccessKey").getCheckedData());
                cloudVo.setSecretKey(configApi.getValueByKeyException(pre+"SecretKey").getCheckedData());
                cloudVo.setBucketName(configApi.getValueByKeyException(pre+"StorageName").getCheckedData());
                cloudVo.setRegion(configApi.getValueByKeyException(pre+"StorageRegion").getCheckedData());

                try{
                    // 构造一个带指定Zone对象的配置类, 默认华东
                    Configuration cfg = new Configuration(Region.huadong());
                    if("huabei".equals(cloudVo.getRegion())){
                        cfg = new Configuration(Region.huabei());
                    }
                    if("huanan".equals(cloudVo.getRegion())){
                        cfg = new Configuration(Region.huanan());
                    }
                    if("beimei".equals(cloudVo.getRegion())){
                        cfg = new Configuration(Region.beimei());
                    }
                    if("dongnanya".equals(cloudVo.getRegion())){
                        cfg = new Configuration(Region.xinjiapo());
                    }

                    // 其他参数参考类注释
                    UploadManager uploadManager = new UploadManager(cfg);
                    // 生成上传凭证，然后准备上传
                    Auth auth = Auth.create(cloudVo.getAccessKey(), cloudVo.getSecretKey());
                    String upToken = auth.uploadToken(cloudVo.getBucketName());

                    String webPathQn = xlwebConfig.getImagePath();
                    logger.info("AsyncServiceImpl.qCloud.id " + systemAttachment.getAttId());
                    qiNiuService.uploadFile(uploadManager, cloudVo, upToken,
                            systemAttachment.getSattDir(), webPathQn + "/" + systemAttachment.getSattDir(), file);
                }catch (Exception e){
                    logger.error("AsyncServiceImpl.qCloud.fail " + e.getMessage());
                }
                break;
            case 3:
                systemAttachment.setImageType(3);
                pre = "al";
                cloudVo.setDomain(configApi.getValueByKeyException(pre+"UploadUrl").getCheckedData());
                cloudVo.setAccessKey(configApi.getValueByKeyException(pre+"AccessKey").getCheckedData());
                cloudVo.setSecretKey(configApi.getValueByKeyException(pre+"SecretKey").getCheckedData());
                cloudVo.setBucketName(configApi.getValueByKeyException(pre+"StorageName").getCheckedData());
                cloudVo.setRegion(configApi.getValueByKeyException(pre+"StorageRegion").getCheckedData());
                try{
                    String webPathAl = xlwebConfig.getImagePath();
                    logger.info("AsyncServiceImpl.oss.id " + systemAttachment.getAttId());
                    ossService.upload(cloudVo, systemAttachment.getSattDir(),  webPathAl + "/" + systemAttachment.getSattDir(),
                            file);
                }catch (Exception e){
                    logger.error("AsyncServiceImpl.oss fail " + e.getMessage());
                }
                break;
            case 4:
                systemAttachment.setImageType(4);
                pre = "tx";
                cloudVo.setDomain(configApi.getValueByKeyException(pre+"UploadUrl").getCheckedData());
                cloudVo.setAccessKey(configApi.getValueByKeyException(pre+"AccessKey").getCheckedData());
                cloudVo.setSecretKey(configApi.getValueByKeyException(pre+"SecretKey").getCheckedData());
                cloudVo.setBucketName(configApi.getValueByKeyException(pre+"StorageName").getCheckedData());
                cloudVo.setRegion(configApi.getValueByKeyException(pre+"StorageRegion").getCheckedData());
                // 1 初始化用户身份信息(secretId, secretKey)
                COSCredentials cred = new BasicCOSCredentials(cloudVo.getAccessKey(), cloudVo.getSecretKey());
                // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
                ClientConfig clientConfig = new ClientConfig(new com.qcloud.cos.region.Region(cloudVo.getRegion()));
                // 3 生成 cos 客户端。
                COSClient cosClient = new COSClient(cred, clientConfig);

                try{
                    String webPathTx = xlwebConfig.getImagePath();
                    logger.info("AsyncServiceImpl.cos.id " + systemAttachment.getAttId());
                    cosService.uploadFile(cloudVo, systemAttachment.getSattDir(), webPathTx + "/" + systemAttachment.getSattDir(), file, cosClient);
                }catch (Exception e){
                    logger.error("AsyncServiceImpl.cos.fail " + e.getMessage());
                }finally {
                    cosClient.shutdown();
                }
                break;
        }
        systemAttachmentService.save(systemAttachment);
        return resultFile;
    }
}

