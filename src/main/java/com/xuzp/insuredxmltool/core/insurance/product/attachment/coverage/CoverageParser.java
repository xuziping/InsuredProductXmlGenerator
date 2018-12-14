package com.xuzp.insuredxmltool.core.insurance.product.attachment.coverage;

import com.xuzp.insuredxmltool.core.insurance.product.attachment.AttachmentParser;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.table.TableParser;
import com.xuzp.insuredxmltool.core.insurance.product.load.XmlNode;
import com.xuzp.insuredxmltool.core.tool.formula.FormulaUtil;

import java.util.Iterator;

public class CoverageParser implements AttachmentParser
{

	public String getName()
	{
		return "coverage";
	}

	public Object parse(Object source, int type)
	{
		Object result = null;
		
		if (type == AttachmentParser.XML)
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
		Coverage coverage = new Coverage();
		
		for (Iterator i = e.getChildren("paragraph").iterator(); i.hasNext(); )
		{
			XmlNode n1 = (XmlNode)i.next();

			String title = n1.getAttribute("title");
			
			CoverageParagraph cp = coverage.newParagraph(title);
			
			String condition = n1.getAttribute("condition");
			if (condition != null)
				cp.setCondition(FormulaUtil.formulaOf(condition));
			
			for (Iterator j = n1.getChildren().iterator(); j.hasNext(); )
			{
				XmlNode n2 = (XmlNode)j.next();
				
				if ("item".equals(n2.getName()))
				{
					String type = n2.getAttribute("type");
					if ("formula".equals(type))
						cp.addContent(CoverageParagraph.FORMULA, FormulaUtil.formulaOf(n2.getText()));
					else
						cp.addContent(CoverageParagraph.TEXT, n2.getText());
				}
				else if ("table".equals(n2.getName()))
				{
					cp.addContent(CoverageParagraph.TABLE, TableParser.buildTable(n2));
				}
			}
		}

		return coverage;
	}
}
