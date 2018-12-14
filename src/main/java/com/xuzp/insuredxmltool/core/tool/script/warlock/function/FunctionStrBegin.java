package com.xuzp.insuredxmltool.core.tool.script.warlock.function;


import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Function;

public class FunctionStrBegin implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 2)
		{
			String r = (String)v[0];
			String r1 = (String)v[1];
			
			return new Boolean(r.startsWith(r1));
		}
		
		throw new RuntimeException("错误的str_begin运算");
	}
}
