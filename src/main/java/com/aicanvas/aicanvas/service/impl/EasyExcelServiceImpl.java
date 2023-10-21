package com.aicanvas.aicanvas.service.impl;

import com.aicanvas.aicanvas.service.IEasyExcelService;
import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class EasyExcelServiceImpl implements IEasyExcelService {


    @Override
    public void importExcel(MultipartFile file) {
        //参考：https://www.yuque.com/easyexcel/doc/read

        // 写法1：JDK8+ ,不用额外写一个DemoDataListener
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里每次会读取3000条数据 然后返回过来 直接调用使用数据就行
        try {
            EasyExcel.read(file.getInputStream(), DemoData.class,
                            new PageReadListener<DemoData>(dataList -> {
                                for (DemoData demoData : dataList) {
                                    log.info("读取到一条数据{}", JSONUtil.toJsonStr(demoData));
                                }
                            }))
                    //这里不指定类型，会默认xlsx。xls和csv都会失效

                    .sheet().doRead();
        } catch (Exception e) {
            log.error("读取文件失败啦！,原因:{}", e.getMessage(), e);
            throw new RuntimeException("读取文件失败啦！");
        }
    }
}