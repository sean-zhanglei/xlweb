package com.nbug.module.infra.dal.mysql.file;

import com.nbug.common.pojo.PageResult;
import com.nbug.depends.mybatis.plus.core.mapper.BaseMapperX;
import com.nbug.depends.mybatis.plus.core.query.LambdaQueryWrapperX;
import com.nbug.module.infra.controller.admin.file.vo.config.FileConfigPageReqVO;
import com.nbug.module.infra.dal.dataobject.file.FileConfigDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileConfigMapper extends BaseMapperX<FileConfigDO> {

    default PageResult<FileConfigDO> selectPage(FileConfigPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FileConfigDO>()
                .likeIfPresent(FileConfigDO::getName, reqVO.getName())
                .eqIfPresent(FileConfigDO::getStorage, reqVO.getStorage())
                .betweenIfPresent(FileConfigDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FileConfigDO::getId));
    }

    default FileConfigDO selectByMaster() {
        return selectOne(FileConfigDO::getMaster, true);
    }

}
