package com.nbug.module.user.api.userVisitRecord;

import com.nbug.mico.common.model.record.UserVisitRecord;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.user.service.UserVisitRecordService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class userVisitRecordApiImpl implements UserVisitRecordApi {

    @Resource
    private UserVisitRecordService userVisitRecordService;

    @Override
    public CommonResult<Boolean> save(UserVisitRecord userVisitRecord) {

        return success(userVisitRecordService.save(userVisitRecord));
    }

    /**
     * 通过日期获取浏览量
     * @param date
     * @return
     */
    @Override
    public CommonResult<Integer> getPageviewsByDate(String date) {

        return success(userVisitRecordService.getPageviewsByDate(date));
    }

    /**
     * 通过日期获取活跃用户数
     * @param date
     * @return
     */
    @Override
    public CommonResult<Integer> getActiveUserNumByDate(String date) {

        return success(userVisitRecordService.getActiveUserNumByDate(date));
    }
}
