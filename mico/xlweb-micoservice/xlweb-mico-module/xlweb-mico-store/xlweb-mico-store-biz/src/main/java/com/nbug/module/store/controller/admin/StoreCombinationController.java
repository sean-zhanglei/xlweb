package com.nbug.module.store.controller.admin;

import com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreCombinationRequest;
import com.nbug.mico.common.request.StoreCombinationSearchRequest;
import com.nbug.mico.common.request.StorePinkSearchRequest;
import com.nbug.mico.common.response.StoreCombinationResponse;
import com.nbug.mico.common.response.StorePinkAdminListResponse;
import com.nbug.mico.common.response.StorePinkDetailResponse;
import com.nbug.mico.common.response.StoreProductInfoResponse;
import com.nbug.module.store.service.StoreCombinationService;
import com.nbug.module.store.service.StorePinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 拼团商品表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("store/combination")
@Tag(name = "管理后台 - 商品——拼团——商品") //配合swagger使用
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
    @Operation(summary = "分页显示拼团商品表") //配合swagger使用
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
    @Operation(summary = "新增拼团商品")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated StoreCombinationRequest request){
        storeCombinationService.saveCombination(request);
        return CommonResult.success("新增拼团商品成功");
    }

    /**
     * 删除拼团商品表
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:combination:delete')")
    @Operation(summary = "删除拼团商品")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonResult<String> delete(@RequestParam(value = "id") Integer id){
        storeCombinationService.deleteById(id);
        return CommonResult.success("success");
    }

    /**
     * 修改拼团商品表
     * @param storeCombinationRequest 修改参数
     */
    @PreAuthorize("hasAuthority('admin:combination:update')")
    @Operation(summary = "修改拼团商品")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestBody @Validated StoreCombinationRequest storeCombinationRequest){
        storeCombinationService.updateCombination(storeCombinationRequest);
        return CommonResult.success("success");
    }

    /**
     * 查询拼团商品信息
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:combination:info')")
    @Operation(summary = "拼团商品详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<StoreProductInfoResponse> info(@RequestParam(value = "id") Integer id){
        StoreProductInfoResponse detail = storeCombinationService.getAdminDetail(id);
        return CommonResult.success(detail);
   }

    /**
     * 修改拼团商品状态
     */
    @PreAuthorize("hasAuthority('admin:combination:update:status')")
    @Operation(summary = "修改拼团商品状态")
    @RequestMapping(value = "/update/status", method = RequestMethod.POST)
    public CommonResult<Object> updateStatus(@RequestParam(value = "id") Integer id, @RequestParam @Validated boolean isShow){
        storeCombinationService.updateCombinationShow(id, isShow);
        return CommonResult.success("success");
    }

    /**
     * 拼团统计
     */
    @PreAuthorize("hasAuthority('admin:combination:statistics')")
    @Operation(summary = "拼团统计")
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> statistics() {
        Map<String, Object> map = storeCombinationService.getAdminStatistics();
        return CommonResult.success(map);
    }

    /**
     * 拼团列表
     */
    @PreAuthorize("hasAuthority('admin:combination:combine:list')")
    @Operation(summary = "拼团列表")
    @RequestMapping(value = "/combine/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<StorePinkAdminListResponse>> getCombineList(@Validated StorePinkSearchRequest request, @Validated PageParamRequest pageParamRequest){
        CommonPage<StorePinkAdminListResponse> responseCommonPage = CommonPage.restPage(storePinkService.getList(request, pageParamRequest));
        return CommonResult.success(responseCommonPage);
    }

    /**
     * 拼团订单列表
     */
    @PreAuthorize("hasAuthority('admin:combination:order:pink')")
    @Operation(summary = "拼团订单列表")
    @RequestMapping(value = "/order_pink/{id}", method = RequestMethod.GET)
    public CommonResult<List<StorePinkDetailResponse>> getPinkList(@PathVariable(value = "id") Integer id) {
        return CommonResult.success(storePinkService.getAdminList(id));
    }
}



