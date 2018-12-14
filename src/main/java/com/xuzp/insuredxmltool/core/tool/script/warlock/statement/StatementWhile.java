package com.xuzp.insuredxmltool.core.tool.script.warlock.statement;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Value;
import com.xuzp.insuredxmltool.core.tool.script.Script;
import com.xuzp.insuredxmltool.core.tool.script.Stack;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Code;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Interrupt;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Syntax;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Words;

public class StatementWhile implements Code
{
	Code c, fc;
	
	public StatementWhile(Words ws)
	{
		int left = 1;
		int right = Syntax.findRightBrace(ws, left + 1);
		c = new Script(ws.cut(left + 1, right));
		
		left = right + 1;
		int type1 = ws.getType(left);
		if (type1 == Words.BRACE)
		{
			right = Syntax.findRightBrace(ws, left + 1);
			fc = new Script(ws.cut(left + 1, right));
		}
		else
		{
			fc = new Script(ws.cut(left, ws.size()));
		}
	}

	public Object run(Factors factors)
	{
		Stack stack = new Stack(factors);
		
		while (Value.booleanOf(c, stack))
		{
			Object result = fc.run(stack);
			
			if (Interrupt.isMatch(result, Interrupt.BREAK))
				break;
			if (Interrupt.isMatch(result, Interrupt.RETURN) || Interrupt.isMatch(result, Interrupt.THROW))
				return result;
		}
		
		return null;
	}

	public String toText(String space)
	{
		StringBuffer buf = new StringBuffer("WHILE (");
		buf.append(c.toText(""));
		buf.append(")\n");
		buf.append(space + "{\n");
		buf.append(fc.toText(space + "    ") + "\n");
		buf.append(space + "}");
		
		return buf.toString();
	}
}
