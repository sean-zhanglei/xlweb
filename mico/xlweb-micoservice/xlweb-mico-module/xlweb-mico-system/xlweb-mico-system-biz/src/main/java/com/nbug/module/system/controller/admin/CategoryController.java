package com.nbug.module.system.controller.admin;

import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.model.category.Category;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.CategoryRequest;
import com.nbug.mico.common.request.CategorySearchRequest;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.utils.XlwebUtil;
import com.nbug.mico.common.vo.CategoryTreeVo;
import com.nbug.module.infra.api.attachment.AttachmentApi;
import com.nbug.module.system.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;


/**
 * 分类表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("system/category")
@Tag(name = "管理后台 - 分类服务")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttachmentApi attachmentApi;

    /**
     * 分页显示分类表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     * @author Mr.Zhang
     * @since 2020-04-16
     */
    @PreAuthorize("hasAuthority('admin:category:list')")
    @Operation(summary = "分页分类列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<Category>>  getList(@ModelAttribute CategorySearchRequest request, @ModelAttribute PageParamRequest pageParamRequest) {
        CommonPage<Category> categoryCommonPage = CommonPage.restPage(categoryService.getList(request, pageParamRequest));
        return CommonResult.success(categoryCommonPage);
    }

    /**
     * 新增分类表
     * @param categoryRequest 新增参数
     */
    @PreAuthorize("hasAuthority('admin:category:save')")
    @Operation(summary = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@Validated CategoryRequest categoryRequest) {
        if (categoryService.create(categoryRequest)) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除分类表
     * @param id Integer
     * @author Mr.Zhang
     * @since 2020-04-16
     */
    @PreAuthorize("hasAuthority('admin:category:delete')")
    @Operation(summary = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @Parameter(name="id", description="分类ID")
    public CommonResult<String> delete(@RequestParam(value = "id") Integer id) {
        if (categoryService.delete(id) > 0) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 修改分类表
     * @param id integer id
     * @param categoryRequest 修改参数
     * @author Mr.Zhang
     * @since 2020-04-16
     */
    @PreAuthorize("hasAuthority('admin:category:update')")
    @Operation(summary = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Parameter(name="id", description="分类ID")
    public CommonResult<String> update(@RequestParam Integer id, @ModelAttribute CategoryRequest categoryRequest) {
        if (null == id || id <= 0) throw new XlwebException("id 参数不合法");
        categoryRequest.setExtra(attachmentApi.clearPrefix(categoryRequest.getExtra()).getCheckedData());
        if (categoryService.update(categoryRequest, id)) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 查询分类表信息
     * @param id Integer
     * @author Mr.Zhang
     * @since 2020-04-16
     */
    @PreAuthorize("hasAuthority('admin:category:info')")
    @Operation(summary = "分类详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @Parameter(name="id", description="分类ID")
    public CommonResult<Category> info(@RequestParam(value = "id") Integer id) {
        Category category = categoryService.getById(id);
        return CommonResult.success(category);
    }


    /**
     * 查询分类表信息
     * @author Mr.Zhang
     * @since 2020-04-16
     */
    @PreAuthorize("hasAuthority('admin:category:list:tree')")
    @Operation(summary = "获取tree结构的列表")
    @RequestMapping(value = "/list/tree", method = RequestMethod.GET)
    @Parameters({
        @Parameter(name="type", description="类型ID | 类型，1 产品分类，2 附件分类，3 文章分类， 4 设置分类， 5 菜单分类， 6 配置分类， 7 秒杀配置", example = "1"),
        @Parameter(name="status", description="-1=全部，0=未生效，1=已生效", example = "1"),
        @Parameter(name="name", description="模糊搜索", example = "电视")
    })
    public CommonResult<List<CategoryTreeVo>> getListTree(@RequestParam(name = "type") Integer type,
                                                          @RequestParam(name = "status") Integer status,
                                                          @RequestParam(name = "name", required = false) String name) {
        List<CategoryTreeVo> listTree = categoryService.getListTree(type,status,name);
        return CommonResult.success(listTree);
    }

    /**
     * 根据分类id集合获取分类数据
     * @param ids String id集合字符串
     * @since 2020-04-16
     */
    @PreAuthorize("hasAuthority('admin:category:list:ids')")
    @Operation(summary = "根据id集合获取分类列表")
    @RequestMapping(value = "/list/ids", method = RequestMethod.GET)
    @Parameter(name = "ids", description="分类id集合")
    public CommonResult<List<Category>> getByIds(@Validated @RequestParam(name = "ids") String ids) {
        return CommonResult.success(categoryService.getByIds(XlwebUtil.stringToArray(ids)));
    }

    /**
     * 更改分类状态
     * @param id Integer 分类id
     * @since 2020-04-16
     * @return
     */
    @PreAuthorize("hasAuthority('admin:category:update:status')")
    @Operation(summary = "更改分类状态")
    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.GET)
    @Parameter(name = "id", description="分类id")
    public CommonResult<Object> getByIds(@Validated @PathVariable(name = "id") Integer id) {
        if (categoryService.updateStatus(id)) {
            return CommonResult.success("修改成功");
        } else {
            return CommonResult.error(INTERNAL_SERVER_ERROR,"修改失败");
        }
    }
}



