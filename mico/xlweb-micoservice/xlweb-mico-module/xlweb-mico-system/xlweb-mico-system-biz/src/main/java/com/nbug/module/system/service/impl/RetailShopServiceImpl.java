package com.nbug.module.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.model.system.SystemConfig;
import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.model.user.UserBrokerageRecord;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.RetailShopRequest;
import com.nbug.mico.common.response.SpreadUserResponse;
import com.nbug.mico.common.response.UserExtractResponse;
import com.nbug.module.store.api.storeOrder.StoreOrderApi;
import com.nbug.module.system.dal.SystemConfigDao;
import com.nbug.module.system.service.RetailShopService;
import com.nbug.module.system.service.SystemConfigService;
import com.nbug.module.user.api.user.UserApi;
import com.nbug.module.user.api.userBrokerageRecord.UserBrokerageRecordApi;
import com.nbug.module.user.api.userExtract.UserExtractApi;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * RetailShopServiceImpl 接口实现 分销业务实现

 */
@Service
public class RetailShopServiceImpl extends ServiceImpl<SystemConfigDao, SystemConfig> implements RetailShopService {

    @Autowired
    private UserApi userApi;

    @Autowired
    private UserExtractApi userExtractApi;

    @Autowired
    private StoreOrderApi storeOrderApi;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private UserBrokerageRecordApi userBrokerageRecordApi;

