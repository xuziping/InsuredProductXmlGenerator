package com.xuzp.insuredxmltool.core.tool.formula.aries.arithmetic;

import com.xuzp.insuredxmltool.core.tool.formula.CalculateException;
import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Formula;
import com.xuzp.insuredxmltool.core.tool.formula.Value;

import java.util.List;

public class ArithmeticHas extends Arithmetic implements Formula
{
	private static final long serialVersionUID = 1L;

	public ArithmeticHas()
	{
		super.addSign("has");
		super.setPriority(75);
		super.setFuntion(false);
	}
	
	public ArithmeticHas(List paramList)
	{
		super.setParameter(paramList);
	}
	
	public Object run(Factors getter)
	{
		Value v1 = Value.valueOf(super.getParameter(0), getter);
		Value v2 = Value.valueOf(super.getParameter(1), getter);
		
		Object result = null;
		
		if (v1.getType() == Value.TYPE_LIST && v2.getType() == Value.TYPE_STRING)
		{
			List l = (List)v1.getValue();
			
			if (l == null)
				result = new Boolean(false);
			else
			{
				result = new Boolean(l.indexOf(v2.getValue()) >= 0);
			}
		}
		else
		{
			throw new CalculateException("“has”逻辑计算要求左边为list，右边为字符串。");
		}
		
		return result;
	}
}
