package com.xuzp.insuredxmltool.core.tool.script.warlock.statement;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Code;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Interrupt;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Expression;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Words;

public class StatementThrow implements Code
{
	Code r;
	
	public StatementThrow(Words ws)
	{
		r = Expression.expressionOf(ws.cut(1));
	}

	public Object run(Factors factors)
	{
		return Interrupt.interruptOf(Interrupt.THROW, r.run(factors));
	}

	public String toText(String space)
	{
		return "THROW " + r.toText("");
	}
}
