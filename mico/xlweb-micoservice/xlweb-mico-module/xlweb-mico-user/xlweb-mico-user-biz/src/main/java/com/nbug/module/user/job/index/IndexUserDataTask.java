package com.nbug.module.user.job.index;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.nbug.depends.tenant.core.aop.TenantIgnore;
import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.constants.UserConstants;
import com.nbug.mico.common.model.product.StoreProduct;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.response.IndexInfoResponse;
import com.nbug.mico.common.response.IndexProductResponse;
import com.nbug.mico.common.utils.date.DateUtil;
import com.nbug.mico.common.utils.redis.RedisUtil;
import com.nbug.module.store.api.storeProduct.StoreProductApi;
import com.nbug.module.system.api.config.ConfigApi;
import com.nbug.module.system.api.systemGroupData.SystemGroupDataApi;
import com.nbug.module.user.service.IndexService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 首页商品数据同步task

 */
@Component
@Slf4j
public class IndexUserDataTask {

    @Autowired
    private IndexService indexService;

    @Autowired
    private SystemGroupDataApi systemGroupDataApi;

    @Autowired
    private StoreProductApi storeProductApi;

    @Autowired
    private ConfigApi configApi;

    @Autowired
    RedisUtil redisUtil;

    @XxlJob("indexInfoDataJob")
    @TenantIgnore
    // @TenantJob // 多租户
    // @Scheduled(fixedDelay = 1000 * 60L) //1分钟同步一次数据
    public void initIndexInfo() {
        log.info("---IndexInfoDataTask task------produce Data with fixed rate task: Execution Time - {}", DateUtil.nowDateTime());
        try {
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

            // 首页配置数据缓存 2分钟 随机秒数[1,60) 避免缓存雪崩
            redisUtil.set("index::" + UserConstants.USER_INDEX_INFO, indexInfoResponse, 60L * 2 + RandomUtil.randomInt(1, 60), TimeUnit.SECONDS);

            log.info("IndexInfoDataTask.task 首页配置数据预热完成" + " | msg : " + "IndexInfoDataTask.task");
        } catch (Exception e) {
            log.error("IndexInfoDataTask.task" + " | msg : " + e.getMessage());
        }
    }

    @XxlJob("indexUserDataJob")
    @TenantIgnore
    // @TenantJob // 多租户
    // @Scheduled(fixedDelay = 1000 * 60L) //1分钟同步一次数据
    public void init() {
        log.info("---IndexUserDataTask task------produce Data with fixed rate task: Execution Time - {}", DateUtil.nowDateTime());
        try {
             // 【1 精品推荐 2 热门榜单 3首发新品 4促销单品】
            PageParamRequest pageParamRequest = new PageParamRequest();
            pageParamRequest.setLimit(10);
            CommonPage<IndexProductResponse> indexProductResponseCommonPageType_1 =  indexService.findIndexProductList(1, pageParamRequest);
            // 首页商品数据缓存 2分钟 随机秒数[1,60) 避免缓存雪崩
            redisUtil.set("index::" + UserConstants.INDEX_PRODUCT + ":type:1:" + SecureUtil.md5(pageParamRequest.toString()), indexProductResponseCommonPageType_1, 60L * 2 + RandomUtil.randomInt(1, 60), TimeUnit.SECONDS);

            CommonPage<IndexProductResponse> indexProductResponseCommonPageType_2 =  indexService.findIndexProductList(2, pageParamRequest);
            // 首页商品数据缓存 2分钟 随机秒数[1,60) 避免缓存雪崩
            redisUtil.set("index::" + UserConstants.INDEX_PRODUCT + ":type:2:" + SecureUtil.md5(pageParamRequest.toString()), indexProductResponseCommonPageType_2, 60L * 2 + RandomUtil.randomInt(1, 60), TimeUnit.SECONDS);

            CommonPage<IndexProductResponse> indexProductResponseCommonPageType_3 =  indexService.findIndexProductList(3, pageParamRequest);
            // 首页商品数据缓存 2分钟 随机秒数[1,60) 避免缓存雪崩
            redisUtil.set("index::" + UserConstants.INDEX_PRODUCT + ":type:3:" + SecureUtil.md5(pageParamRequest.toString()), indexProductResponseCommonPageType_3, 60L * 2 + RandomUtil.randomInt(1, 60), TimeUnit.SECONDS);

            CommonPage<IndexProductResponse> indexProductResponseCommonPageType_4 =  indexService.findIndexProductList(4, pageParamRequest);
            // 首页商品数据缓存 2分钟 随机秒数[1,60) 避免缓存雪崩
            redisUtil.set("index::" + UserConstants.INDEX_PRODUCT + ":type:4:" + SecureUtil.md5(pageParamRequest.toString()), indexProductResponseCommonPageType_4, 60L * 2 + RandomUtil.randomInt(1, 60), TimeUnit.SECONDS);

            log.info("IndexUserDataTask.task 首页商品数据预热完成" + " | msg : " + "IndexUserDataTask.task");
        } catch (Exception e) {
            log.error("IndexUserDataTask.task" + " | msg : " + e.getMessage());
        }

    }

