package com.xuzp.insuredxmltool.core.tool.script.warlock.statement;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Value;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Code;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Reference;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Expression;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Words;

import java.math.BigDecimal;

public class ArithmeticSubLet implements Code
{
	Code l, r;
	
	public ArithmeticSubLet(Words ws, int i)
	{
		l = Expression.expressionOf(ws.cut(0, i));
		r = Expression.expressionOf(ws.cut(i + 1));
	}

	public Object run(Factors factors)
	{
		Value left = Value.valueOf(l, factors);
		Value right = Value.valueOf(r, factors);
		
		if (left.isDecimal() && right.isDecimal())
		{
//			Double v = Double.valueOf(left.doubleValue() - right.doubleValue());
			BigDecimal v = left.toDecimal().subtract(right.toDecimal());
			((Reference)l).let(factors, v);

			return v;
		}
		
		throw new RuntimeException("只可以在数字上做递减赋值运算");
	}

	public String toText(String space)
	{
		return l.toText("") + " -= " + r.toText("");
	}
}
