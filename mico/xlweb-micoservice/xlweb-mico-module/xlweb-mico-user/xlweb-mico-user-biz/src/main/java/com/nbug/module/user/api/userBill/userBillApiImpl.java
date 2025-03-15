package com.nbug.module.user.api.userBill;

import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.model.user.UserBill;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.FundsMonitorSearchRequest;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreOrderRefundRequest;
import com.nbug.mico.common.vo.MyRecord;
import com.nbug.module.user.service.UserBillService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class userBillApiImpl implements UserBillApi {

    @Resource
    private UserBillService userBillService;

    @Override
    public CommonResult<Boolean> save(UserBill userBill) {
        return success(userBillService.save(userBill));
    }

    /**
     * 保存退款日志
     *
     * @return boolean
     */
    @Override
    public CommonResult<Boolean> saveRefundBill(StoreOrderRefundRequest request, User user) {
        return success(userBillService.saveRefundBill(request, user));
    }

    @Override
    public CommonResult<List<Map<String, Object>>> listMaps(String timeType, Date startDate, Date endDate) {
        return success(userBillService.listMaps(timeType, startDate, endDate));
    }

    @Override
    public CommonResult<Boolean> saveBatch(List<UserBill> billList) {
        return success(userBillService.saveBatch(billList));
    }

    /**
     * 获取账单记录列表
     * @param type
     * @param time
     * @param pageParamRequest
     * @return
     */
    @Override
    public CommonResult<MyRecord> getFlowRecord(String type, String time, PageParamRequest pageParamRequest) {
        return success(userBillService.getFlowRecord(type, time, pageParamRequest));
    }

    /**
     * 资金流水统计数据
     * @param ids
     * @param keywords
     * @param pageParamRequest
     * @return
     */
    @Override
    public CommonResult<MyRecord> getFlowList(List<Integer> ids, String keywords, PageParamRequest pageParamRequest) {
        return success(userBillService.getFlowList(ids, keywords, pageParamRequest));
    }

    @Override
    public CommonResult<MyRecord> getBalanceBasic(String time) {
        return success(userBillService.getBalanceBasic(time));
    }

    @Override
    public CommonResult<MyRecord> getBalanceTrend(String time) {
        return success(userBillService.getBalanceTrend(time));
    }

    @Override
    public CommonResult<MyRecord> getBalanceChannel(String time) {
        return success(userBillService.getBalanceChannel(time));
    }

    @Override
    public CommonResult<MyRecord> getBalanceType(String time) {
        return success(userBillService.getBalanceType(time));
    }

    @Override
    public CommonResult<MyRecord> getBalanceList(FundsMonitorSearchRequest request, PageParamRequest pageParamRequest) {
        return success(userBillService.getBalanceList(request, pageParamRequest));
    }
}
