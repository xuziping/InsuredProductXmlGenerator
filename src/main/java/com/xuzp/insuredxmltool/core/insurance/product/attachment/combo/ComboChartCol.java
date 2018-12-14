package com.xuzp.insuredxmltool.core.insurance.product.attachment.combo;


import com.xuzp.insuredxmltool.core.tool.formula.Formula;

import java.io.Serializable;

public class ComboChartCol implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	String  code;
	Formula name;
	
	String addCol;
	
	int mode;
	int type;
	
	public ComboChartCol(String code, Formula name, int mode)
	{
		this.code = code;
		this.name = name;
		this.mode = mode;
	}
	
	public int getMode()
	{
		return mode;
	}
	
	public String getCode()
	{
		return code;
	}
	
	public Formula getName()
	{
		return name;
	}
	
	public boolean equals(Object v)
	{
		ComboChartCol col = (ComboChartCol)v;
		if (code == null)
			return super.equals(col);
		
		return col.getCode().equals(code);
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getAddCol()
	{
		return addCol;
	}

	public void setAddCol(String addCol)
	{
		this.addCol = addCol;
	}
}
