package com.xuzp.insuredxmltool;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.common.collect.Lists;
import com.xuzp.insuredxmltool.constants.TemplateConstant;
import com.xuzp.insuredxmltool.enums.*;
import com.xuzp.insuredxmltool.excel.model.*;
import com.xuzp.insuredxmltool.excel.parser.简单键值解析器;
import com.xuzp.insuredxmltool.excel.parser.解析器;
import com.xuzp.insuredxmltool.excel.parser.责任免除解析器;
import com.xuzp.insuredxmltool.excel.parser.责任给付解析器;
import com.xuzp.insuredxmltool.template.模板;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
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
public class MainProcessor {

    List<解析器> 一组解析器 = new ArrayList<解析器>() {
        {
            try {
                add(new 简单键值解析器(险种信息.class, 0, 1));
                add(new 简单键值解析器(投保规则.class, 1, 2));
                add(new 责任给付解析器());
                add(new 责任免除解析器());
                add(new 简单键值解析器(万能险配置.class,0,2));
                add(new 简单键值解析器(利益演示.class,0,2));
            } catch (Exception e) {
                log.error("初始化解析器失败", e);
            }
        }
    };

    public static void main(String[] args) throws Exception {
        if(args==null || args.length!=1){
            log.error("请指定需求excel路径");
            return;
        }
        MainProcessor processor = new MainProcessor();
        String content = processor.read(args[0]).constructTemplate();
        processor.generateFile(args[0], content);
//        log.info(content);
    }

    public MainProcessor read(String 文件) throws Exception {
        ExcelTypeEnum Excel版本 = 文件.endsWith(ExcelTypeEnum.XLSX.getValue()) ? ExcelTypeEnum.XLSX : ExcelTypeEnum.XLS;
        for(解析器 解析器: 一组解析器){
            try (InputStream 文件流 = new FileInputStream(文件);) {
                ExcelReader Excel读取器 = new ExcelReader(文件流, Excel版本, null, 解析器, true);
                Excel读取器.getSheets().stream().filter(Excel页签 -> Excel页签.getSheetName().equals(解析器.解析器类型()))
                        .findFirst().ifPresent(Excel页签 -> Excel读取器.read(Excel页签));
            }
        }
        return this;
    }

    public String constructTemplate(){

        险种信息 险种信息 = (险种信息)一组解析器.get(0).结果();
        投保规则 投保规则 = (投保规则)一组解析器.get(1).结果();
        责任给付 责任给付 = (责任给付)一组解析器.get(2).结果();
        责任免除 责任免除 = (责任免除)一组解析器.get(3).结果();
        利益演示 利益演示 = (利益演示)一组解析器.get(4).结果();

        String content =  模板.loadTemplate(TemplateConstant.SAMPLE_FTL, new HashMap<String, Object>(){
            {
                put("内部标识", getVal(险种信息.内部标识));
                put("公司编码", getVal(险种信息.公司编码));
                put("保险编码", getVal(险种信息.险种编码));
                put("保险名称",getVal(险种信息.险种名称));
                put("保险简称", getVal(险种信息.险种简称));
                put("计算单位", getVal(险种信息.计算单位));
                put("主附险标记", getVal(分词.matchOne(险种信息.主附险标记, MainRiderEnum.values())));
                put("保险类别", getVal(分词.matchOne(险种信息.险种页签, RiskTypeEnum.values())));
                put("保险次序", "1000");
                put("输入类目", getVal(分词.matchOne(投保规则.保费保额, InputTypeEnum.values())));
                put("交费方式列表", 分词.matchList(投保规则.交费方式!=null?投保规则.交费方式:投保规则.交费频次, PayPeriodEnum.values()));
                put("交费年期列表", 分词.matchList(投保规则.交费年期, PayEnum.values()));
                put("保险期间列表", 分词.matchList(投保规则.保险期间, InsureEnum.values()));
                put("责任给付列表", 责任给付!=null ? 责任给付.责任列表:null);
                put("限制职业", 分词.matchOne(投保规则.限制职业, Lists.newArrayList("1-4类","1~4类")));
                put("最小投保人年龄", 分词.matchOneNumber(投保规则.最小投保人年龄));
                put("最大投保人年龄", 分词.matchOneNumber(投保规则.最大投保人年龄));
                put("最小被保人年龄", 分词.matchOneNumber(投保规则.最小被保人年龄));
                put("最大被保人年龄", 分词.matchOneNumber(投保规则.最大被保人年龄));
                put("现价表结构", 利益演示!=null? 分词.matchOne(利益演示.现价表结构, DataLayoutEnum.values()): null);
                put("责任免除列表", 责任免除!=null ? 责任免除.责任免除列表: null);
                put("保单年度", 利益演示!=null ? 利益演示.保单年度:null);
                put("年龄", 利益演示!=null ? 利益演示.年龄:null);
                put("年交保险费", 利益演示!=null ? 利益演示.年交保险费:null);
                put("累计保险费", 利益演示!=null ? 利益演示.累计保险费:null);
                put("重大疾病保障", 利益演示!=null ? 利益演示.重大疾病保障:null);
                put("身故保障", 利益演示!=null ? 利益演示.身故保障:null);
                put("轻症疾病保障", 利益演示!=null ? 利益演示.轻症疾病保障:null);
                put("现金价值", 利益演示!=null ? 利益演示.现金价值:null);

            }});
        return content;
    }

    private String getVal(String val){
        return getVal(val, "等待输入");
    }

    private String getVal(String val, String defaultVal){
        if(StringUtils.isEmpty(val)) {
            return defaultVal;
        }
        return val;
    }

    private void generateFile(String excelFile, String content) throws Exception {
        险种信息 险种信息 = (险种信息)一组解析器.get(0).结果();
        String fileName = getVal(险种信息.内部标识)+".xml";
        File path = new File(excelFile).getParentFile();
        File outFile = FileUtils.getFile(path, fileName);
        FileUtils.writeStringToFile(outFile, content, TemplateConstant.ENCODING);
        log.info("生成{}成功",fileName);
    }
}
