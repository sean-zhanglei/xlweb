package com.nbug.module.store.api.storeProductRelation;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.UserCollectAllRequest;
import com.nbug.mico.common.request.UserCollectRequest;
import com.nbug.mico.common.response.UserRelationResponse;
import com.nbug.module.store.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 用户收藏")
public interface StoreProductRelationApi {

    String PREFIX = ApiConstants.PREFIX + "/storeProductRelation";

    @GetMapping(PREFIX + "/getCollectCountByUid")
    @Operation(summary = "获取用户收藏数量")
    @Parameter(name = "userId", description = "用户id", required = true)
    public CommonResult<Integer> getCollectCountByUid(@RequestParam Integer userId);

    @GetMapping(PREFIX + "/getUserList")
    @Operation(summary = "获取用户收藏列表")
    @Parameter(name = "pageParamRequest", description = "分页参数", required = true)
    public CommonResult<List<UserRelationResponse>> getUserList(@SpringQueryMap PageParamRequest pageParamRequest);

    @PostMapping(PREFIX + "/add")
    @Operation(summary = "添加收藏产品")
    @Parameter(name = "request", description = "收藏产品", required = true)
    public CommonResult<Boolean> add(@Validated @RequestBody UserCollectRequest request);

    @PostMapping(PREFIX + "/delete")
    @Operation(summary = "取消收藏产品")
    @Parameter(name = "requestJson", description = "收藏产品", required = true)
    public CommonResult<Boolean> delete(@RequestParam String requestJson);

    @PostMapping(PREFIX + "/deleteByProId")
    @Operation(summary = "取消收藏产品(通过商品)")
    @Parameter(name = "proId", description = "商品id", required = true)
    public CommonResult<Boolean> deleteByProId(@RequestParam Integer proId);

    @PostMapping(PREFIX + "/deleteByProIdAndUid")
    @Operation(summary = "取消收藏产品(通过商品和用户)")
    @Parameter(name = "proId", description = "商品id", required = true)
    public CommonResult<Boolean> deleteByProIdAndUid(@RequestParam Integer proId);

    @PostMapping(PREFIX + "/all")
    @Operation(summary = "批量收藏")
    @Parameter(name = "request", description = "批量收藏", required = true)
    public CommonResult<Boolean> all(@Validated @RequestBody UserCollectAllRequest request);
}
