package com.nbug.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbug.mico.common.model.system.SystemConfig;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.RetailShopRequest;
import com.nbug.mico.common.response.SpreadUserResponse;

/**
 * 分销业务

 */
public interface RetailShopService extends IService<SystemConfig> {

    /**
     * 分销员列表
     * @param keywords 搜索参数
     * @param dateLimit 时间参数
     * @param pageRequest 分页参数
     * @return CommonPage
     */
    CommonPage<SpreadUserResponse> getSpreadPeopleList(String keywords, String dateLimit, PageParamRequest pageRequest);

    /**
     * 获取分销配置
     * @return 分销配置信息
     */
    RetailShopRequest getManageInfo();

    /**
     * 保存或者更新分销配置信息
     * @param retailShopRequest 待保存数据
     * @return 保存结果
     */
    boolean setManageInfo(RetailShopRequest retailShopRequest);

}
