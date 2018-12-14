package com.xuzp.insuredxmltool.core.tool.script.warlock.statement;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Value;
import com.xuzp.insuredxmltool.core.tool.script.Script;
import com.xuzp.insuredxmltool.core.tool.script.Stack;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Code;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Interrupt;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Reference;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Syntax;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Words;

import java.util.Collection;
import java.util.Map;

public class StatementFor implements Code
{
	int type = 1;
	
	Code f1, f2, f3, fc;
	
	public StatementFor(Words ws)
	{
		int left = 1;
		int right = Syntax.findRightBrace(ws, left + 1);
		Script c = new Script(ws.cut(left + 1, right));
		
		if (c.size() == 3)
		{
			f1 = c.getSentence(0);
			f2 = c.getSentence(1);
			f3 = c.getSentence(2);
		}
		else if (c.size() == 1)
		{
			f1 = c.getSentence(0);
			type = 2;
		}
		
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
		
		if (type == 2)
		{
			Code[] cc = (Code[])f1.run(factors);
			Object value = cc[1].run(factors);
			Reference ref = (Reference)cc[0];
			
			if (value instanceof Object[])
			{
				for (Object v : (Object[])value)
				{
					ref.let(stack, v);
					
					Object result = fc.run(stack);
					
					if (Interrupt.isMatch(result, Interrupt.BREAK))
						break;
					if (Interrupt.isMatch(result, Interrupt.RETURN) || Interrupt.isMatch(result, Interrupt.THROW))
						return result;
				}
			}
			else if (value instanceof Collection)
			{
				for (Object v : (Collection<?>)value)
				{
					ref.let(stack, v);
					
					Object result = fc.run(stack);
					
					if (Interrupt.isMatch(result, Interrupt.BREAK))
						break;
					if (Interrupt.isMatch(result, Interrupt.RETURN) || Interrupt.isMatch(result, Interrupt.THROW))
						return result;
				}
			}
			else if (value instanceof Map)
			{
				for (Object v : ((Map<?, ?>)value).values())
				{
					ref.let(stack, v);
					
					Object result = fc.run(stack);
					
					if (Interrupt.isMatch(result, Interrupt.BREAK))
						break;
					if (Interrupt.isMatch(result, Interrupt.RETURN) || Interrupt.isMatch(result, Interrupt.THROW))
						return result;
				}
			}
		}
		else
		{
			if (f1 != null)
				f1.run(stack);
			
			while (Value.booleanOf(f2, stack))
			{
				Object result = fc.run(stack);
				
				if (Interrupt.isMatch(result, Interrupt.BREAK))
					break;
				if (Interrupt.isMatch(result, Interrupt.RETURN) || Interrupt.isMatch(result, Interrupt.THROW))
					return result;
				
				f3.run(stack);
			}
		}
		
		return null;
	}

	public String toText(String space)
	{
		StringBuffer buf = new StringBuffer("FOR (");
		if (type == 2)
			buf.append(f1.toText(""));
		else
			buf.append((f1 == null ? "" : f1.toText("")) + "; " + (f2 == null ? "" : f2.toText("")) + "; " + (f3 == null ? "" : f3.toText("")));
		buf.append(")\n");
		buf.append(space + "{\n");
		buf.append(fc.toText(space + "    ") + "\n");
		buf.append(space + "}");
		
		return buf.toString();
	}
}
