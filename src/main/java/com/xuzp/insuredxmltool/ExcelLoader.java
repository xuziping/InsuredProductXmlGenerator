package com.xuzp.insuredxmltool;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.xuzp.insuredxmltool.excel.model.信息;
import com.xuzp.insuredxmltool.excel.model.险种信息;
import com.xuzp.insuredxmltool.utils.ReflectTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @author za-xuzhiping
 * @Date 2018/12/11
 * @Time 11:41
 */
@Slf4j
public class ExcelLoader {


    public static void main(String[] args) throws Exception {
        new ExcelLoader().read("D:/myworkspace/InsuredProductXmlGenerator/src/main/resources/example.xlsx");
    }

    public void read(String file) throws Exception {
        try (InputStream in = new FileInputStream(file);) {

            解析器 险种信息解析器 = new 解析器(险种信息.class, 0, 1);
            ExcelTypeEnum excelTypeEnum = file.endsWith(".xlsx") ? ExcelTypeEnum.XLSX: ExcelTypeEnum.XLS;
            ExcelReader excelReader = new ExcelReader(in, excelTypeEnum, null, 险种信息解析器, true);
            excelReader.getSheets().stream().filter(x->x.getSheetName().equals(险种信息.class.getSimpleName()))
                    .findFirst().ifPresent(x->excelReader.read(x));
            log.info("{}", 险种信息解析器.结果());
        }
    }

    class 解析器 <T extends 信息> extends AnalysisEventListener<List<String>> {

        private T 信息汇总;
        private int 字段索引;
        private int 值索引;

        public 解析器(Class<T> 信息类别, int 字段索引, int 值索引) throws Exception{
            this.信息汇总 = 信息类别.newInstance();
            this.字段索引 = 字段索引;
            this.值索引 = 值索引;
        }

        @Override
        public void invoke(List<String> lineData, AnalysisContext context) {
            String 字段 = lineData.get(字段索引);
            String 值 = lineData.get(值索引);
            if(StringUtils.isNotEmpty(字段) && StringUtils.isNotEmpty(值)) {
                ReflectTool.setAttribute(信息汇总, 字段, 值);
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            System.err.println("爷，解析完成");
        }

        public T 结果(){
            return 信息汇总;
        }
    };
}
