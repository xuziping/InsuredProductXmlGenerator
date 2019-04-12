package com.xuzp.insuredxmltool.core.tool.script.warlock.statement;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Value;
import com.xuzp.insuredxmltool.core.tool.script.SyntaxException;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Code;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Reference;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Expression;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Words;

import java.math.BigDecimal;

public class ArithmeticSubSub implements Code
{
	Code l, r;
	
	public ArithmeticSubSub(Words ws, int i)
	{
		if (i == 0)
			r = Expression.expressionOf(ws.cut(i + 1));
		else if (i == ws.size() - 1)
			l = Expression.expressionOf(ws.cut(0, i));
		
		if ((l == null && r == null) || (!(l instanceof Reference) && !(r instanceof Reference)))
			throw new SyntaxException("递减运算要求左右之一为引用");
	}

	public Object run(Factors factors)
	{
		if (l != null)
		{
			Value v = Value.valueOf(l, factors);
			if (v.isDecimal())
			{
				double num = v.doubleValue();
				((Reference)l).let(factors, Double.valueOf(num - 1));
				
	//			BigDecimal num = v.toDecimal();
	//			((Reference)l).let(factors, num.subtract(new BigDecimal(1)));
				return Double.valueOf(num);
			}
		}
		else
		{
			Value v = Value.valueOf(r, factors);
			if (v.isDecimal())
			{
//				Double num = Double.valueOf(v.doubleValue() - 1);
//				((Reference)r).let(factors, num);
	
				BigDecimal num = v.toDecimal().subtract(new BigDecimal(1));
				((Reference)r).let(factors, num);
				return num;
			}
		}
		
		throw new RuntimeException("只可以在数字上做递减");
	}

	public String toText(String space)
	{
		if (l != null)
			return l.toText("") + "--";
		else
			return "--" + l.toText("");
	}
}
