package com.nbug.module.store.controller.admin;

import com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreBargainRequest;
import com.nbug.mico.common.request.StoreBargainSearchRequest;
import com.nbug.mico.common.request.StoreBargainUserSearchRequest;
import com.nbug.mico.common.response.StoreBargainResponse;
import com.nbug.mico.common.response.StoreBargainUserHelpResponse;
import com.nbug.mico.common.response.StoreBargainUserResponse;
import com.nbug.mico.common.response.StoreProductInfoResponse;
import com.nbug.module.store.service.StoreBargainService;
import com.nbug.module.store.service.StoreBargainUserHelpService;
import com.nbug.module.store.service.StoreBargainUserService;
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


/**
 * 砍价表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("store/bargain")
@Tag(name = "管理后台 - 商品 -- 砍价 -- 商品") //配合swagger使用
public class StoreBargainController {

    @Autowired
    private StoreBargainService storeBargainService;

    @Autowired
    private StoreBargainUserService storeBargainUserService;

    @Autowired
    private StoreBargainUserHelpService storeBargainUserHelpService;

    /**
     * 分页显示砍价商品列表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:bargain:list')")
    @Operation(summary = "分页显示砍价商品列表") //配合swagger使用
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreBargainResponse>>  getList(@Validated StoreBargainSearchRequest request, @Validated PageParamRequest pageParamRequest){
        CommonPage<StoreBargainResponse> storeBargainCommonPage = CommonPage.restPage(storeBargainService.getList(request, pageParamRequest));
        return CommonResult.success(storeBargainCommonPage);
    }

    /**
     * 新增砍价商品
     * @param storeBargainRequest 新增参数
     */
    @PreAuthorize("hasAuthority('admin:bargain:save')")
    @Operation(summary = "新增砍价商品")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated StoreBargainRequest storeBargainRequest){
        storeBargainService.saveBargain(storeBargainRequest);
        return CommonResult.success("添加砍价商品成功");
    }

    /**
     * 删除砍价商品
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:bargain:delete')")
    @Operation(summary = "删除砍价商品")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonResult<String> delete(@RequestParam(value = "id") Integer id){
        storeBargainService.deleteById(id);
        return CommonResult.success("success");
    }

    /**
     * 修改砍价商品
     * @param storeBargainRequest 修改参数
     */
    @PreAuthorize("hasAuthority('admin:bargain:update')")
    @Operation(summary = "修改砍价商品")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestBody @Validated StoreBargainRequest storeBargainRequest){
        storeBargainService.updateBargain(storeBargainRequest);
        return CommonResult.success("success");
    }

    /**
     * 查询砍价商品详情
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:bargain:info')")
    @Operation(summary = "查询砍价商品详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<StoreProductInfoResponse> info(@RequestParam(value = "id") Integer id){
        StoreProductInfoResponse storeBargainResponse = storeBargainService.getAdminDetail(id);
        return CommonResult.success(storeBargainResponse);
   }

   /**
    *
    * 修改砍价商品状态
    * @param id 商品id
    * @param status 商品状态
    * @return {@link CommonResult<String>}
    */
   @PreAuthorize("hasAuthority('admin:bargain:update:status')")
    @Operation(summary = "修改砍价商品状态")
    @RequestMapping(value = "/update/status", method = RequestMethod.POST)
    public CommonResult<String> updateStatus(@RequestParam @Validated Integer id, @RequestParam @Validated boolean status){
        storeBargainService.updateBargainStatus(id,status);
        return CommonResult.success("success");
    }

    /**
     * 分页显示砍价列表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:bargain:user:list')")
    @Operation(summary = "分页显示砍价列表") //配合swagger使用
    @RequestMapping(value = "/bargain_list", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreBargainUserResponse>> getBargainUserList(@Validated StoreBargainUserSearchRequest request, @Validated PageParamRequest pageParamRequest){
        CommonPage<StoreBargainUserResponse> bargainUserCommonPage = CommonPage.restPage(storeBargainUserService.getList(request, pageParamRequest));
        return CommonResult.success(bargainUserCommonPage);
    }

    /**
     * 获取砍价参与详情列表
     * @param id StoreBargainUser 砍价参与用户编号
     */
    @PreAuthorize("hasAuthority('admin:bargain:user:help:list')")
    @Operation(summary = "获取砍价参与详情列表") //配合swagger使用
    @RequestMapping(value = "/bargain_list/{id}", method = RequestMethod.GET)
    public CommonResult<List<StoreBargainUserHelpResponse>> getBargainUserHelpDetail(@PathVariable(value = "id") Integer id){
        List<StoreBargainUserHelpResponse> list = storeBargainUserHelpService.getList(id);
        return CommonResult.success(list);
    }
}



