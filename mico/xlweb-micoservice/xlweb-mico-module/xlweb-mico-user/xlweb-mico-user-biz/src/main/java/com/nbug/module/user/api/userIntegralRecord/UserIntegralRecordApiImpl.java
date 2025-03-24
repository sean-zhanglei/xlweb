package com.nbug.module.user.api.userIntegralRecord;

import com.nbug.mico.common.model.user.UserIntegralRecord;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.vo.MyRecord;
import com.nbug.module.user.service.UserIntegralRecordService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class UserIntegralRecordApiImpl implements UserIntegralRecordApi {

    @Resource
    private UserIntegralRecordService   userIntegralRecordService;

    /**
     * 根据订单编号、uid获取记录列表
     * @param orderNo 订单编号
     * @param userId 用户uid
     * @return 记录列表
     */
    @Override
    public CommonResult<List<UserIntegralRecord>> findListByOrderIdAndUid(String orderNo, Integer userId) {
        return success(userIntegralRecordService.findListByOrderIdAndUid(orderNo, userId));
    }

    @Override
    public CommonResult<Boolean> saveBatch(List<UserIntegralRecord> userIntegralRecords) {
        return success(userIntegralRecordService.saveBatch(userIntegralRecords));
    }

    @Override
    public CommonResult<Boolean> updateBatchById(List<UserIntegralRecord> userIntegralRecords) {
        return success(userIntegralRecordService.updateBatchById(userIntegralRecords));
    }

    @Override
    public CommonResult<MyRecord> getIntegralBasic( String time) {
        return success(userIntegralRecordService.getIntegralBasic(time));
    }

    @Override
    public CommonResult<MyRecord> getIntegralTrend(String time) {
        return success(userIntegralRecordService.getIntegralTrend(time));
    }

    @Override
    public CommonResult<MyRecord> getIntegralChannel(String time) {
        return success(userIntegralRecordService.getIntegralChannel(time));
    }

    @Override

    public CommonResult<MyRecord> getIntegralType(String time) {
        return success(userIntegralRecordService.getIntegralType(time));
    }
}
