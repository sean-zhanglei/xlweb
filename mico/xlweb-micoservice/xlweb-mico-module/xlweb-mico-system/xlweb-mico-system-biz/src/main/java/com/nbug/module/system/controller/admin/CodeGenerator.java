package com.nbug.module.system.controller.admin;

import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.utils.date.DateUtil;
import com.nbug.mico.common.utils.genutils.GenCodePageListUtils;
import com.nbug.mico.common.utils.genutils.GenCodePageQueryUtils;
import com.nbug.module.system.service.impl.XlwebGeneratorCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 前后端代码生成器 controller

 */
@Controller
@RequestMapping("api/admin/codegen")
@Tag(name = "代码生成")
public class CodeGenerator {

    @Autowired
    private XlwebGeneratorCodeService xlwebGeneratorCodeService;

    @ResponseBody
    @Operation(summary="代码生成-新列表")
    @GetMapping("/list")
    public CommonResult<Object> listNew(@RequestParam Map<String, Object> params){
        GenCodePageListUtils pageUtil = xlwebGeneratorCodeService.queryList(new GenCodePageQueryUtils(params));
        return CommonResult.success(pageUtil);
    }

    /**
     * 生成代码 API
     */
    @GetMapping("/code")
    public void code(@RequestParam String tables, HttpServletResponse response) throws IOException {
        byte[] data = xlwebGeneratorCodeService.generatorCode(tables.split(","));

        String contentLength = "Content-Length";
        String contentType = "application/octet-stream; charset=UTF-8;";
        String contentDisposition = "Content-Disposition";
        String attachment = "attachment; filename=\"XLWEB-Java-Code-"+ DateUtil.dateToStr(new Date(), Constants.DATE_FORMAT_HHMM) +".zip\"";

        response.reset();
        response.addHeader(contentLength, data.length +"");
        response.setContentType(contentType);
        response.setHeader(contentDisposition, attachment);

        IOUtils.write(data, response.getOutputStream());
    }
}
