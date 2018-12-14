package com.xuzp.insuredxmltool.core.tool.script.warlock.statement;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Code;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Expression;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Words;

public class ArithmeticColon implements Code
{
	Code[] v;
	
	public ArithmeticColon(Words ws, int i)
	{
		v = new Code[2];
		v[0] = Expression.expressionOf(ws.cut(0, i));
		v[1] = Expression.expressionOf(ws.cut(i + 1));
	}

	public Object run(Factors factors)
	{
		return v;
	}
	
	public String toText(String space)
	{
		return v[0].toText("") + " : " + v[1].toText("");
	}
}
