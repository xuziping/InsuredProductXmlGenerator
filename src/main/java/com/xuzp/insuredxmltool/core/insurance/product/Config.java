package com.xuzp.insuredxmltool.core.insurance.product;

import com.xuzp.insuredxmltool.core.insurance.plan.filter.CoverageFilter;
import com.xuzp.insuredxmltool.core.insurance.plan.filter.DocumentFilter;
import com.xuzp.insuredxmltool.core.insurance.plan.filter.axachart.AxaChartFilter;
import com.xuzp.insuredxmltool.core.insurance.plan.filter.chart.ChartFilter;
import com.xuzp.insuredxmltool.core.insurance.plan.filter.liability.LiabilityFilter;
import com.xuzp.insuredxmltool.core.insurance.plan.filter.table.ComboChartFilter;
import com.xuzp.insuredxmltool.core.insurance.plan.filter.table.ComboFilter;
import com.xuzp.insuredxmltool.core.insurance.plan.filter.table.TableFilter;
import com.xuzp.insuredxmltool.core.insurance.plan.filter.tgraph.TGraphFilter;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.AttachmentParser;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.axachart.AxaChartParser;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.chart.ChartParser;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.combo.ComboChartDefParser;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.combo.ComboDefParser;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.combo.ComboParser;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.coverage.CoverageParser;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.document.DocumentParser;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.liability.LiabilityParser;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.table.TableParser;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.tgraph.TGraphParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lerrain on 2017/11/9.
 */
public class Config
{
    static Map filterMap = new HashMap();
    static Map attachmentParsers = new HashMap();

    static
    {
        filterMap.put("coverage", new CoverageFilter());
        filterMap.put("liability", new LiabilityFilter());
        filterMap.put("table", new TableFilter());
        filterMap.put("combo", new ComboFilter());
        filterMap.put("combo_chart", new ComboChartFilter());
        filterMap.put("chart", new ChartFilter());
        filterMap.put("chart@axa", new AxaChartFilter());
        filterMap.put("tgraph", new TGraphFilter());
        filterMap.put("document", new DocumentFilter());

        attachmentParsers.put("table", new TableParser());
        attachmentParsers.put("coverage", new CoverageParser());
        attachmentParsers.put("liability", new LiabilityParser());
        attachmentParsers.put("combo", new ComboParser());
        attachmentParsers.put("combo_def", new ComboDefParser());
        attachmentParsers.put("combo_chart_def", new ComboChartDefParser());
        attachmentParsers.put("chart", new ChartParser());
        attachmentParsers.put("chart@axa", new AxaChartParser());
        attachmentParsers.put("tgraph", new TGraphParser());
        attachmentParsers.put("document", new DocumentParser());
    }

    public static void addFilter(String name, Object filter)
    {
        filterMap.put(name, filter);
    }

    public static void addParser(String name, AttachmentParser filter)
    {
        attachmentParsers.put(name, filter);
    }

    public static Map getFilterMap()
    {
        return filterMap;
    }

    public static Map getParserMap()
    {
        return attachmentParsers;
    }
}
