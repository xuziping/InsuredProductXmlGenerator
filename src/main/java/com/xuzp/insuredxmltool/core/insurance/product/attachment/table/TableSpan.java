package com.xuzp.insuredxmltool.core.insurance.product.attachment.table;


import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Formula;

import java.io.Serializable;

public class TableSpan implements Formula, Serializable
{
	private static final long serialVersionUID = 1L;

	Integer value;
	
	public TableSpan(int v)
	{
		value = new Integer(v);
	}
	
	public String toString()
	{
		return value.toString();
	}

	public Object run(Factors factors)
	{
		return value;
	}
}
