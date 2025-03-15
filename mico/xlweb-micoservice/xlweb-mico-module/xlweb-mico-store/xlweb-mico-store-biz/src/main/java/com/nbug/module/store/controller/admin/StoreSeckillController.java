package com.nbug.module.store.controller.admin;

import com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreSeckillAddRequest;
import com.nbug.mico.common.request.StoreSeckillSearchRequest;
import com.nbug.mico.common.response.StoreProductInfoResponse;
import com.nbug.mico.common.response.StoreSeckillResponse;
import com.nbug.module.store.service.StoreSeckillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 商品秒杀产品表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/admin/store/seckill")
@Tag(name = "商品 -- 秒杀 -- 商品") //配合swagger使用
public class StoreSeckillController {

    @Autowired
    private StoreSeckillService storeSeckillService;

    /**
     * 分页显示商品秒杀产品表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:seckill:list')")
    @Operation(summary = "分页列表") //配合swagger使用
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreSeckillResponse>>  getList(@Validated StoreSeckillSearchRequest request, @Validated PageParamRequest pageParamRequest) {
        CommonPage<StoreSeckillResponse> storeSeckillCommonPage =
                CommonPage.restPage(storeSeckillService.getList(request, pageParamRequest));
        return CommonResult.success(storeSeckillCommonPage);
    }

    /**
     * 新增商品秒杀产品表
     * @param storeSeckillRequest 新增参数
     */
    @PreAuthorize("hasAuthority('admin:seckill:save')")
    @Operation(summary = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated StoreSeckillAddRequest storeSeckillRequest) {
        if (storeSeckillService.saveSeckill(storeSeckillRequest)) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除商品秒杀产品表
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:seckill:delete')")
    @Operation(summary = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonResult<String> delete(@RequestParam(value = "id") Integer id) {
        if (storeSeckillService.deleteById(id)) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 修改商品秒杀产品表
     * @param storeSeckillRequest 修改参数
     */
    @PreAuthorize("hasAuthority('admin:seckill:update')")
    @Operation(summary = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestBody @Validated StoreSeckillAddRequest storeSeckillRequest) {
        if (storeSeckillService.updateSeckill(storeSeckillRequest)) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('admin:seckill:update:status')")
    @Operation(summary = "修改秒杀商品状态")
    @RequestMapping(value = "/update/status", method = RequestMethod.POST)
    public CommonResult<String> updateStatus(@RequestParam @Validated Integer id, @RequestParam @Validated boolean status) {
        if (storeSeckillService.updateSecKillStatus(id,status)) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 查询商品秒杀产品表信息
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:seckill:info')")
    @Operation(summary = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<StoreProductInfoResponse> info(@RequestParam(value = "id") Integer id) {
        StoreProductInfoResponse storeSeckill = storeSeckillService.getDetailAdmin(id);
        return CommonResult.success(storeSeckill);
   }
}



