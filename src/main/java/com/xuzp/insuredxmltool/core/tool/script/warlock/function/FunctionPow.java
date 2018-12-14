package com.xuzp.insuredxmltool.core.tool.script.warlock.function;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Function;
import com.xuzp.insuredxmltool.core.tool.formula.Value;

public class FunctionPow implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 2)
		{
			double v1 = Value.valueOf(v[0]).doubleValue();
			double v2 = Value.valueOf(v[1]).doubleValue();
			
			return Double.valueOf(Math.pow(v1, v2));
		}
		
		throw new RuntimeException("错误的pow运算");
	}
}
