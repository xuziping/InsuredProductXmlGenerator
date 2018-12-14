package com.xuzp.insuredxmltool.core.tool.script.warlock.statement;


import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.script.SyntaxException;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Code;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Reference;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Expression;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Words;

public class ArithmeticLet implements Code
{
	Code l, r;
	
	public ArithmeticLet(Words ws, int i)
	{
		l = Expression.expressionOf(ws.cut(0, i));
		r = Expression.expressionOf(ws.cut(i + 1));
		
		if (!(l instanceof Reference))
			throw new SyntaxException("被赋值一方必须是一个引用 - " + ws);
	}

	public Object run(Factors factors)
	{
		Object v = r.run(factors);
		((Reference)l).let(factors, v);
		
		return v;
	}

	public String toText(String space)
	{
		return l.toText("") + " = " + r.toText("");
	}
}
