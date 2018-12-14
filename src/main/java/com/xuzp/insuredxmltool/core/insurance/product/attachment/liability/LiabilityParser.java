package com.xuzp.insuredxmltool.core.insurance.product.attachment.liability;

import com.xuzp.insuredxmltool.core.insurance.product.attachment.AttachmentParser;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.table.TableParser;
import com.xuzp.insuredxmltool.core.insurance.product.load.XmlNode;
import com.xuzp.insuredxmltool.core.tool.formula.FormulaUtil;

import java.util.Iterator;

public class LiabilityParser implements AttachmentParser
{

	public String getName()
	{
		return "liability";
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
		LiabilityDef coverage = new LiabilityDef();
		coverage.setType(LiabilityDef.TYPE_GROUP);
		
		for (Iterator i = e.getChildren("paragraph").iterator(); i.hasNext(); )
		{
			XmlNode n1 = (XmlNode)i.next();

			String title = n1.getAttribute("title");
			
			LiabilityDef cp = new LiabilityDef();
			cp.setType(LiabilityDef.TYPE_GROUP);
			cp.setTitle(title);
			
			String condition = n1.getAttributeInOrder("condition,c");
			if (condition != null)
				cp.setCondition(FormulaUtil.formulaOf(condition));
			
			for (Iterator j = n1.getChildren().iterator(); j.hasNext(); )
			{
				XmlNode n2 = (XmlNode)j.next();
				
				if ("item".equals(n2.getName()))
				{
					LiabilityDef item = new LiabilityDef();
					item.setType(LiabilityDef.TYPE_TEXT);

					String cstr = n2.getAttributeInOrder("condition,c");
					if (cstr != null)
						item.setCondition(FormulaUtil.formulaOf(cstr));
					
					String text = n2.getText();
					text = text.replaceAll("[\\\\][n]", "\n");

					String type = n2.getAttribute("type");
					if ("formula".equals(type))
						item.setContent(FormulaUtil.formulaOf(text));
					else
						item.setContent(text);
					
					cp.addParagraph(item);
				}
				else if ("table".equals(n2.getName()))
				{
					LiabilityDef item = new LiabilityDef();
					item.setType(LiabilityDef.TYPE_TABLE);
					item.setContent(TableParser.buildTable(n2));

					String cstr = n2.getAttributeInOrder("condition,c");
					if (cstr != null)
						item.setCondition(FormulaUtil.formulaOf(cstr));

					cp.addParagraph(item);
				}
			}

			coverage.addParagraph(cp);
		}

		return coverage;
	}
}
