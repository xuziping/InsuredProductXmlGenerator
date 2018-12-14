package com.xuzp.insuredxmltool.core.insurance.product.attachment.chart;

import com.xuzp.insuredxmltool.core.insurance.product.attachment.AttachmentParser;
import com.xuzp.insuredxmltool.core.insurance.product.load.XmlNode;
import com.xuzp.insuredxmltool.core.tool.formula.FormulaUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChartParser implements AttachmentParser, Serializable
{
	private static final long serialVersionUID = 1L;

	public String getName()
	{
		return "chart";
	}

	public Object parse(Object source, int type)
	{
		Object result = null;
		
		if (type == AttachmentParser.XML)
			result = prepareXml((XmlNode)source);
		
		return result;
	}
	
	private Object prepareXml(XmlNode e)
	{
		List chartList = new ArrayList();
		
		for (Iterator i = e.getChildren("chart").iterator(); i.hasNext(); )
		{
			XmlNode n1 = (XmlNode)i.next();
			chartList.add(buildChart(n1));
		}

		return chartList;
	}
	
	public static ChartDef buildChart(XmlNode e)
	{
		String from = e.getAttribute("from");
		String to = e.getAttribute("to");
		String var = e.getAttribute("name");
		
		ChartDef chart = new ChartDef(FormulaUtil.formulaOf(from), FormulaUtil.formulaOf(to));
		chart.setVarName(var);
		
		for (Iterator i = e.getChildren().iterator(); i.hasNext(); )
		{
			XmlNode n1 = (XmlNode)i.next();
			if ("item".equals(n1.getName()))
			{
				String type = n1.getAttribute("type");
				ChartItem item = new ChartItem("bar".equalsIgnoreCase(type) ? ChartItem.TYPE_BAR : ChartItem.TYPE_LINE, FormulaUtil.formulaOf(n1.getText()));
				item.setName(n1.getAttribute("name"));
				item.setColor(n1.getAttribute("color"));
				chart.addItem(item);
			}		
			else if ("bar".equals(n1.getName()))
			{
				ChartItem item = new ChartItem(ChartItem.TYPE_BAR, FormulaUtil.formulaOf(n1.getText()));
				item.setName(n1.getAttribute("name"));
				item.setColor(n1.getAttribute("color"));
				chart.addItem(item);
			}
			else if ("line".equals(n1.getName()))
			{
				ChartItem item = new ChartItem(ChartItem.TYPE_LINE, FormulaUtil.formulaOf(n1.getText()));
				item.setName(n1.getAttribute("name"));
				item.setColor(n1.getAttribute("color"));
				chart.addItem(item);
			}
		}
		
		return chart;
	}
}
