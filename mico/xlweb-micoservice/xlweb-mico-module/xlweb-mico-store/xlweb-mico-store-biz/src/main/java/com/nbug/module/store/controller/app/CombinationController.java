package com.nbug.module.store.controller.app;

import com.github.pagehelper.PageInfo;
import com.nbug.mico.common.model.combination.StoreCombination;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StorePinkRequest;
import com.nbug.mico.common.response.CombinationDetailResponse;
import com.nbug.mico.common.response.CombinationHeaderResponse;
import com.nbug.mico.common.response.CombinationIndexResponse;
import com.nbug.mico.common.response.GoPinkResponse;
import com.nbug.mico.common.response.StoreCombinationH5Response;
import com.nbug.module.store.service.StoreCombinationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;

/**
 * 拼团商品

 */
@Slf4j
@RestController
@RequestMapping("api/front/store/combination")
@Tag(name = "应用后台 - 拼团商品")
public class CombinationController {

    @Autowired
    private StoreCombinationService storeCombinationService;

    /**
     * 拼团首页
     */
    @Operation(summary = "拼团首页数据")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public CommonResult<CombinationIndexResponse> index() {
        return CommonResult.success(storeCombinationService.getIndexInfo());
    }

    /**
     * 拼团商品列表header
     */
    @Operation(summary = "拼团商品列表header")
    @RequestMapping(value = "/header", method = RequestMethod.GET)
    public CommonResult<CombinationHeaderResponse> header() {
        return CommonResult.success(storeCombinationService.getHeader());
    }

    /**
     * 拼团商品列表
     */
    @Operation(summary = "拼团商品列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreCombinationH5Response>> list(@ModelAttribute PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage.restPage(storeCombinationService.getH5List(pageParamRequest)));
    }

    /**
     * 拼团商品详情
     */
    @Operation(summary = "拼团商品详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public CommonResult<CombinationDetailResponse> detail(@PathVariable(value = "id") Integer id) {
        CombinationDetailResponse h5Detail = storeCombinationService.getH5Detail(id);
        return CommonResult.success(h5Detail);
    }

    /**
     * 去拼团
     * @param pinkId 拼团团长单id
     */
    @Operation(summary = "去拼团")
    @RequestMapping(value = "/pink/{pinkId}", method = RequestMethod.GET)
    public CommonResult<GoPinkResponse> goPink(@PathVariable(value = "pinkId") Integer pinkId) {
        GoPinkResponse goPinkResponse = storeCombinationService.goPink(pinkId);
        return CommonResult.success(goPinkResponse);
    }

    /**
     * 更多拼团
     */
    @Operation(summary = "更多拼团")
    @RequestMapping(value = "/more", method = RequestMethod.GET)
    public CommonResult<PageInfo<StoreCombination>> getMore(@RequestParam Integer comId, @Validated PageParamRequest pageParamRequest) {
        PageInfo<StoreCombination> more = storeCombinationService.getMore(pageParamRequest, comId);
        return CommonResult.success(more);
    }

    /**
     * 取消拼团
     */
    @Operation(summary = "取消拼团")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonResult<Object> remove(@RequestBody @Validated StorePinkRequest storePinkRequest) {
        if (storeCombinationService.removePink(storePinkRequest)) {
            return CommonResult.success("取消成功");
        } else {
            return CommonResult.error(INTERNAL_SERVER_ERROR, "取消失败");
        }
    }

}
