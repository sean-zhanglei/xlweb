package com.nbug.admin.controller;

import com.nbug.common.page.CommonPage;
import com.nbug.common.response.CommonResult;
import com.nbug.common.request.PageParamRequest;
import com.nbug.common.request.StoreCombinationRequest;
import com.nbug.common.request.StoreCombinationSearchRequest;
import com.nbug.common.request.StorePinkSearchRequest;
import com.nbug.common.response.StoreCombinationResponse;
import com.nbug.common.response.StorePinkAdminListResponse;
import com.nbug.common.response.StorePinkDetailResponse;
import com.nbug.common.response.StoreProductInfoResponse;
import com.nbug.service.service.StoreCombinationService;
import com.nbug.service.service.StorePinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 拼团商品表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/admin/store/combination")
@Api(tags = "商品——拼团——商品") //配合swagger使用
public class StoreCombinationController {

    @Autowired
    private StoreCombinationService storeCombinationService;

    @Autowired
    private StorePinkService storePinkService;

    /**
     * 分页显示拼团商品表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     * @return
     */
    @PreAuthorize("hasAuthority('admin:combination:list')")
    @ApiOperation(value = "分页显示拼团商品表") //配合swagger使用
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreCombinationResponse>> getList(@Validated StoreCombinationSearchRequest request, @Validated PageParamRequest pageParamRequest){
        CommonPage<StoreCombinationResponse> commonPage = CommonPage.restPage(storeCombinationService.getList(request, pageParamRequest));
        return CommonResult.success(commonPage);
    }

    /**
     * 新增拼团商品表
     * @param request 新增参数
     */
    @PreAuthorize("hasAuthority('admin:combination:save')")
    @ApiOperation(value = "新增拼团商品")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated StoreCombinationRequest request){
        if(storeCombinationService.saveCombination(request)){
            return CommonResult.success("新增拼团商品成功");
        }else{
            return CommonResult.failed("新增拼团商品失败");
        }
    }

    /**
     * 删除拼团商品表
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:combination:delete')")
    @ApiOperation(value = "删除拼团商品")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonResult<String> delete(@RequestParam(value = "id") Integer id){
        if(storeCombinationService.deleteById(id)){
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }

    /**
     * 修改拼团商品表
     * @param storeCombinationRequest 修改参数
     */
    @PreAuthorize("hasAuthority('admin:combination:update')")
    @ApiOperation(value = "修改拼团商品")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestBody @Validated StoreCombinationRequest storeCombinationRequest){
        if(storeCombinationService.updateCombination(storeCombinationRequest)){
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }

    /**
     * 查询拼团商品信息
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:combination:info')")
    @ApiOperation(value = "拼团商品详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<StoreProductInfoResponse> info(@RequestParam(value = "id") Integer id){
        StoreProductInfoResponse detail = storeCombinationService.getAdminDetail(id);
        return CommonResult.success(detail);
   }

    /**
     * 修改拼团商品状态
     */
    @PreAuthorize("hasAuthority('admin:combination:update:status')")
    @ApiOperation(value = "修改拼团商品状态")
    @RequestMapping(value = "/update/status", method = RequestMethod.POST)
    public CommonResult<Object> updateStatus(@RequestParam(value = "id") Integer id, @RequestParam @Validated boolean isShow){
        if(storeCombinationService.updateCombinationShow(id, isShow)){
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }

    /**
     * 拼团统计
     */
    @PreAuthorize("hasAuthority('admin:combination:statistics')")
    @ApiOperation(value = "拼团统计")
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> statistics() {
        Map<String, Object> map = storeCombinationService.getAdminStatistics();
        return CommonResult.success(map);
    }

    /**
     * 拼团列表
     */
    @PreAuthorize("hasAuthority('admin:combination:combine:list')")
    @ApiOperation(value = "拼团列表")
    @RequestMapping(value = "/combine/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<StorePinkAdminListResponse>> getCombineList(@Validated StorePinkSearchRequest request, @Validated PageParamRequest pageParamRequest){
        CommonPage<StorePinkAdminListResponse> responseCommonPage = CommonPage.restPage(storePinkService.getList(request, pageParamRequest));
        return CommonResult.success(responseCommonPage);
    }

    /**
     * 拼团订单列表
     */
    @PreAuthorize("hasAuthority('admin:combination:order:pink')")
    @ApiOperation(value = "拼团订单列表")
    @RequestMapping(value = "/order_pink/{id}", method = RequestMethod.GET)
    public CommonResult<List<StorePinkDetailResponse>> getPinkList(@PathVariable(value = "id") Integer id) {
        return CommonResult.success(storePinkService.getAdminList(id));
    }
}



