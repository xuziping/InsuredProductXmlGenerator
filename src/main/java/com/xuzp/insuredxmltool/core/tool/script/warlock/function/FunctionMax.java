package com.xuzp.insuredxmltool.core.tool.script.warlock.function;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Function;
import com.xuzp.insuredxmltool.core.tool.formula.Value;

public class FunctionMax implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length > 0)
		{
			double max = Value.valueOf(v[0]).doubleValue();
			
			for (int i = 1; i < v.length; i++)
			{
				double d = Value.valueOf(v[i]).doubleValue();
				if (max < d)
					max = d;
			}
			
			return Double.valueOf(max);
		}
		
		throw new RuntimeException("错误的max运算");
	}
}
