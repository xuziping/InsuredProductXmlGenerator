package com.xuzp.insuredxmltool.excel.parser;

import com.alibaba.excel.context.AnalysisContext;
import com.google.common.collect.Lists;
import com.xuzp.insuredxmltool.excel.model.示例1;
import com.xuzp.insuredxmltool.excel.model.责任信息;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author za-xuzhiping
 * @Date 2018/12/18
 * @Time 11:40
 */
@Slf4j
public class 示例1解析器 extends 解析器{

    private 示例1 信息汇总;

    public 示例1解析器() throws Exception{
        信息汇总 = new 示例1();
        信息汇总.责任列表 = Lists.newArrayList();
    }

    @Override
    public void invoke(List<String> lineData, AnalysisContext context) {

        if(isHeader(lineData)){
            责任信息 上个责任 = 信息汇总.责任列表.size()>0 ? 信息汇总.责任列表.get(信息汇总.责任列表.size()-1):null;
            if(上个责任!=null && 上个责任.给付原因标准和限额.isEmpty()){
                信息汇总.责任列表.remove(上个责任);
            }

            责任信息 责任 = new 责任信息();
            责任.保障类别名称 = lineData.get(0);
            责任.给付原因标准和限额 = new LinkedHashMap<String, String>();
            信息汇总.责任列表.add(责任);
        } else if (isContent(lineData)) {
            信息汇总.责任列表.get(信息汇总.责任列表.size()-1).给付原因标准和限额.put(lineData.get(0).trim().replaceAll("\n"," "), lineData.get(1).trim().replaceAll("\n"," "));
        }
    }

    private boolean isHeader(List<String> lines){
        if(CollectionUtils.isEmpty(lines)) {
            return false;
        }
        if (StringUtils.isNotEmpty(lines.get(0)) &&
                (lines.size() == 1 ||
                !lines.subList(1, lines.size()-1).parallelStream().filter(StringUtils::isNoneBlank).findFirst().isPresent())){
            return true;
        }
        return false;
    }

    private boolean isContent(List<String> lines){
        if(CollectionUtils.isEmpty(lines)) {
            return false;
        }
        if (lines.size() > 1 && StringUtils.isNotEmpty(lines.get(0)) && StringUtils.isNotEmpty(lines.get(1))
               &&
                (lines.size() == 2 ||
                        !lines.subList(2, lines.size()-1).parallelStream().filter(StringUtils::isNoneBlank).findFirst().isPresent())){
            return true;
        }
        return false;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("爷，示例1解析完成: {}", 信息汇总);
    }

    @Override
    public 示例1 结果(){
        return 信息汇总;
    }

    @Override
    public String 解析器类型(){
        return 示例1.class.getSimpleName();
    }
}
