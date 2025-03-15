package com.nbug.module.user.api.userBrokerageRecord;

import com.nbug.mico.common.model.user.UserBrokerageRecord;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.user.service.UserBrokerageRecordService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class UserBrokerageRecordApiImpl implements UserBrokerageRecordApi {

    @Resource
    private UserBrokerageRecordService userBrokerageRecordService;

    /**
     * 获取记录(订单不可用此方法)
     * @param linkId 关联id
     * @param linkType 关联类型
     * @return 记录列表
     */
    @Override
    public CommonResult<List<UserBrokerageRecord>> findListByLinkIdAndLinkType(String linkId, String linkType) {
        return success(userBrokerageRecordService.findListByLinkIdAndLinkType(linkId, linkType));
    }

    @Override
    public CommonResult<Boolean> updateBatchById(List<UserBrokerageRecord> userBrokerageRecords) {
        return success(userBrokerageRecordService.updateBatchById(userBrokerageRecords));
    }

    /**
     * 获取记录(订单不可用此方法)
     * @param linkId 关联id
     * @param linkType 关联类型
     * @return 记录列表
     */
    @Override
    public CommonResult<UserBrokerageRecord> getByLinkIdAndLinkType(String linkId, String linkType) {
        return success(userBrokerageRecordService.getByLinkIdAndLinkType(linkId, linkType));
    }

    @Override
    public CommonResult<Boolean> saveBatch(List<UserBrokerageRecord> userBrokerageRecords) {
        return success(userBrokerageRecordService.saveBatch(userBrokerageRecords));
    }

    /**
     * 根据Uid和时间参数获取分佣记录列表
     * @param uid 用户uid
     * @return List
     */
    @Override
    public CommonResult<List<UserBrokerageRecord>> getSpreadListByUid(Integer uid) {
        return success(userBrokerageRecordService.getSpreadListByUid(uid));
    }

    /**
     * 获取冻结期佣金
     * @param uid uid
     * @return BigDecimal
     */
    @Override
    public CommonResult<BigDecimal> getFreezePrice(Integer uid) {
        return success(userBrokerageRecordService.getFreezePrice(uid));
    }
}
