package com.xuzp.insuredxmltool.core.tool.script.warlock.statement;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Function;
import com.xuzp.insuredxmltool.core.tool.script.Script;
import com.xuzp.insuredxmltool.core.tool.script.Stack;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Code;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Interrupt;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Syntax;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Words;

public class ArithmeticFunctionDim implements Code, Function
{
	String[] param;
	Script content;
	
	public ArithmeticFunctionDim(Words ws, int i)
	{
		int l = i + 1;
		int r = Syntax.findRightBrace(ws, l + 1);

		int num = (r - l) / 2;
		param = new String[num];

		Words w = ws.cut(l + 1, r);
		for (int m = 0; m < num; m++)
		{
			param[m] = w.getWord(m * 2);
		}

		l = r + 1;
		r = Syntax.findRightBrace(ws, l + 1);
		
		content = new Script(ws.cut(l + 1, r));
	}

	public Object run(Factors factors)
	{
		return this;
	}

	public String toText(String space)
	{
		return "function dec";
	}

	public Object run(Object[] v, Factors p)
	{
		Stack stack = new Stack(p);
		
		for (int i = 0; i < param.length && i < v.length; i++)
		{
			stack.declare(param[i]);
			stack.set(param[i], v[i]);
		}
		
		Object result = content.run(stack);
		
		if (Interrupt.isMatch(result, Interrupt.RETURN))
			result = Interrupt.getValue(result);
		
		return result;
	}
}
