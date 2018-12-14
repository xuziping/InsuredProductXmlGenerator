package com.xuzp.insuredxmltool.core.insurance.product.attachment.combo;

import com.xuzp.insuredxmltool.core.insurance.plan.filter.table.TableText;
import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Formula;
import com.xuzp.insuredxmltool.core.tool.formula.FormulaUtil;

import java.io.Serializable;

public class ComboText implements Serializable
{
	private static final long serialVersionUID = 1L;

	String  text;
	Formula textF;
	
	boolean count = true;
	boolean bold = false;

	public TableText toTableText(Factors f)
	{
		String t = textF == null ? text : textF.run(f).toString();
		
		TableText tt = new TableText();
		tt.setText(t);
		tt.setCount(count);
		tt.setBold(bold);
		
		return tt;
	}

	public void setText(String text)
	{
		this.text = text;
	}
	
	public void setText(Formula textF)
	{
		this.textF = textF;
	}

	public boolean isCount()
	{
		return count;
	}

	public void setCount(boolean count)
	{
		this.count = count;
	}

	public boolean isBold()
	{
		return bold;
	}

	public void setBold(boolean bold)
	{
		this.bold = bold;
	}
	
	public static ComboText comboTextOf(boolean count, String type, String text)
	{
		ComboText c = new ComboText();
		c.setCount(count);
		
		if ("formula".equals(type))
			c.setText(FormulaUtil.formulaOf(text));
		else
			c.setText(text);
		
		return c;
	}
}
