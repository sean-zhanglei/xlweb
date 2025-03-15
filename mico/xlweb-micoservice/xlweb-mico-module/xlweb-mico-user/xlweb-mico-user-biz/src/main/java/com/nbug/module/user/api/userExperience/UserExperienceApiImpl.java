package com.nbug.module.user.api.userExperience;

import com.nbug.mico.common.model.user.UserExperienceRecord;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.user.service.UserExperienceRecordService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class UserExperienceApiImpl implements UserExperienceApi {

    @Resource
    private UserExperienceRecordService userExperienceRecordService;


    /**
     * 通过订单编号获取记录
     * @param orderNo 订单编号
     * @param uid uid
     * @return UserExperienceRecord
     */
    @Override
    public CommonResult<UserExperienceRecord> getByOrderNoAndUid(String orderNo, Integer uid) {
        return success(userExperienceRecordService.getByOrderNoAndUid(orderNo, uid));
    }

    @Override
    public CommonResult<Boolean> save(UserExperienceRecord userExperienceRecord) {
        return success(userExperienceRecordService.save(userExperienceRecord));
    }
}
