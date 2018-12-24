package com.xuzp.insuredxmltool.excel.parser;

import com.alibaba.excel.context.AnalysisContext;
import com.google.common.collect.Lists;
import com.xuzp.insuredxmltool.excel.model.责任免除;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author za-xuzhiping
 * @Date 2018/12/24
 * @Time 16:34
 */
@Slf4j
public class 责任免除解析器 extends 解析器{

    private 责任免除 信息汇总;

    @Override
    public void invoke(List<String> strings, AnalysisContext analysisContext) {
        if(CollectionUtils.isNotEmpty(strings) && StringUtils.isNotBlank(strings.get(0))) {
            if(信息汇总==null){
                信息汇总 = new 责任免除();
            }
            if(信息汇总.责任免除列表 == null){
                信息汇总.责任免除列表 = Lists.newArrayList();
            }
            信息汇总.责任免除列表.add(strings.get(0));
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("{}解析完成: {}", 信息汇总.getClass().getSimpleName(), 信息汇总);
    }

    @Override
    public 责任免除 结果(){
        return 信息汇总;
    }

    @Override
    public String 解析器类型(){
        return 责任免除.class.getSimpleName();
    }
}
