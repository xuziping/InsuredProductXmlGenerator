package com.xuzp.insuredxmltool.core.tool.script.warlock.statement;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Value;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Code;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Expression;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Words;

public class ArithmeticNot implements Code
{
	Code r;
	
	public ArithmeticNot(Words ws, int i)
	{
		r = Expression.expressionOf(ws.cut(i + 1));
	}

	public Object run(Factors factors)
	{
		Value v = Value.valueOf(r, factors);
		if (v.isBoolean() || v.isDecimal())
			return new Boolean(!v.booleanValue());
		
		throw new RuntimeException("NOT逻辑运算，要求值为boolean类型");
	}

	public String toText(String space)
	{
		return "NOT " + r.toText("");
	}
}
