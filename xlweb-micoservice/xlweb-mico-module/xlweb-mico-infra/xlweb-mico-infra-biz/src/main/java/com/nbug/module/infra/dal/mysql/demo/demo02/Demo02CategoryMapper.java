package com.nbug.module.infra.dal.mysql.demo.demo02;

import com.nbug.depends.mybatis.plus.core.mapper.BaseMapperX;
import com.nbug.depends.mybatis.plus.core.query.LambdaQueryWrapperX;
import com.nbug.module.infra.controller.admin.demo.demo02.vo.Demo02CategoryListReqVO;
import com.nbug.module.infra.dal.dataobject.demo.demo02.Demo02CategoryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 示例分类 Mapper
 *
 * @author NBUG
 */
@Mapper
public interface Demo02CategoryMapper extends BaseMapperX<Demo02CategoryDO> {

    default List<Demo02CategoryDO> selectList(Demo02CategoryListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<Demo02CategoryDO>()
                .likeIfPresent(Demo02CategoryDO::getName, reqVO.getName())
                .eqIfPresent(Demo02CategoryDO::getParentId, reqVO.getParentId())
                .betweenIfPresent(Demo02CategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(Demo02CategoryDO::getId));
    }

	default Demo02CategoryDO selectByParentIdAndName(Long parentId, String name) {
	    return selectOne(Demo02CategoryDO::getParentId, parentId, Demo02CategoryDO::getName, name);
	}

    default Long selectCountByParentId(Long parentId) {
        return selectCount(Demo02CategoryDO::getParentId, parentId);
    }

}