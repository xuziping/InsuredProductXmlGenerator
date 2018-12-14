package com.xuzp.insuredxmltool.core.tool.script.warlock.function;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Function;
import com.xuzp.insuredxmltool.core.tool.formula.Value;

import java.util.Random;

public class FunctionRandom implements Function
{
	static Random random = new Random();
	
	public Object run(Object[] v, Factors factors)
	{
		if (v == null || v.length == 0)
			return random.nextInt();
		
		if (v.length == 1)
			return random.nextInt(Value.intOf(v[0]));
		
		throw new RuntimeException("错误的random运算");
	}
}
