package com.xuzp.insuredxmltool;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.xuzp.insuredxmltool.excel.model.投保规则;
import com.xuzp.insuredxmltool.excel.model.险种信息;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author za-xuzhiping
 * @Date 2018/12/11
 * @Time 11:41
 */
@Slf4j
public class 主程序 {

    List<解析器> 一组解析器 = new ArrayList<解析器>() {
        {
            try {
                add(new 解析器(险种信息.class, 0, 1));
                add(new 解析器(投保规则.class, 1, 2));
            } catch (Exception e) {
                log.error("初始化解析器失败", e);
            }
        }
    };

    public static void main(String[] args) throws Exception {
        new 主程序().read("D:/myworkspace/InsuredProductXmlGenerator/src/main/resources/example.xlsx");
    }

    public void read(String file) throws Exception {
        ExcelTypeEnum Excel版本 = file.endsWith(".xlsx") ? ExcelTypeEnum.XLSX : ExcelTypeEnum.XLS;
        for(解析器 解析器: 一组解析器){
            try (InputStream 文件流 = new FileInputStream(file);) {
                ExcelReader excelReader = new ExcelReader(文件流, Excel版本, null, 解析器, true);
                excelReader.getSheets().stream().filter(x -> x.getSheetName().equals(解析器.解析器类型()))
                        .findFirst().ifPresent(x -> excelReader.read(x));
            }
        }
    }
}
