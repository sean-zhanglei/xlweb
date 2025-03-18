package com.nbug.module.store.controller.app;

import com.github.pagehelper.PageInfo;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.BargainFrontRequest;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.response.BargainDetailH5Response;
import com.nbug.mico.common.response.BargainHeaderResponse;
import com.nbug.mico.common.response.BargainIndexResponse;
import com.nbug.mico.common.response.BargainRecordResponse;
import com.nbug.mico.common.response.BargainUserInfoResponse;
import com.nbug.mico.common.response.StoreBargainDetailResponse;
import com.nbug.module.store.service.StoreBargainService;
import com.nbug.module.store.service.StoreBargainUserHelpService;
import com.nbug.module.store.service.StoreBargainUserService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * H5 砍价

 */
@Slf4j
@RestController
@RequestMapping("api/front/store/bargain")
@Tag(name = "应用后台 - 砍价商品")
public class BargainController {

    @Autowired
    private StoreBargainService storeBargainService;

    @Autowired
    private StoreBargainUserService storeBargainUserService;

    @Autowired
    private StoreBargainUserHelpService storeBargainUserHelpService;

    /**
     * 砍价首页信息
     */
    @Operation(summary = "砍价首页信息")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public CommonResult<BargainIndexResponse> index(){
        return CommonResult.success(storeBargainService.getIndexInfo());
    }

    /**
     * 砍价商品列表header
     */
    @Operation(summary = "砍价商品列表header")
    @RequestMapping(value = "/header", method = RequestMethod.GET)
    public CommonResult<BargainHeaderResponse> header(){
        return CommonResult.success(storeBargainService.getHeader());
    }

    /**
     * 砍价商品列表
     * @return 砍价商品列表
     */
    @Operation(summary = "砍价商品列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<PageInfo<StoreBargainDetailResponse>> list(@ModelAttribute PageParamRequest pageParamRequest){
        return CommonResult.success(storeBargainService.getH5List(pageParamRequest));
    }

    /**
     * 获取用户砍价信息
     */
    @Operation(summary = "获取用户砍价信息")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public CommonResult<BargainUserInfoResponse> getBargainUserInfo(@ModelAttribute @Validated BargainFrontRequest bargainFrontRequest) {
        return CommonResult.success(storeBargainUserService.getBargainUserInfo(bargainFrontRequest));
    }

    /**
     * 砍价商品详情
     */
    @Operation(summary = "砍价商品详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public CommonResult<BargainDetailH5Response> detail(@PathVariable(value = "id") Integer id) {
        BargainDetailH5Response h5Detail = storeBargainService.getH5Detail(id);
        return CommonResult.success(h5Detail);
    }

    /**
     * 创建砍价活动
     */
    @Operation(summary = "创建砍价活动")
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public CommonResult<Map<String, Object>> start(@RequestBody @Validated BargainFrontRequest bargainFrontRequest) {
        return CommonResult.success(storeBargainService.start(bargainFrontRequest).getColumns());
    }

    /**
     * 砍价
     */
    @Operation(summary = "砍价")
    @RequestMapping(value = "/help", method = RequestMethod.POST)
    public CommonResult<Map<String, Object>> help(@RequestBody @Validated BargainFrontRequest bargainFrontRequest) {
        return CommonResult.success(storeBargainUserHelpService.help(bargainFrontRequest));
    }

    /**
     * 砍价记录
     */
    @Operation(summary = "砍价记录")
    @RequestMapping(value = "/record", method = RequestMethod.GET)
    public CommonResult<CommonPage<BargainRecordResponse>> recordList(@ModelAttribute PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage.restPage(storeBargainUserService.getRecordList(pageParamRequest)));
    }

}
