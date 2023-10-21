package com.aicanvas.aicanvas.controller;

import com.aicanvas.aicanvas.utils.ExcelUtils;
import com.alibaba.excel.EasyExcel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
public class UploadController {

    @PostMapping("/uploadCSV")
    public String uploadCSV(@RequestParam("file") MultipartFile file) throws IOException {

        // 获取上传文件的原始文件名
        String originalFilename = file.getOriginalFilename();
        // 将上传的文件保存到本地
        EasyExcel.read(file.getInputStream())
                .excelType(ExcelUtils.getExcelType(file.getOriginalFilename()))
                .sheet().doRead();
        // 返回成功信息或执行其他操作
        return originalFilename + "文件上传成功";

    }

    @PostMapping("/uploadExcel")
    public String uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {

        // 获取上传文件的原始文件名
        String filename = file.getOriginalFilename();
        // 将上传的文件保存到本地
        EasyExcel.read(file.getInputStream())
                .excelType(ExcelUtils.getExcelType(file.getOriginalFilename()))
                .sheet().doRead();
        // 返回成功信息或执行其他操作
        return filename + "文件上传成功";

    }


    @PostMapping("/uploadMysql")
    public String uploadMysql(@RequestParam("file") MultipartFile file) {

        // 获取上传文件的原始文件名
        String originalFilename = file.getOriginalFilename();
        // 将上传的文件保存到本地

        // 返回成功信息或执行其他操作
        return "文件上传成功：" + originalFilename;

    }

}

