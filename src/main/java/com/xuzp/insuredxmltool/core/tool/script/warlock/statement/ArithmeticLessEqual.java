package com.xuzp.insuredxmltool.core.tool.script.warlock.statement;
import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Value;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Code;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Expression;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Words;

import java.util.Date;

public class ArithmeticLessEqual implements Code
{
	Code l, r;
	
	public ArithmeticLessEqual(Words ws, int i)
	{
		l = Expression.expressionOf(ws.cut(0, i));
		r = Expression.expressionOf(ws.cut(i + 1));
	}

	public Object run(Factors factors)
	{
		Value left = Value.valueOf(l, factors);
		Value right = Value.valueOf(r, factors);
		
		if (left.isDecimal() && right.isDecimal())
		{
			return new Boolean(left.doubleValue() <= right.doubleValue());
//			return new Boolean(left.toDecimal().compareTo(right.toDecimal()) <= 0);
		}
		else if (left.isType(Value.TYPE_DATE) && right.isType(Value.TYPE_DATE))
		{
			Date d1 = (Date)left.getValue();
			Date d2 = (Date)right.getValue();
			return new Boolean(d1.before(d2) || d1.compareTo(d2) == 0);
		}
		
		throw new RuntimeException("大小比较只可以在数字上进行");
	}

	public String toText(String space)
	{
		return l.toText("") + " <= " + r.toText("");
	}
}
