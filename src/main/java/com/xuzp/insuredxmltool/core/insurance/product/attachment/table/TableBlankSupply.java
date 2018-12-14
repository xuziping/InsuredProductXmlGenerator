package com.xuzp.insuredxmltool.core.insurance.product.attachment.table;

import com.xuzp.insuredxmltool.core.tool.formula.Formula;

/**
 * 
 * @author lerrain
 * @deprecated
 */
public class TableBlankSupply
{
	public static final int MODE_ADD	= 1; //相加
	public static final int MODE_ACC	= 2; //累加
	
	int mode = MODE_ADD;
	String  code;
	Formula formula;
	
	public TableBlankSupply(String code, Formula formula)
	{
		this.code = code;
		this.formula = formula;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public Formula getFormula()
	{
		return formula;
	}

	public void setFormula(Formula formula)
	{
		this.formula = formula;
	}

	public int getMode()
	{
		return mode;
	}

	public void setMode(int mode)
	{
		this.mode = mode;
	}
}
