package com.xuzp.insuredxmltool.core.tool.script.warlock.function;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Function;
import com.xuzp.insuredxmltool.core.tool.formula.Value;

import java.math.BigDecimal;

public class FunctionFloor implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 1)
			return Integer.valueOf((int)Math.floor(Value.valueOf(v[0]).doubleValue()));
		
		if (v.length == 2)
		{
			int scale = Value.valueOf(v[1]).intValue();
			
			BigDecimal d = Value.valueOf(v[0]).toDecimal();
			return d.setScale(scale, BigDecimal.ROUND_FLOOR);
		}
		
		throw new RuntimeException("错误的floor运算");
	}
}
