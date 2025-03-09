package com.nbug.module.infra.dal.mysql.file;

import com.nbug.common.pojo.PageResult;
import com.nbug.depends.mybatis.plus.core.mapper.BaseMapperX;
import com.nbug.depends.mybatis.plus.core.query.LambdaQueryWrapperX;
import com.nbug.module.infra.controller.admin.file.vo.file.FilePageReqVO;
import com.nbug.module.infra.dal.dataobject.file.FileDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件操作 Mapper
 *
 * @author NBUG
 */
@Mapper
public interface FileMapper extends BaseMapperX<FileDO> {

    default PageResult<FileDO> selectPage(FilePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FileDO>()
                .likeIfPresent(FileDO::getPath, reqVO.getPath())
                .likeIfPresent(FileDO::getType, reqVO.getType())
                .betweenIfPresent(FileDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FileDO::getId));
    }

}
