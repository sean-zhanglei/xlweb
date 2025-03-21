package com.nbug.module.store.controller.admin;

import com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreProductAddRequest;
import com.nbug.mico.common.request.StoreProductRequest;
import com.nbug.mico.common.request.StoreProductSearchRequest;
import com.nbug.mico.common.response.StoreProductInfoResponse;
import com.nbug.mico.common.response.StoreProductResponse;
import com.nbug.mico.common.response.StoreProductTabsHeader;
import com.nbug.module.store.service.StoreCartService;
import com.nbug.module.store.service.StoreProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


/**
 * 商品表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("store/product")
@Tag(name = "管理后台 - 商品") //配合swagger使用
public class StoreProductController {

    @Autowired
    private StoreProductService storeProductService;

    @Autowired
    private StoreCartService storeCartService;

    /**
     * 分页显示商品表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:product:list')")
    @Operation(summary = "分页列表") //配合swagger使用
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreProductResponse>> getList(@Validated StoreProductSearchRequest request,
                                                                  @Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage.restPage(storeProductService.getAdminList(request, pageParamRequest)));
    }

    /**
     * 新增商品
     * @param request 新增参数
     */
    @PreAuthorize("hasAuthority('admin:product:save')")
    @Operation(summary = "新增商品")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated StoreProductAddRequest request) {
        storeProductService.save(request);
        return CommonResult.success("success");
    }

    /**
     * 删除商品表
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:product:delete')")
    @Operation(summary = "删除")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public CommonResult<String> delete(@RequestBody @PathVariable Integer id, @RequestParam(value = "type", required = false, defaultValue = "recycle")String type) {
        storeProductService.deleteProduct(id, type);
        if ("recycle".equals(type)) {
            storeCartService.productStatusNotEnable(id);
        } else {
            storeCartService.productDelete(id);
        }
        return CommonResult.success("success");
    }

    /**
     * 恢复已删除商品表
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:product:restore')")
    @Operation(summary = "恢复商品")
    @RequestMapping(value = "/restore/{id}", method = RequestMethod.GET)
    public CommonResult<String> restore(@RequestBody @PathVariable Integer id) {
        storeProductService.reStoreProduct(id);
        return CommonResult.success("success");
    }

    /**
     * 商品修改
     * @param storeProductRequest 商品参数
     */
    @PreAuthorize("hasAuthority('admin:product:update')")
    @Operation(summary = "商品修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestBody @Validated StoreProductAddRequest storeProductRequest) {
        storeProductService.update(storeProductRequest);
        return CommonResult.success("success");
    }

    /**
     * 商品详情
     * @param id 商品id
     */
    @PreAuthorize("hasAuthority('admin:product:info')")
    @Operation(summary = "商品详情")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public CommonResult<StoreProductInfoResponse> info(@PathVariable Integer id) {
        return CommonResult.success(storeProductService.getInfo(id));
   }

    /**
     * 商品tabs表头数据
     */
    @PreAuthorize("hasAuthority('admin:product:tabs:headers')")
   @Operation(summary = "商品表头数量")
   @RequestMapping(value = "/tabs/headers", method = RequestMethod.GET)
   public CommonResult<List<StoreProductTabsHeader>> getTabsHeader() {
        return CommonResult.success(storeProductService.getTabsHeader());
   }


    /**
     * 上架
     */
    @PreAuthorize("hasAuthority('admin:product:up')")
    @Operation(summary = "上架")
    @RequestMapping(value = "/putOnShell/{id}", method = RequestMethod.GET)
    public CommonResult<String> putOn(@PathVariable Integer id) {
        storeProductService.putOnShelf(id);
        return CommonResult.success("success");
    }

    /**
     * 下架
     */
    @PreAuthorize("hasAuthority('admin:product:down')")
    @Operation(summary = "下架")
    @RequestMapping(value = "/offShell/{id}", method = RequestMethod.GET)
    public CommonResult<String> offShell(@PathVariable Integer id) {
        storeProductService.offShelf(id);
        return CommonResult.success("success");
    }

    @PreAuthorize("hasAuthority('admin:product:import:product')")
    @Operation(summary = "导入99Api商品")
    @RequestMapping(value = "/importProduct", method = RequestMethod.POST)
    @Parameters({
            @Parameter(name = "form", description = "导入平台1=淘宝，2=京东，3=苏宁，4=拼多多, 5=天猫", required = true),
            @Parameter(name = "url", description = "URL", required = true),
    })
    public CommonResult<StoreProductRequest> importProduct(
            @RequestParam @Valid int form,
            @RequestParam @Valid String url) throws IOException, JSONException {
        StoreProductRequest productRequest = storeProductService.importProductFromUrl(url, form);
        return CommonResult.success(productRequest);
    }
}



