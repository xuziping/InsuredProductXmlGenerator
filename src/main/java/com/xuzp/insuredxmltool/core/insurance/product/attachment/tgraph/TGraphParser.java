package com.xuzp.insuredxmltool.core.insurance.product.attachment.tgraph;

import com.xuzp.insuredxmltool.core.insurance.product.attachment.AttachmentParser;
import com.xuzp.insuredxmltool.core.insurance.product.load.XmlNode;
import com.xuzp.insuredxmltool.core.tool.formula.Formula;
import com.xuzp.insuredxmltool.core.tool.formula.FormulaUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TGraphParser implements AttachmentParser, Serializable
{
	private static final long serialVersionUID = 1L;

	public String getName()
	{
		return "tgraph";
	}

	public Object parse(Object source, int type)
	{
		Object result = null;
		
		if (type == TGraphParser.XML)
		{
			result = prepareXml((XmlNode)source);
		}
		else
		{
			
		}
		
		return result;
	}
	
	private Object prepareXml(XmlNode e)
	{
		List items = new ArrayList();
		
		for (Iterator iter = e.getChildren("item").iterator(); iter.hasNext(); )
		{
			XmlNode n1 = (XmlNode)iter.next();

			TGraphItemDynamic gid;

			Formula f = n1.hasAttribute("value") ? FormulaUtil.formulaOf(n1.getAttribute("value")) : null;
			
			if (n1.hasAttribute("desc"))
				gid = new TGraphItemDynamic(FormulaUtil.formulaOf(n1.getAttribute("desc")), f);
			else
				gid = new TGraphItemDynamic(n1.getText(), f);
			
			if (n1.hasAttribute("condition"))
				gid.setCondition(FormulaUtil.formulaOf(n1.getAttribute("condition")));
			
			String bold = n1.getAttribute("bold");
			gid.setBold("yes".equalsIgnoreCase(bold));
			
			items.add(gid);
		}

		return items;
	}
}
