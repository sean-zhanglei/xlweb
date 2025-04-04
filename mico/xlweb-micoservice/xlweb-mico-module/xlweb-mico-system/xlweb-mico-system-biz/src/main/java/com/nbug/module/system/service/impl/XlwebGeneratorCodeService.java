package com.nbug.module.system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.nbug.mico.common.utils.genutils.GenCodePageListUtils;
import com.nbug.mico.common.utils.genutils.GenCodePageQueryUtils;
import com.nbug.mico.common.utils.genutils.GenCodeUtils;
import com.nbug.module.system.dal.MySqlGenDao;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成也业务类

 */
@Service
public class XlwebGeneratorCodeService {

    @Autowired
    private MySqlGenDao mySqlGenDao;

    /**
     * 查询数据库对应表
     * @param pageQueryUtils
     * @return
     */
    public GenCodePageListUtils queryList(GenCodePageQueryUtils pageQueryUtils) {
        Page<?> page = PageHelper.startPage(pageQueryUtils.getPage(), pageQueryUtils.getLimit());
        List<Map<String, Object>> list = mySqlGenDao.getList(pageQueryUtils);
        int total = (int) page.getTotal();
        return new GenCodePageListUtils(list, total, pageQueryUtils.getLimit(), pageQueryUtils.getPage());
    }

    public Map<String, String> queryTable(String tableName) {
        return mySqlGenDao.getTable(tableName);
    }

    public List<Map<String, String>> queryColumns(String tableName) {
        return mySqlGenDao.getColumns(tableName);
    }


    /**
     * 参训数据库表对应的列数据
     * @param tableNames 数据库表名称
     * @return byte 数组
     */
    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames) {
            //查询表信息
            Map<String, String> table = queryTable(tableName);
            //查询列信息
            List<Map<String, String>> columns = queryColumns(tableName);
            //生成代码
            GenCodeUtils.generatorCode(table, columns, zip);
        }

        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
}
