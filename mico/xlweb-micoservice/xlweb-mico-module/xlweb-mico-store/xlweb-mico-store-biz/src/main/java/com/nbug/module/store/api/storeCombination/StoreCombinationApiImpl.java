package com.nbug.module.store.api.storeCombination;

import com.github.pagehelper.PageInfo;
import com.nbug.mico.common.model.combination.StoreCombination;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreCombinationSearchRequest;
import com.nbug.mico.common.response.StoreCombinationResponse;
import com.nbug.module.store.service.StoreCombinationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class StoreCombinationApiImpl implements StoreCombinationApi {

    @Resource
    private StoreCombinationService storeCombinationService;

    /**
     * 添加/扣减库存
     * @param id 秒杀商品id
     * @param num 数量
     * @param type 类型：add—添加，sub—扣减
     */
    @Override
    public CommonResult<Boolean> operationStock(Integer id, Integer num, String type) {
        return success(storeCombinationService.operationStock(id, num, type));
    }


    /**
     * 查询带异常
     * @param combinationId 拼团商品id
     * @return StoreCombination
     */
    @Override
    public CommonResult<StoreCombination> getByIdException(Integer combinationId) {
        return success(storeCombinationService.getByIdException(combinationId));
    }

    @Override
    public CommonResult<StoreCombination> getById(Integer id) {
        return success(storeCombinationService.getById(id));
    }

    /**
     * 分页显示拼团商品表
     * @param request   搜索条件
     * @param pageParamRequest  分页参数
     */
    @Override
    public CommonResult<PageInfo<StoreCombinationResponse>> getList(StoreCombinationSearchRequest request, PageParamRequest pageParamRequest) {
        return success(storeCombinationService.getList(request, pageParamRequest));
    }
}
