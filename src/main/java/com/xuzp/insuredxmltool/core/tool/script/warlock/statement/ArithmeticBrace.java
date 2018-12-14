package com.xuzp.insuredxmltool.core.tool.script.warlock.statement;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.script.SyntaxException;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Code;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Wrap;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Expression;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Words;

import java.util.LinkedHashMap;
import java.util.Map;

public class ArithmeticBrace implements Code
{
	String type = null;
	
	Code v, a;
	
//	ArithmeticArray pv;
	
	public ArithmeticBrace(Words ws, int i)
	{
		if (ws.getType(i) != Words.BRACE || ws.getType(ws.size() - 1) != Words.BRACE_R)
			throw new SyntaxException("找不到数组的右括号");
		
		if (i > 0) //否则为Object数组定义 [a, b, c, ...]
		{
			if (i == 1 && "double".equals(ws.getWord(0))) // double[a, b, c, ...]
				type = "double";
			else
				v = Expression.expressionOf(ws.cut(0, i));
		}
		
		a = Expression.expressionOf(ws.cut(i + 1, ws.size() - 1));
	}

	public Object run(Factors factors)
	{
		Object r = a.run(factors);
		
		Map res = new LinkedHashMap();
		
		if (r instanceof Wrap)
		{
			for (Object val : ((Wrap)r).toList())
			{
				Code[] v = (Code[])val;
				res.put(v[0].toString(), v[1].run(factors));
			}
		}
		else if (r instanceof Code[])
		{
			Code[] v = (Code[])r;
			res.put(v[0].toString(), v[1].run(factors));
		}
		
		return res;
	}

	public String toText(String space)
	{
		return (v == null ? "OBJECT" : v.toText("")) + "{" + a.toText("") + "}";
	}
}
