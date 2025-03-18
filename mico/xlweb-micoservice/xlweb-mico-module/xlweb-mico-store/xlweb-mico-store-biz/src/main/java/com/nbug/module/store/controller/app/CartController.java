package com.nbug.module.store.controller.app;

import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.CartNumRequest;
import com.nbug.mico.common.request.CartRequest;
import com.nbug.mico.common.request.CartResetRequest;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.response.CartInfoResponse;
import com.nbug.module.store.service.StoreCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;


/**
 * 购物车 前端控制器
 */
@Slf4j
@RestController
@RequestMapping("api/front/store/cart")
@Tag(name = "应用后台 - 商品 -- 购物车") //配合swagger使用
public class CartController {

    @Autowired
    private StoreCartService storeCartService;

    /**
     * 分页显示购物车表
     */
    @Operation(summary = "分页列表") //配合swagger使用
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @Parameters({
            @Parameter(name="isValid", description="类型，true-有效商品，false-无效商品", required = true),
            @Parameter(name="page", description="页码", required = true),
            @Parameter(name="limit", description="每页数量", required = true)
    })
    public CommonResult<CommonPage<CartInfoResponse>> getList(@RequestParam Boolean isValid, @Validated PageParamRequest pageParamRequest) {
        CommonPage<CartInfoResponse> restPage = CommonPage.restPage(storeCartService.getList(pageParamRequest, isValid));
        return CommonResult.success(restPage);
    }

    /**
     * 新增购物车表
     * @param storeCartRequest 新增参数
     */
    @Operation(summary = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<HashMap<String,String>> save(@RequestBody @Validated CartRequest storeCartRequest) {
        String cartId = storeCartService.saveCate(storeCartRequest);
        if (StringUtils.isNotBlank(cartId)) {
            HashMap<String,String> result = new HashMap<>();
            result.put("cartId", cartId);
            return CommonResult.success(result);
        } else {
            return CommonResult.error(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除购物车表
     * @param ids 购物车ids
     */
    @Operation(summary = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public CommonResult<String> delete(@RequestParam(value = "ids") List<Long> ids) {
        if (storeCartService.deleteCartByIds(ids)) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 修改商品数量
     * @param id integer id
     * @param number 修改的产品数量
     */
    @Operation(summary = "修改")
    @RequestMapping(value = "/num", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestParam Integer id, @RequestParam Integer number) {
        if (storeCartService.updateCartNum(id, number)) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取购物车数量
     */
    @Operation(summary = "获取购物车数量")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public CommonResult<Map<String, Integer>> count(@Validated CartNumRequest request) {
        return CommonResult.success(storeCartService.getUserCount(request));
    }

    /**
     * 购物车重选提交
     * @param resetRequest 重选参数
     * @return 结果
     */
    @Operation(summary = "购物车重选提交")
    @RequestMapping(value = "/resetcart", method = RequestMethod.POST)
    public CommonResult<Object> resetCart(@RequestBody @Validated CartResetRequest resetRequest){
        return CommonResult.success(storeCartService.resetCart(resetRequest));
    }
}