    /**
     * 获取分销列表
     * @param keywords 搜索参数
     * @param dateLimit 时间参数
     * @param pageRequest 分页参数
     */
    @Override
    public CommonPage<SpreadUserResponse> getSpreadPeopleList(String keywords, String dateLimit, PageParamRequest pageRequest) {
        Page<User> pageUserPage = PageHelper.startPage(pageRequest.getPage(), pageRequest.getLimit());
        // id,头像，昵称，姓名，电话，推广用户数，推广订单数，推广订单额，佣金总金额，已提现金额，提现次数，未提现金额，上级推广人
        PageInfo<User> userPageInfo = userApi.getAdminSpreadPeopleList(keywords, dateLimit, pageRequest).getCheckedData();

        if (CollUtil.isEmpty(userPageInfo.getList())) {
            return CommonPage.restPage(new PageInfo<>());
        }
        List<User> userList = userPageInfo.getList();
        List<SpreadUserResponse> responseList = CollUtil.newArrayList();
        userList.forEach(user -> {
            SpreadUserResponse userResponse = new SpreadUserResponse();
            BeanUtils.copyProperties(user, userResponse);
            // 上级推广员名称
            userResponse.setSpreadNickname("无");
            if (ObjectUtil.isNotNull(user.getSpreadUid()) && user.getSpreadUid() > 0) {
                User spreadUser = userApi.getById(user.getSpreadUid()).getCheckedData();
                userResponse.setSpreadNickname(Optional.ofNullable(spreadUser.getNickname()).orElse("--"));
            }

            List<UserBrokerageRecord> recordList = userBrokerageRecordApi.getSpreadListByUid(user.getUid()).getCheckedData();
            if (CollUtil.isEmpty(recordList)) {
                // 推广订单数
                userResponse.setSpreadOrderNum(0);
                // 推广订单额
                userResponse.setSpreadOrderTotalPrice(BigDecimal.ZERO);
                // 佣金总金额
                userResponse.setTotalBrokeragePrice(BigDecimal.ZERO);
                // 已提现金额
                userResponse.setExtractCountPrice(BigDecimal.ZERO);
                // 提现次数
                userResponse.setExtractCountNum(0);
                // 冻结中佣金
                userResponse.setFreezeBrokeragePrice(BigDecimal.ZERO);
            } else {
                // 推广订单数
                userResponse.setSpreadOrderNum(recordList.size());
                // 佣金总金额
                userResponse.setTotalBrokeragePrice(recordList.stream().map(UserBrokerageRecord::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
                // 推广订单额
                List<String> orderNoList = recordList.stream().map(UserBrokerageRecord::getLinkId).collect(Collectors.toList());
                BigDecimal spreadOrderTotalPrice = storeOrderApi.getSpreadOrderTotalPriceByOrderList(orderNoList).getCheckedData();
                userResponse.setSpreadOrderTotalPrice(spreadOrderTotalPrice);

                UserExtractResponse extractResponse = userExtractApi.getUserExtractByUserId(user.getUid()).getCheckedData();
                // 已提现金额
                userResponse.setExtractCountPrice(extractResponse.getExtractCountPrice());
                // 提现次数
                userResponse.setExtractCountNum(extractResponse.getExtractCountNum());
                // 冻结中佣金
                userResponse.setFreezeBrokeragePrice(userBrokerageRecordApi.getFreezePrice(user.getUid()).getCheckedData());
            }
            responseList.add(userResponse);
        });
        PageInfo<SpreadUserResponse> responsePageInfo = CommonPage.copyPageInfo(pageUserPage, responseList);
        responsePageInfo.setTotal(userPageInfo.getTotal());
        responsePageInfo.setPages(userPageInfo.getPages());
        return CommonPage.restPage(responsePageInfo);
    }

    /**
     * 获取分销配置信息
     * @return 返回配置信息
     */
    @Override
    public RetailShopRequest getManageInfo() {
//        List<String> keys = initKeys();
        RetailShopRequest response = new RetailShopRequest();
        response.setBrokerageFuncStatus(Integer.parseInt(systemConfigService.getValueByKey("brokerage_func_status")));
//        response.setStoreBrokerageStatus(systemConfigService.getValueByKey(keys.get(1)));
        response.setStoreBrokerageRatio(Integer.parseInt(systemConfigService.getValueByKey("store_brokerage_ratio")));
        response.setStoreBrokerageTwo(Integer.parseInt(systemConfigService.getValueByKey("store_brokerage_two")));
        response.setUserExtractMinPrice(new BigDecimal(systemConfigService.getValueByKey("user_extract_min_price")));
        response.setUserExtractBank(systemConfigService.getValueByKey("user_extract_bank").replace("\\n","\n"));
        response.setExtractTime(Integer.parseInt(systemConfigService.getValueByKey("extract_time")));
        response.setStoreBrokerageQuota(Integer.parseInt(systemConfigService.getValueByKey("store_brokerage_quota")));
        response.setStoreBrokerageIsBubble(Integer.parseInt(systemConfigService.getValueByKey("store_brokerage_is_bubble")));
        response.setBrokerageBindind(Integer.parseInt(systemConfigService.getValueByKey("brokerage_bindind")));
        return response;
    }

    /**
     * 更新分销配置信息
     * @param retailShopRequest 待保存数据
     * @return 更新结果
     */
    @Override
    public boolean setManageInfo(RetailShopRequest retailShopRequest) {
        // 返佣比例之和+起来不能超过100%
        int ration = retailShopRequest.getStoreBrokerageTwo() + retailShopRequest.getStoreBrokerageRatio();
        if (ration > 100 || ration < 0) throw new XlwebException("返佣比例加起来不能超过100%");

//        List<String> keys = initKeys();
        systemConfigService.updateOrSaveValueByName("brokerage_func_status", retailShopRequest.getBrokerageFuncStatus().toString());
//        systemConfigService.updateOrSaveValueByName("store_brokerage_status", retailShopRequest.getStoreBrokerageStatus());
        systemConfigService.updateOrSaveValueByName("store_brokerage_ratio", retailShopRequest.getStoreBrokerageRatio().toString());
        systemConfigService.updateOrSaveValueByName("store_brokerage_two", retailShopRequest.getStoreBrokerageTwo().toString());
        systemConfigService.updateOrSaveValueByName("user_extract_min_price", retailShopRequest.getUserExtractMinPrice().toString());
        systemConfigService.updateOrSaveValueByName("user_extract_bank", retailShopRequest.getUserExtractBank());
        systemConfigService.updateOrSaveValueByName("extract_time", retailShopRequest.getExtractTime().toString());
//        systemConfigService.updateOrSaveValueByName(keys.get(7), retailShopRequest.getStoreBrokeragePrice().toString());
        systemConfigService.updateOrSaveValueByName("brokerage_bindind", retailShopRequest.getBrokerageBindind().toString());
        systemConfigService.updateOrSaveValueByName("store_brokerage_quota", retailShopRequest.getStoreBrokerageQuota().toString());
        systemConfigService.updateOrSaveValueByName("store_brokerage_is_bubble", retailShopRequest.getStoreBrokerageIsBubble().toString());
        return true;
    }

}
