package com.nbug.module.system.api.category;

import com.nbug.mico.common.model.category.Category;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.vo.CategoryTreeVo;
import com.nbug.module.system.service.CategoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class CategoryApiImpl implements CategoryApi {

    @Resource
    private CategoryService categoryService;


    @Override
    public CommonResult<List<Category>> getByIds(List<Integer> adminIdList) {
        return CommonResult.success(categoryService.getByIds(adminIdList));
    }

    @Override
    public CommonResult<List<Category>> getChildVoListByPid(Integer pid) {
        return CommonResult.success(categoryService.getChildVoListByPid(pid));
    }

    @Override
    public CommonResult<HashMap<Integer, String>> getListInId(List<Integer> cateIdList) {
        return CommonResult.success(categoryService.getListInId(cateIdList));
    }

    /**
     * 获取树形结构数据
     * @param type 分类
     * @param status 状态
     * @param name 名称
     * @return List
     */
    @Override
    public CommonResult<List<CategoryTreeVo>> getListTree(Integer type, Integer status, String name) {
        return CommonResult.success(categoryService.getListTree(type, status, name));
    }
}