    @XxlJob("indexProductDetailDataJob")
    @TenantIgnore
    // @TenantJob // 多租户
    // @Scheduled(fixedDelay = 10000 * 60L) //10分钟同步一次数据
    public void initProductDetail() {
        log.info("---IndexProductDetailDataTask task------produce Data with fixed rate task: Execution Time - {}", DateUtil.nowDateTime());
        try {
            // 【1 精品推荐 2 热门榜单 3首发新品 4促销单品】
            PageParamRequest pageParamRequest = new PageParamRequest();
            pageParamRequest.setLimit(10);
            CommonPage<IndexProductResponse> indexProductResponseCommonPageType_1 =  indexService.findIndexProductList(1, pageParamRequest);
            CommonPage<IndexProductResponse> indexProductResponseCommonPageType_2 =  indexService.findIndexProductList(2, pageParamRequest);
            CommonPage<IndexProductResponse> indexProductResponseCommonPageType_3 =  indexService.findIndexProductList(3, pageParamRequest);
            CommonPage<IndexProductResponse> indexProductResponseCommonPageType_4 =  indexService.findIndexProductList(4, pageParamRequest);
            // 去重
            Set<IndexProductResponse> sets = new HashSet<>();
            sets.addAll(indexProductResponseCommonPageType_1.getList());
            sets.addAll(indexProductResponseCommonPageType_2.getList());
            sets.addAll(indexProductResponseCommonPageType_3.getList());
            sets.addAll(indexProductResponseCommonPageType_4.getList());

            for (IndexProductResponse indexProductResponse: sets
                 ) {
                StoreProduct storeProduct = storeProductApi.getById(indexProductResponse.getId()).getCheckedData();
                // 首页商品数据缓存 10分钟 随机秒数[1,60) 避免缓存雪崩
                redisUtil.hmSet("index::" + UserConstants.INDEX_PRODUCT_DETAIL + ":normal", String.valueOf(indexProductResponse.getId()), storeProduct);
                boolean success = redisUtil.expire("index::" + UserConstants.INDEX_PRODUCT_DETAIL + ":normal", 60L * 10 + RandomUtil.randomInt(1, 60));
                if (! success) {
                    redisUtil.hmDelete("index::" + UserConstants.INDEX_PRODUCT_DETAIL + ":normal", String.valueOf(indexProductResponse.getId()));
                    log.error("IndexProductDetailDataTask.task 首页商品详情数据预热失败" + " | msg : " + "IndexProductDetailDataTask.task");
                }
            }
            log.info("IndexProductDetailDataTask.task 首页商品详情数据预热完成" + " | msg : " + "IndexProductDetailDataTask.task");
        } catch (Exception e) {
            log.error("IndexProductDetailDataTask.task" + " | msg : " + e.getMessage());
        }

    }
}
