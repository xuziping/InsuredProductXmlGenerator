package com.xuzp.insuredxmltool.core.insurance.product.attachment.tgraph;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Formula;

public class TGraphItemDynamic
{
	String  text;
	Formula textF;
	
	Formula condition;
	Formula value;
	
	boolean bold = false;
	
	public TGraphItemDynamic(Formula textF, Formula value)
	{
		this.textF = textF;
		this.value = value;
	}
	
	public TGraphItemDynamic(String text, Formula value)
	{
		this.text = text;
		this.value = value;
	}
	
	public String getText(Factors f)
	{
		if (textF == null)
			return text;
		
		return (String)textF.run(f);
	}
	
	public boolean hasValue()
	{
		return value != null;
	}
	
	public Object getValue(Factors f)
	{
		if (value == null)
			return null;
		
		return value.run(f);
	}

	public Formula getCondition()
	{
		return condition;
	}

	public void setCondition(Formula condition)
	{
		this.condition = condition;
	}

	public boolean isBold()
	{
		return bold;
	}

	public void setBold(boolean bold)
	{
		this.bold = bold;
	}
}
