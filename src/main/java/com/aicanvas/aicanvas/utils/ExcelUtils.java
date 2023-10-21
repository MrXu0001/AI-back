package com.aicanvas.aicanvas.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.support.ExcelTypeEnum;

public class ExcelUtils {
    /**
     * 获取导出格式
     *
     * @param fileName 文件名，机器人.xlsx
     * @return 导出格式（缺失默认xlsx）
     */
    public static ExcelTypeEnum getExcelType(String fileName) {
        if (StrUtil.isBlank(fileName)) {
            return ExcelTypeEnum.XLSX;
        }
        if (fileName.endsWith(ExcelTypeEnum.XLS.getValue())) {
            return ExcelTypeEnum.XLS;
        } else if (fileName.endsWith(ExcelTypeEnum.CSV.getValue())) {
            return ExcelTypeEnum.CSV;
        } else {
            return ExcelTypeEnum.XLSX;
        }
    }
}
