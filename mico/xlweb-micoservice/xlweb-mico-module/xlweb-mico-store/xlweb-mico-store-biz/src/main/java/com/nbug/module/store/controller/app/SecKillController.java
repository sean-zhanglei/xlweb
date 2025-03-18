package com.nbug.module.store.controller.app;

import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.response.SecKillResponse;
import com.nbug.mico.common.response.SeckillIndexResponse;
import com.nbug.mico.common.response.StoreSecKillH5Response;
import com.nbug.mico.common.response.StoreSeckillDetailResponse;
import com.nbug.module.store.service.StoreSeckillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * SecKillController

 */
@Slf4j
@RestController
@RequestMapping("api/front/store/seckill")
@Tag(name = "应用后台 - 秒杀商品")
public class SecKillController {

    @Autowired
    StoreSeckillService storeSeckillService;

    /**
     * 秒杀首页数据
     * @return 可秒杀配置
     */
    @Operation(summary = "秒杀首页数据")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public CommonResult<SeckillIndexResponse> index() {
        return CommonResult.success(storeSeckillService.getIndexInfo());
    }

    /**
     * 秒杀Index
     * @return 可秒杀配置
     */
    @Operation(summary = "秒杀Header")
    @RequestMapping(value = "/header", method = RequestMethod.GET)
    public CommonResult<List<SecKillResponse>> header() {
        return CommonResult.success(storeSeckillService.getForH5Index());
    }

    /**
     * 根据时间段查询秒杀信息
     * @return 查询时间内的秒杀商品列表
     */
    @Operation(summary = "秒杀列表")
    @RequestMapping(value = "/list/{timeId}", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreSecKillH5Response>> list(@PathVariable("timeId") String timeId, @ModelAttribute PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage.restPage(storeSeckillService.getKillListByTimeId(timeId, pageParamRequest)));
    }


    @Operation(summary = "详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public CommonResult<StoreSeckillDetailResponse> info(@PathVariable(value = "id") Integer id) {
        StoreSeckillDetailResponse storeSeckill = storeSeckillService.getDetailH5(id);
        return CommonResult.success(storeSeckill);
    }
}
