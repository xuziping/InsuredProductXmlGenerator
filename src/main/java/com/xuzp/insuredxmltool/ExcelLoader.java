package com.xuzp.insuredxmltool;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.event.AnalysisEventListener;
import com.xuzp.insuredxmltool.utils.EasyExcelFactory;
import lombok.Data;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @author za-xuzhiping
 * @Date 2018/12/11
 * @Time 11:41
 */
public class ExcelLoader {

    public static void main(String[] args) throws Exception {
        new ExcelLoader().read("D:/zy_workspace/generali-doc/产品文档/产品列表-真意.xlsx");
    }

    public void read(String file) throws Exception {
        try (InputStream in = new FileInputStream(file);) {
            AnalysisEventListener<List<String>> listener = new AnalysisEventListener<List<String>>() {

                @Override
                public void invoke(List<String> object, AnalysisContext context) {
                    System.err.println("Row:" + context.getCurrentRowNum() + " Data:" + object);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                    System.err.println("doAfterAllAnalysed...");
                }
            };
            ExcelReader excelReader = EasyExcelFactory.getExcelReader(in, null, listener);
            excelReader.read();
        }
    }

    @Data
    public static class ExcelPropertyIndexModel extends BaseRowModel {

        @ExcelProperty(value = "姓名", index = 0)
        private String name;

        @ExcelProperty(value = "年龄", index = 1)
        private String age;

        @ExcelProperty(value = "邮箱", index = 2)
        private String email;

        @ExcelProperty(value = "地址", index = 3)
        private String address;

        @ExcelProperty(value = "性别", index = 4)
        private String sax;

        @ExcelProperty(value = "高度", index = 5)
        private String heigh;

        @ExcelProperty(value = "备注", index = 6)
        private String last;

    }
}
