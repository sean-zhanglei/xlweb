package com.nbug.module.store.controller.admin;

import com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants;
import com.nbug.mico.common.model.product.StoreProductRule;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreProductRuleRequest;
import com.nbug.mico.common.request.StoreProductRuleSearchRequest;
import com.nbug.mico.common.utils.XlwebUtil;
import com.nbug.module.store.service.StoreProductRuleService;
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
import org.springframework.web.bind.annotation.RestController;


/**
 * 商品规则值(规格)表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/admin/store/product/rule")
@Tag(name = "管理后台 - 商品 -- 规则值(规格)") //配合swagger使用
public class StoreProductRuleController {

    @Autowired
    private StoreProductRuleService storeProductRuleService;

    /**
     * 分页显示商品规则值(规格)表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:product:rule:list')")
    @Operation(summary = "分页列表") //配合swagger使用
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreProductRule>>  getList(
            @Validated StoreProductRuleSearchRequest request, @Validated PageParamRequest pageParamRequest) {
        CommonPage<StoreProductRule> storeProductRuleCommonPage =
                CommonPage.restPage(storeProductRuleService.getList(request, pageParamRequest));
        return CommonResult.success(storeProductRuleCommonPage);
    }

    /**
     * 新增商品规则值(规格)表
     * @param storeProductRuleRequest 新增参数
     */
    @PreAuthorize("hasAuthority('admin:product:rule:save')")
    @Operation(summary = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated StoreProductRuleRequest storeProductRuleRequest) {
        if (storeProductRuleService.save(storeProductRuleRequest)) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除商品规则值(规格)表
     * @param ids Integer
     */
    @PreAuthorize("hasAuthority('admin:product:rule:delete')")
    @Operation(summary = "删除")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    public CommonResult<String> delete(@PathVariable String ids) {
        if (storeProductRuleService.removeByIds(XlwebUtil.stringToArray(ids))) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 修改商品规则值(规格)表
     * @param storeProductRuleRequest 修改参数
     */
    @PreAuthorize("hasAuthority('admin:product:rule:update')")
    @Operation(summary = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestBody @Validated StoreProductRuleRequest storeProductRuleRequest) {
        if (storeProductRuleService.updateRule(storeProductRuleRequest)) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 查询商品规则值(规格)表信息
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:product:rule:info')")
    @Operation(summary = "详情")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public CommonResult<StoreProductRule> info(@PathVariable Integer id) {
        StoreProductRule storeProductRule = storeProductRuleService.getById(id);
        return CommonResult.success(storeProductRule);
   }
}



