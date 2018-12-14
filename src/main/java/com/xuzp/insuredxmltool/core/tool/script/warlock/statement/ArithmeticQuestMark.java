package com.xuzp.insuredxmltool.core.tool.script.warlock.statement;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Value;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Code;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Expression;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Words;

public class ArithmeticQuestMark implements Code
{
	Code l, r;
	
	public ArithmeticQuestMark(Words ws, int i)
	{
		l = Expression.expressionOf(ws.cut(0, i));
		r = Expression.expressionOf(ws.cut(i + 1));
	}

	public Object run(Factors factors)
	{
		Value v = Value.valueOf(l, factors);
		if (v.isType(Value.TYPE_BOOLEAN))
		{
			Object ro = r.run(factors);
			
			if (!(ro instanceof Code[]) || ((Code[])ro).length != 2)
				throw new RuntimeException("?:组合运算没有找到冒号");

			Code[] c = (Code[])ro;

			if (v.booleanValue())
				return c[0].run(factors);
			else
				return c[1].run(factors);
		}
		
		throw new RuntimeException("?!组合运算要求问号左侧值为boolean类型");
	}

	public String toText(String space)
	{
		return l.toText("") + " ? " + r.toText("");
	}
}
