package com.nbug.module.user.controller.app;

import com.nbug.mico.common.model.user.UserAddress;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.UserAddressDelRequest;
import com.nbug.mico.common.request.UserAddressRequest;
import com.nbug.module.user.service.UserAddressService;
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
 * 用户地址 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/front/user/address")
@Tag(name = "应用后台 - 用户 -- 地址")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    /**
     * 分页显示用户地址
     */
    @Operation(summary = "列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserAddress>>  getList(PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage.restPage(userAddressService.getList(pageParamRequest)));
    }

    /**
     * 新增用户地址
     * @param request 新增参数
     */
    @Operation(summary = "保存")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonResult<UserAddress> save(@RequestBody @Validated UserAddressRequest request) {
        return CommonResult.success(userAddressService.create(request));
    }

    /**
     * 删除用户地址
     * @param request UserAddressDelRequest 参数
     */
    @Operation(summary = "删除")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public CommonResult<String> delete(@RequestBody UserAddressDelRequest request) {
        if (userAddressService.delete(request.getId())) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 地址详情
     */
    @Operation(summary = "地址详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public CommonResult<UserAddress> info(@PathVariable("id") Integer id) {
        return CommonResult.success(userAddressService.getDetail(id));
    }

    /**
     * 获取默认地址
     */
    @Operation(summary = "获取默认地址")
    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public CommonResult<UserAddress> getDefault() {
        return CommonResult.success(userAddressService.getDefault());

    }

    /**
     * 设置默认地址
     * @param request UserAddressDelRequest 参数
     */
    @Operation(summary = "设置默认地址")
    @RequestMapping(value = "/default/set", method = RequestMethod.POST)
    public CommonResult<Object> def(@RequestBody UserAddressDelRequest request) {
        if (userAddressService.def(request.getId())) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(INTERNAL_SERVER_ERROR);
        }
    }
}



