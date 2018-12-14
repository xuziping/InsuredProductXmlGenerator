package com.xuzp.insuredxmltool.core.tool.script.warlock.statement;


import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Value;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Code;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Expression;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Words;

import java.math.BigDecimal;

public class ArithmeticMod implements Code
{
	Code l, r;
	
	public ArithmeticMod(Words ws, int i)
	{
		l = Expression.expressionOf(ws.cut(0, i));
		r = Expression.expressionOf(ws.cut(i + 1));
	}

	public Object run(Factors factors)
	{
		Value left = Value.valueOf(l, factors);
		Value right = Value.valueOf(r, factors);
		Object result = null;
		if (left.isDecimal() && right.isDecimal())
		{
			BigDecimal amt = new BigDecimal(String.valueOf(left.toDecimal()));
			BigDecimal[] results = amt.divideAndRemainder(BigDecimal.valueOf(right.toDecimal().intValue()));
			result = results[1];
			return result;
		}
		
		throw new RuntimeException("只可以对数字做取余运算");
	}

	public String toText(String space)
	{
		return l.toText("") + " - " + r.toText("");
	}
}
