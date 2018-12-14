package com.xuzp.insuredxmltool.core.tool.script.warlock.function;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Function;

public class FunctionTrim implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 1)
		{
			if (v[0] == null)
				return null;
			else
				return v[0].toString().trim();
		}
		
		throw new RuntimeException("错误的trim运算");
	}
}
