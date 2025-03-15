package com.nbug.module.system.api.category;

import com.nbug.mico.common.model.category.Category;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.vo.CategoryTreeVo;
import com.nbug.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;

@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 分类")
public interface CategoryApi {

    String PREFIX = ApiConstants.PREFIX + "/category";

    @GetMapping(PREFIX + "/getByIds")
    @Operation(summary = "根据id获取分类集合")
    @Parameter(name = "adminIdList", description = "分类ID集合", required = true)
    public CommonResult<List<Category>> getByIds(List<Integer> adminIdList);

    @GetMapping(PREFIX + "/getChildVoListByPid")
    @Operation(summary = "根据父级id获取子级分类集合")
    @Parameter(name = "pid", description = "父级分类ID", required = true)
    public CommonResult<List<Category>> getChildVoListByPid(Integer pid);

    @GetMapping(PREFIX + "/getListInId")
    @Operation(summary = "根据id获取分类集合")
    @Parameter(name = "cateIdList", description = "分类ID集合", required = true)
    public CommonResult<HashMap<Integer, String>> getListInId(List<Integer> cateIdList);

    @GetMapping(PREFIX + "/getListTree")
    @Operation(summary = "获取树形结构数据")
    @Parameters({
            @Parameter(name = "type", description = "分类类型", required = true),
            @Parameter(name = "status", description = "状态", required = true),
            @Parameter(name = "name", description = "名称")
    })
    public CommonResult<List<CategoryTreeVo>> getListTree(Integer type, Integer status, String name);
}
