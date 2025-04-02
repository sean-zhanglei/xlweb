package com.nbug.module.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.nbug.depends.web.web.core.util.WebFrameworkUtils;
import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.constants.SysConfigConstants;
import com.nbug.mico.common.constants.SysGroupDataConstants;
import com.nbug.mico.common.constants.UserConstants;
import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.model.product.StoreProduct;
import com.nbug.mico.common.model.record.UserVisitRecord;
import com.nbug.mico.common.model.system.SystemConfig;
import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.response.IndexInfoResponse;
import com.nbug.mico.common.response.IndexProductResponse;
import com.nbug.mico.common.response.ProductActivityItemResponse;
import com.nbug.mico.common.utils.XlwebUtil;
import com.nbug.mico.common.utils.redis.RedisUtil;
import com.nbug.mico.common.vo.MyRecord;
import com.nbug.module.store.api.storeProduct.StoreProductApi;
import com.nbug.module.system.api.config.ConfigApi;
import com.nbug.module.system.api.systemGroupData.SystemGroupDataApi;
import com.nbug.module.user.service.IndexService;
import com.nbug.module.user.service.UserService;
import com.nbug.module.user.service.UserVisitRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
* IndexServiceImpl 接口实现

*/
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private SystemGroupDataApi systemGroupDataApi;

    @Autowired
    private ConfigApi configApi;

    @Autowired
    private UserService userService;

    @Autowired
    private StoreProductApi storeProductApi;

    @Autowired
    private UserVisitRecordService userVisitRecordService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 首页数据
     * banner、金刚区、广告位
     */
    @Override
    public IndexInfoResponse getIndexInfo() {
        String key = "index::" + UserConstants.USER_INDEX_INFO;
        if (redisUtil.exists(key)) {
            // 保存用户访问记录
            UserVisitRecord visitRecord = new UserVisitRecord();
            visitRecord.setDate(DateUtil.date().toString("yyyy-MM-dd"));
            visitRecord.setUid(userService.getUserId());
            visitRecord.setVisitType(1);
            userVisitRecordService.save(visitRecord);
            IndexInfoResponse indexInfoResponse = (IndexInfoResponse) redisUtil.get(key);
            User user = userService.getInfo();
            if (ObjectUtil.isNotNull(user) && user.getSubscribe()) {
                indexInfoResponse.setSubscribe(user.getSubscribe());
            }
            // 2 分钟缓存 避免批量失效增加【1,2） 分钟随机值
            redisUtil.set(key, indexInfoResponse, 2L + RandomUtil.randomInt(1, 2), TimeUnit.MINUTES);
            return redisUtil.get(key);
        } else {
            IndexInfoResponse indexInfoResponse = new IndexInfoResponse();
            indexInfoResponse.setBanner(systemGroupDataApi.getListMapByGid(Constants.GROUP_DATA_ID_INDEX_BANNER).getCheckedData()); //首页banner滚动图
            indexInfoResponse.setMenus(systemGroupDataApi.getListMapByGid(Constants.GROUP_DATA_ID_INDEX_MENU).getCheckedData()); //导航模块
            indexInfoResponse.setRoll(systemGroupDataApi.getListMapByGid(Constants.GROUP_DATA_ID_INDEX_NEWS_BANNER).getCheckedData()); //首页滚动新闻

            indexInfoResponse.setLogoUrl(configApi.getValueByKey(Constants.CONFIG_KEY_SITE_LOGO).getCheckedData());// 企业logo地址
            indexInfoResponse.setYzfUrl(configApi.getValueByKey(Constants.CONFIG_KEY_YZF_H5_URL).getCheckedData());// 云智服H5 url
            indexInfoResponse.setConsumerHotline(configApi.getValueByKey(Constants.CONFIG_KEY_CONSUMER_HOTLINE).getCheckedData());// 客服电话
            indexInfoResponse.setTelephoneServiceSwitch(configApi.getValueByKey(Constants.CONFIG_KEY_TELEPHONE_SERVICE_SWITCH).getCheckedData());// 客服电话服务
            indexInfoResponse.setCategoryPageConfig(configApi.getValueByKey(Constants.CONFIG_CATEGORY_CONFIG).getCheckedData());// 商品分类页配置
            indexInfoResponse.setIsShowCategory(configApi.getValueByKey(Constants.CONFIG_IS_SHOW_CATEGORY).getCheckedData());// 是否隐藏一级分类
            indexInfoResponse.setExplosiveMoney(systemGroupDataApi.getListMapByGid(Constants.GROUP_DATA_ID_INDEX_EX_BANNER).getCheckedData());//首页超值爆款
            indexInfoResponse.setHomePageSaleListStyle(configApi.getValueByKey(Constants.CONFIG_IS_PRODUCT_LIST_STYLE).getCheckedData());// 首页商品列表模板配置

            indexInfoResponse.setConsumerWelcomeTitle(configApi.getValueByKey(Constants.CONFIG_KEY_CONSUMER_WELCOME_TITLE).getCheckedData());// 欢迎语标题
            indexInfoResponse.setConsumerWelcome(configApi.getValueByKey(Constants.CONFIG_KEY_CONSUMER_WELCOME).getCheckedData());// 欢迎语

            indexInfoResponse.setSubscribe(false);
            User user = userService.getInfo();
            if (ObjectUtil.isNotNull(user) && user.getSubscribe()) {
                indexInfoResponse.setSubscribe(user.getSubscribe());
            }

            // 保存用户访问记录
            UserVisitRecord visitRecord = new UserVisitRecord();
            visitRecord.setDate(DateUtil.date().toString("yyyy-MM-dd"));
            visitRecord.setUid(userService.getUserId());
            visitRecord.setVisitType(1);
            userVisitRecordService.save(visitRecord);

            // 2 分钟缓存 避免批量失效增加【1,2） 分钟随机值
            redisUtil.set(key, indexInfoResponse, 2L + RandomUtil.randomInt(1, 2), TimeUnit.MINUTES);
            return redisUtil.get(key);
        }
    }

    /**
     * 热门搜索
     * @return List<HashMap<String, String>>
     */
    @Override
    public List<HashMap<String, Object>> hotKeywords() {
        // 按用户UID缓存
        Long userId = WebFrameworkUtils.getLoginUserId();
        if (redisUtil.exists(userId + "::" + UserConstants.USER_HOT_KEYWORDS)) {
            return redisUtil.get(userId + "::" + UserConstants.USER_HOT_KEYWORDS);
        } else {
            List<HashMap<String, Object>> list = systemGroupDataApi.getListMapByGid(SysGroupDataConstants.GROUP_DATA_ID_INDEX_KEYWORDS).getCheckedData();
            // 60 分钟缓存 避免批量失效增加【1-60） 分钟随机值
            redisUtil.set(userId + "::" + UserConstants.USER_HOT_KEYWORDS, list, 60L + RandomUtil.randomInt(1, 60), TimeUnit.MINUTES);
            return redisUtil.get(userId + "::" + UserConstants.USER_HOT_KEYWORDS);
        }
    }

    /**
     * 微信分享配置
     * @return Object
     */
    @Override
    public HashMap<String, String> getShareConfig() {
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> info = configApi.info(Constants.CONFIG_FORM_ID_PUBLIC).getCheckedData();
        if(info == null) {
            throw new XlwebException("请配置公众号分享信息！");
        }
        map.put("img", info.get(SysConfigConstants.CONFIG_KEY_ADMIN_WECHAT_SHARE_IMAGE));
        map.put("title", info.get(SysConfigConstants.CONFIG_KEY_ADMIN_WECHAT_SHARE_TITLE));
        map.put("synopsis", info.get(SysConfigConstants.CONFIG_KEY_ADMIN_WECHAT_SHARE_SYNOSIS));
        return map;
    }

    /**
     * 获取首页商品列表
     * @param type 类型 【1 精品推荐 2 热门榜单 3首发新品 4促销单品】
     * @param pageParamRequest 分页参数
     * @return List
     */
    @Override
    public CommonPage<IndexProductResponse> findIndexProductList(Integer type, PageParamRequest pageParamRequest) {
        String key = "index::" + UserConstants.INDEX_PRODUCT + ":type:" + type + ":"  + SecureUtil.md5(pageParamRequest.toString());
        if (redisUtil.exists(key)) {
            return redisUtil.get(key);
        } else {
            if (type < Constants.INDEX_RECOMMEND_BANNER || type > Constants.INDEX_BENEFIT_BANNER) {
                return CommonPage.restPage(new ArrayList<>());
            }
            List<StoreProduct> storeProductList = storeProductApi.getIndexProduct(type, pageParamRequest).getCheckedData();
            if (CollUtil.isEmpty(storeProductList)) {
                return CommonPage.restPage(new ArrayList<>());
            }
            CommonPage<StoreProduct> storeProductCommonPage = CommonPage.restPage(storeProductList);

            List<IndexProductResponse> productResponseArrayList = new ArrayList<>();
            for (StoreProduct storeProduct : storeProductList) {
                IndexProductResponse productResponse = new IndexProductResponse();
                List<Integer> activityList = XlwebUtil.stringToArrayInt(storeProduct.getActivity());
                // 活动类型默认：直接跳过
                if (activityList.get(0).equals(Constants.PRODUCT_TYPE_NORMAL)) {
                    BeanUtils.copyProperties(storeProduct, productResponse);
                    productResponseArrayList.add(productResponse);
                    continue;
                }
                // 根据参与活动添加对应商品活动标示
                HashMap<Integer, ProductActivityItemResponse> activityByProduct =
                        storeProductApi.getActivityByProduct(storeProduct.getId(), storeProduct.getActivity()).getCheckedData();
                if (CollUtil.isNotEmpty(activityByProduct)) {
                    for (Integer activity : activityList) {
                        if (activity.equals(Constants.PRODUCT_TYPE_NORMAL)) {
                            break;
                        }
                        if (activity.equals(Constants.PRODUCT_TYPE_SECKILL)) {
                            ProductActivityItemResponse itemResponse = activityByProduct.get(Constants.PRODUCT_TYPE_SECKILL);
                            if (ObjectUtil.isNotNull(itemResponse)) {
                                productResponse.setActivityH5(itemResponse);
                                break;
                            }
                        }
                        if (activity.equals(Constants.PRODUCT_TYPE_BARGAIN)) {
                            ProductActivityItemResponse itemResponse = activityByProduct.get(Constants.PRODUCT_TYPE_BARGAIN);
                            if (ObjectUtil.isNotNull(itemResponse)) {
                                productResponse.setActivityH5(itemResponse);
                                break;
                            }
                        }
                        if (activity.equals(Constants.PRODUCT_TYPE_PINGTUAN)) {
                            ProductActivityItemResponse itemResponse = activityByProduct.get(Constants.PRODUCT_TYPE_PINGTUAN);
                            if (ObjectUtil.isNotNull(itemResponse)) {
                                productResponse.setActivityH5(itemResponse);
                                break;
                            }
                        }
                    }
                }
                BeanUtils.copyProperties(storeProduct, productResponse);
                productResponseArrayList.add(productResponse);
            }
            CommonPage<IndexProductResponse> productResponseCommonPage = CommonPage.restPage(productResponseArrayList);
            BeanUtils.copyProperties(storeProductCommonPage, productResponseCommonPage, "list");

            // 首页商品数据缓存 2分钟 随机秒数[1,60) 避免缓存雪崩
            redisUtil.set(key, productResponseCommonPage, 60L * 2 + RandomUtil.randomInt(1, 60), TimeUnit.SECONDS);
            return redisUtil.get(key);
        }
    }

    /**
     * 获取颜色配置
     * @return SystemConfig
     */
    @Override
    public SystemConfig getColorConfig() {
        return configApi.getColorConfig().getCheckedData();
    }

    /**
     * 获取版本信息
     * @return MyRecord
     */
    @Override
    public MyRecord getVersion() {
        MyRecord record = new MyRecord();
        // app版本号
        record.set("appVersion", configApi.getValueByKey(Constants.CONFIG_APP_VERSION).getCheckedData());
        record.set("androidAddress", configApi.getValueByKey(Constants.CONFIG_APP_ANDROID_ADDRESS).getCheckedData());
        record.set("iosAddress", configApi.getValueByKey(Constants.CONFIG_APP_IOS_ADDRESS).getCheckedData());
        record.set("openUpgrade", configApi.getValueByKey(Constants.CONFIG_APP_OPEN_UPGRADE).getCheckedData());
        return record;
    }

    /**
     * 获取全局本地图片域名
     * @return String
     */
    @Override
    public String getImageDomain() {
        String localUploadUrl = configApi.getValueByKey("localUploadUrl").getCheckedData();
        return StrUtil.isBlank(localUploadUrl) ? "" : localUploadUrl;
    }
}

