package com.nbug.module.infra.dal.mysql.db;

import com.nbug.depends.mybatis.plus.core.mapper.BaseMapperX;
import com.nbug.module.infra.dal.dataobject.db.DataSourceConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源配置 Mapper
 *
 * @author NBUG
 */
@Mapper
public interface DataSourceConfigMapper extends BaseMapperX<DataSourceConfigDO> {
}
