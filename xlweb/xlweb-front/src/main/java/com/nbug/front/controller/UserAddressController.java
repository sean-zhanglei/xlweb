package com.nbug.front.controller;

import com.nbug.common.page.CommonPage;
import com.nbug.common.request.UserAddressDelRequest;
import com.nbug.common.request.UserAddressRequest;
import com.nbug.common.response.CommonResult;
import com.nbug.common.request.PageParamRequest;
import com.nbug.common.model.user.UserAddress;
import com.nbug.service.service.UserAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 用户地址 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/front/address")
@Api(tags = "用户 -- 地址")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    /**
     * 分页显示用户地址
     */
    @ApiOperation(value = "列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserAddress>>  getList(PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage.restPage(userAddressService.getList(pageParamRequest)));
    }

    /**
     * 新增用户地址
     * @param request 新增参数
     */
    @ApiOperation(value = "保存")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonResult<UserAddress> save(@RequestBody @Validated UserAddressRequest request) {
        return CommonResult.success(userAddressService.create(request));
    }

    /**
     * 删除用户地址
     * @param request UserAddressDelRequest 参数
     */
    @ApiOperation(value = "删除")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public CommonResult<String> delete(@RequestBody UserAddressDelRequest request) {
        if (userAddressService.delete(request.getId())) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 地址详情
     */
    @ApiOperation(value = "地址详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public CommonResult<UserAddress> info(@PathVariable("id") Integer id) {
        return CommonResult.success(userAddressService.getDetail(id));
    }

    /**
     * 获取默认地址
     */
    @ApiOperation(value = "获取默认地址")
    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public CommonResult<UserAddress> getDefault() {
        return CommonResult.success(userAddressService.getDefault());

    }

    /**
     * 设置默认地址
     * @param request UserAddressDelRequest 参数
     */
    @ApiOperation(value = "设置默认地址")
    @RequestMapping(value = "/default/set", method = RequestMethod.POST)
    public CommonResult<UserAddress> def(@RequestBody UserAddressDelRequest request) {
        if (userAddressService.def(request.getId())) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }
}



