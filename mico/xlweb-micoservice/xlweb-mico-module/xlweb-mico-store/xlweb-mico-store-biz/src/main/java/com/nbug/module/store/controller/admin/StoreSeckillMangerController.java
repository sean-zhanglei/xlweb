package com.nbug.module.store.controller.admin;

import com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreSeckillMangerRequest;
import com.nbug.mico.common.request.StoreSeckillMangerSearchRequest;
import com.nbug.mico.common.response.StoreSeckillManagerResponse;
import com.nbug.module.store.service.StoreSeckillMangerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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


/**
 * 秒杀配置 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/admin/store/seckill/manger")
@Tag(name = "商品 -- 秒杀 -- 配置") //配合swagger使用
public class StoreSeckillMangerController {

    @Autowired
    private StoreSeckillMangerService storeSeckillMangerService;

    /**
     * 分页显示
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:seckill:manger:list')")
    @Operation(summary = "分页列表") //配合swagger使用
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreSeckillManagerResponse>>  getList(
            @Validated StoreSeckillMangerSearchRequest request, @Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage.restPage(storeSeckillMangerService.getList(request, pageParamRequest)));
    }

    /**
     * 新增
     * @param storeSeckillMangerRequest 新增参数
     */
    @PreAuthorize("hasAuthority('admin:seckill:manger:save')")
    @Operation(summary = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated StoreSeckillMangerRequest storeSeckillMangerRequest) {
        if (storeSeckillMangerService.saveManger(storeSeckillMangerRequest)) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 删除
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:seckill:manger:delete')")
    @Operation(summary = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonResult<String> delete(@RequestParam(value = "id") Integer id) {
        if (storeSeckillMangerService.deleteLogicById(id)) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 修改
     * @param id integer id
     * @param storeSeckillMangerRequest 修改参数
     */
    @PreAuthorize("hasAuthority('admin:seckill:manger:update')")
    @Operation(summary = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestParam Integer id, @RequestBody @Validated StoreSeckillMangerRequest storeSeckillMangerRequest) {
        return storeSeckillMangerService.update(id, storeSeckillMangerRequest) ? CommonResult.success("success") : CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
    }

    /**
     * 查询信息
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:seckill:manger:info')")
    @Operation(summary = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<StoreSeckillManagerResponse> info(@RequestParam(value = "id") Integer id) {
        return CommonResult.success(storeSeckillMangerService.detail(id));
   }

    /**
     * 更新秒杀配置状态
     * @param id    配置id
     * @param status    待更新配置状态
     * @return  更新结果
     */
    @PreAuthorize("hasAuthority('admin:seckill:manger:update:status')")
   @Operation(summary = "秒杀配置状态更新")
   @RequestMapping(value = "/update/status/{id}", method = RequestMethod.POST)
   @Parameters({
           @Parameter(name = "id", description = "商品id", required = true),
           @Parameter(name = "status", description = "状态", required = true)
   })
   public CommonResult<Object> updateStatus(@PathVariable(value = "id") Integer id, Boolean status) {
        return CommonResult.success(storeSeckillMangerService.updateStatus(id,status));
   }


}



