package com.xuzp.insuredxmltool.core.insurance.plan.filter;

import com.xuzp.insuredxmltool.core.insurance.product.attachment.document.DynamicText;
import com.xuzp.insuredxmltool.core.tool.formula.Factors;

import java.io.Serializable;
import java.util.Map;

public class StaticText implements FormatStyle, Serializable
{
	private static final long serialVersionUID = 1L;

	String text;
	
	Map style;
	
	boolean bold;
	
	public static StaticText textOf(DynamicText dText, Factors f)
	{
		StaticText st = new StaticText();
		st.setText(dText.getText(f));
		st.setStyle(dText.getStyle());
		st.setBold(dText.isBold());
		
		return st;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public Map getStyle()
	{
		return style;
	}

	public void setStyle(Map style)
	{
		this.style = style;
	}

	@Override
	public String getStyle(String name)
	{
		return style == null ? null : (String)style.get(name);
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
