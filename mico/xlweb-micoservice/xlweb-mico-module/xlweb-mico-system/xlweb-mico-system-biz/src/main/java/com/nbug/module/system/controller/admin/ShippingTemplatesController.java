package com.nbug.module.system.controller.admin;

import com.nbug.mico.common.model.express.ShippingTemplates;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.ShippingTemplatesRequest;
import com.nbug.mico.common.request.ShippingTemplatesSearchRequest;
import com.nbug.module.system.service.ShippingTemplatesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

import static com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;

/**
 * 物流-模板控制器

 */
@Slf4j
@RestController
@RequestMapping("api/admin/system/express/shipping/templates")
@Tag(name = "设置 -- 物流 -- 模板")
public class ShippingTemplatesController {

    @Autowired
    private ShippingTemplatesService shippingTemplatesService;

    /**
     * 分页显示
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:shipping:templates:list')")
    @Operation(summary = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<ShippingTemplates>>  getList(@Validated ShippingTemplatesSearchRequest request, @Validated PageParamRequest pageParamRequest){
        CommonPage<ShippingTemplates> shippingTemplatesCommonPage = CommonPage.restPage(shippingTemplatesService.getList(request, pageParamRequest));
        return CommonResult.success(shippingTemplatesCommonPage);
    }

    /**
     * 新增
     * @param request 新增参数
     */
    @PreAuthorize("hasAuthority('admin:shipping:templates:save')")
    @Operation(summary = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated ShippingTemplatesRequest request){
        if (shippingTemplatesService.create(request)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR,"新增运费模板失败");
    }

    /**
     * 删除
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:shipping:templates:delete')")
    @Operation(summary = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @Parameter(name="id", description="模板ID", required = true)
    public CommonResult<String> delete(@RequestParam(value = "id") Integer id){
        if(shippingTemplatesService.remove(id)){
            return CommonResult.success("success");
        }else{
            return CommonResult.error(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 修改
     * @param id integer id
     * @param request ShippingTemplatesRequest 修改参数
     */
    @PreAuthorize("hasAuthority('admin:shipping:templates:update')")
    @Operation(summary = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestParam Integer id, @RequestBody @Validated ShippingTemplatesRequest request){
        if (shippingTemplatesService.update(id, request)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 查询信息
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:shipping:templates:info')")
    @Operation(summary = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @Parameter(name="id", description="模板ID", required = true)
    public CommonResult<ShippingTemplates> info(@RequestParam(value = "id") Integer id){
        return CommonResult.success(shippingTemplatesService.getInfo(id));
   }
}



