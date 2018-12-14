package com.xuzp.insuredxmltool.core.insurance.product.attachment.combo;

import com.xuzp.insuredxmltool.core.insurance.product.attachment.AttachmentParser;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.chart.ChartItem;
import com.xuzp.insuredxmltool.core.insurance.product.load.XmlNode;
import com.xuzp.insuredxmltool.core.tool.formula.FormulaUtil;

import java.io.Serializable;
import java.util.Iterator;

public class ComboChartDefParser implements AttachmentParser, Serializable
{
	private static final long serialVersionUID = 1L;

	public String getName()
	{
		return "combo_chart_def";
	}

	public Object parse(Object source, int type)
	{
		Object result = null;
		
		if (type == AttachmentParser.XML)
		{
			XmlNode node = (XmlNode)source;
			result = parseBenefitCombo(node);
		}
		else
		{
		}
		
		return result;
	}

	public static ComboChart parseBenefitCombo(XmlNode e)
	{
		ComboChart chartDef = new ComboChart();
		
		for (Iterator iter = e.getChildren().iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();

			String nodeName = n1.getName();
			if ("item".equals(nodeName))
			{
				chartDef.addCol(parseComboChartCol(n1));
			}
		}
		
		return chartDef;
	}
	
	private static ComboChartCol parseComboChartCol(XmlNode node)
	{
		String code = node.getAttribute("code");
		String mode = node.getAttribute("mode");
		String name = node.getAttribute("name");
		String add = node.getAttribute("add");
		String type = node.getAttribute("type");
		String color = node.getAttribute("color");

		int m = "add".equalsIgnoreCase(mode) ? ComboCol.MODE_ADD :
				"accumulate".equalsIgnoreCase(mode) || "acc".equalsIgnoreCase(mode) ? ComboCol.MODE_ACCUMULATE :
				"cover".equalsIgnoreCase(mode) ? ComboCol.MODE_COVER :
				ComboCol.MODE_ADD;
		
		int t = "line".equalsIgnoreCase(type) ? ChartItem.TYPE_LINE : "bar".equalsIgnoreCase(mode) ? ChartItem.TYPE_BAR : ChartItem.TYPE_LINE;
		
		ComboChartCol col = new ComboChartCol(code, name != null ? FormulaUtil.formulaOf("'" + name.replaceAll("[\\\\][n]", "\n") + "'") : FormulaUtil.formulaOf(node.getText()), m);
		col.setAddCol(add);
		col.setType(t);
		
		return col;
	}
}
