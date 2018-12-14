package com.xuzp.insuredxmltool.core.tool.script.warlock.function;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Function;
import com.xuzp.insuredxmltool.core.tool.formula.Value;

public class FunctionStr implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 1)
		{
			if (v[0] == null)
				return null;
			else
				return v[0].toString();
		}
		else if (v.length == 2)
		{
			String r = (String)v[0];
			int r1 = Value.intOf(v[1]);
			
			return r.substring(r1);
		}
		else if (v.length == 3)
		{
			String r = (String)v[0];
			int r1 = Value.intOf(v[1]);
			int r2 = Value.intOf(v[2]);
			
			return r.substring(r1, r2);
		}
		
		throw new RuntimeException("错误的str运算");
	}
}
