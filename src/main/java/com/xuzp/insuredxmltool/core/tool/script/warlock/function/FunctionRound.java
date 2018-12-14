package com.xuzp.insuredxmltool.core.tool.script.warlock.function;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Function;
import com.xuzp.insuredxmltool.core.tool.formula.Value;

import java.math.BigDecimal;

public class FunctionRound implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 1)
			return Integer.valueOf((int)Math.round(Value.valueOf(v[0]).doubleValue() + 0.0000001f));
		
		if (v.length == 2)
		{
			int scale = Value.valueOf(v[1]).intValue();
			
//			BigDecimal d = BigDecimal.valueOf(Value.doubleOf(v[0]) + 0.0000000001f);
//			BigDecimal d = Value.valueOf(v[0]).toDecimal().add(new BigDecimal(0.0000001f));
			BigDecimal d = BigDecimal.valueOf(Value.doubleOf(v[0]));
			return d.setScale(scale, BigDecimal.ROUND_HALF_UP);
		}
		
		throw new RuntimeException("错误的round运算");
	}
}
