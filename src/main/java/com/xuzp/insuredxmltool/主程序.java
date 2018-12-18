package com.xuzp.insuredxmltool;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.xuzp.insuredxmltool.constants.TemplateConstant;
import com.xuzp.insuredxmltool.excel.model.投保规则;
import com.xuzp.insuredxmltool.excel.model.示例1;
import com.xuzp.insuredxmltool.excel.model.险种信息;
import com.xuzp.insuredxmltool.excel.parser.示例1解析器;
import com.xuzp.insuredxmltool.excel.parser.简单键值解析器;
import com.xuzp.insuredxmltool.excel.parser.解析器;
import com.xuzp.insuredxmltool.template.模板;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
                add(new 简单键值解析器(险种信息.class, 0, 1));
                add(new 简单键值解析器(投保规则.class, 1, 2));
                add(new 示例1解析器());
            } catch (Exception e) {
                log.error("初始化解析器失败", e);
            }
        }
    };

    public static void main(String[] args) throws Exception {
        new 主程序().read("D:/myworkspace/InsuredProductXmlGenerator/src/main/resources/example.xlsx");
    }

    public void read(String 文件) throws Exception {
        ExcelTypeEnum Excel版本 = 文件.endsWith(ExcelTypeEnum.XLSX.getValue()) ? ExcelTypeEnum.XLSX : ExcelTypeEnum.XLS;
        for(解析器 解析器: 一组解析器){
            try (InputStream 文件流 = new FileInputStream(文件);) {
                ExcelReader Excel读取器 = new ExcelReader(文件流, Excel版本, null, 解析器, true);
                Excel读取器.getSheets().stream().filter(Excel页签 -> Excel页签.getSheetName().equals(解析器.解析器类型()))
                        .findFirst().ifPresent(Excel页签 -> Excel读取器.read(Excel页签));
            }
        }
    }

    public void constructTemplateParams(){

        险种信息 险种信息 = (险种信息)一组解析器.get(0).结果();
        投保规则 投保规则 = (投保规则)一组解析器.get(1).结果();
        示例1 示例 = (示例1)一组解析器.get(2).结果();

        String content =  模板.loadTemplate(TemplateConstant.SAMPLE_FTL, new HashMap<String, Object>(){
            {
                put("内部标识", "等待输入");
                put("公司编码", "等待输入");
                put("保险编码", 险种信息.险种编码);
                put("保险名称",险种信息.险种名称);
                put("保险简称", 险种信息.险种简称);
                put("计算单位", "等待输入");
                put("保险类别", 险种信息.产品类别);
                put("保险次序", "1000");
                put("输入类目", "AMOUNT");
                put("交费方式列表", new ArrayList<String>(){
                    {
                        add("single");
                        add("year");
                    }
                });
                put("交费年期列表", new ArrayList<String>(){
                    {
                        add("single");
                        add("term_3");
                        add("term_5");
                        add("term_10");
                        add("term_20");
                        add("term_30");
                    }
                });
                put("保险期间列表", new ArrayList<String>(){
                    {
                        add("to_full");
                    }
                });
            }
        });
    }
}
