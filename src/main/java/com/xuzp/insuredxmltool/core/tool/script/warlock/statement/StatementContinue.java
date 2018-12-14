package com.xuzp.insuredxmltool.core.tool.script.warlock.statement;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Code;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Interrupt;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Words;

public class StatementContinue implements Code
{
	public StatementContinue(Words ws)
	{
	}

	public Object run(Factors factors)
	{
		return Interrupt.interruptOf(Interrupt.CONTINUE);
	}

	public String toText(String space)
	{
		return "CONTINUE";
	}
}
