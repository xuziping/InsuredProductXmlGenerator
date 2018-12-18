package com.xuzp.insuredxmltool.excel.parser;

import com.alibaba.excel.event.AnalysisEventListener;
import com.xuzp.insuredxmltool.excel.model.信息;

import java.util.List;

/**
 * @author za-xuzhiping
 * @Date 2018/12/18
 * @Time 11:44
 */


public abstract class 解析器 extends AnalysisEventListener<List<String>> {

    public abstract String 解析器类型();

    public abstract 信息 结果();
}
