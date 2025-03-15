package com.nbug.module.user.controller.app;

import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.UserCollectAllRequest;
import com.nbug.mico.common.request.UserCollectRequest;
import com.nbug.mico.common.response.UserRelationResponse;
import com.nbug.module.store.api.storeProductRelation.StoreProductRelationApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;


/**
 * 商品点赞和收藏表 前端控制器
 
 */
@Slf4j
@RestController
@RequestMapping("api/front/user/collect")
@Tag(name = "用户 -- 点赞/收藏")
public class UserCollectController {

    @Autowired
    private StoreProductRelationApi storeProductRelationApi;

    /**
     * 我的收藏列表
     */
    @Operation(summary = "我的收藏列表")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserRelationResponse>> getList(@Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage.restPage(storeProductRelationApi.getUserList(pageParamRequest).getCheckedData()));
    }

    /**
     * 添加收藏产品
     * @param request StoreProductRelationRequest 新增参数
     */
    @Operation(summary = "添加收藏产品")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated UserCollectRequest request) {
        if (storeProductRelationApi.add(request).getCheckedData()) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 添加收藏产品
     * @param request UserCollectAllRequest 新增参数
     */
    @Operation(summary = "批量收藏")
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public CommonResult<String> all(@RequestBody @Validated UserCollectAllRequest request) {
        if (storeProductRelationApi.all(request).getCheckedData()) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 取消收藏产品
     */
    @Operation(summary = "取消收藏产品")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public CommonResult<String> delete(@RequestBody String requestJson) {
        if (storeProductRelationApi.delete(requestJson).getCheckedData()) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 取消收藏产品(通过商品)
     */
    @Operation(summary = "取消收藏产品(通过商品)")
    @RequestMapping(value = "/cancel/{proId}", method = RequestMethod.POST)
    public CommonResult<String> cancel(@PathVariable Integer proId) {
        if (storeProductRelationApi.deleteByProIdAndUid(proId).getCheckedData()) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(INTERNAL_SERVER_ERROR);
        }
    }
}



