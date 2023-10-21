package com.aicanvas.aicanvas.service;

import org.springframework.web.multipart.MultipartFile;

public interface IEasyExcelService {
    /**
     * 3excel导入  能支持xlsx，xls，csv格式
     * 参考：https://www.yuque.com/easyexcel/doc/read
     *
     * @param file excel文件（测试数据：printExcel1接口的导出结果即可）
     */
    void importExcel(MultipartFile file);
}