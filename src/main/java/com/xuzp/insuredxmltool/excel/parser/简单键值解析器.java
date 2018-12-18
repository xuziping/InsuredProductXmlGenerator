package com.xuzp.insuredxmltool.excel.parser;

import com.alibaba.excel.context.AnalysisContext;
import com.xuzp.insuredxmltool.excel.model.信息;
import com.xuzp.insuredxmltool.utils.ReflectTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author za-xuzhiping
 * @Date 2018/12/13
 * @Time 10:48
 */
@Slf4j
public class 简单键值解析器<T extends 信息> extends 解析器 {

    private T 信息汇总;
    private int 字段索引;
    private int 值索引;

    public 简单键值解析器(Class<T> 信息类别, int 字段索引, int 值索引) throws Exception{
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
        log.info("爷，{}解析完成: {}", 信息汇总.getClass().getSimpleName(), 信息汇总);
    }

    @Override
    public T 结果(){
        return 信息汇总;
    }

    @Override
    public String 解析器类型(){
        return 信息汇总.getClass().getSimpleName();
    }
}