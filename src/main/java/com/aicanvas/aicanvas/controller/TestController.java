package com.aicanvas.aicanvas.controller;

import com.aicanvas.aicanvas.VO.LoginForm;
import com.aicanvas.aicanvas.utils.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/test")
public class TestController {

    @CrossOrigin
    @PostMapping("/upload")
    public @ResponseBody String uploadCSV(@RequestParam("file") MultipartFile file) {
        System.out.println(1111);
        System.out.println(file);
        if (!file.isEmpty()) {
            try {
                // 获取上传文件的原始文件名
                String originalFilename = file.getOriginalFilename();
                // 定义本地存储路径，根据实际情况修改
                String filePath = "D:/works/" + originalFilename;
                // 创建本地文件
                File localFile = new File(filePath);
                // 将上传的文件保存到本地
                file.transferTo(localFile);
                // 返回成功信息或执行其他操作
                return "文件上传成功：" + originalFilename;
            } catch (IOException e) {
                // 处理异常情况
                return "文件上传失败: " + e.getMessage();
            }
        } else {
            return "请选择一个CSV文件";
        }
    }
    @PostMapping("/getMessage")
    @CrossOrigin
    public Object getMessage(@RequestBody String message){
        System.out.println(2222);
        System.out.println(message);
        return "I get it";
    }
}
