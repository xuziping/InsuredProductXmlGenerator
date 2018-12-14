package com.xuzp.insuredxmltool.core.tool.formula.aries.arithmetic;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Formula;
import com.xuzp.insuredxmltool.core.tool.formula.Value;

import java.math.BigDecimal;
import java.util.List;

public class FunctionInt extends Arithmetic implements Formula
{
	private static final long serialVersionUID = 1L;

	public FunctionInt()
	{
		super.addSign("int");
		super.setPriority(1000);
	}
	
	public FunctionInt(List paramList)
	{
		super.setParameter(paramList);
	}
	
	public Object run(Factors getter)
	{
		Integer r = null;

		int size = paramList.size();
		if (size == 1)
		{
			Value key = Value.valueOf((Formula)paramList.get(0), getter);
			if (key.isType(Value.TYPE_STRING))
			{
				r = new Integer(new BigDecimal(key.toString()).toBigInteger().intValue());
			}
			else
			{
				r = new Integer((int)key.toDecimal().toBigInteger().intValue());
			}
		}
		
		return r;
	}
}
