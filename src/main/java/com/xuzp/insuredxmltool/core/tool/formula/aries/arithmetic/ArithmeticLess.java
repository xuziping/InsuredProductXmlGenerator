package com.xuzp.insuredxmltool.core.tool.formula.aries.arithmetic;

import com.xuzp.insuredxmltool.core.tool.formula.CalculateException;
import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Formula;
import com.xuzp.insuredxmltool.core.tool.formula.Value;

import java.util.List;

public class ArithmeticLess extends Arithmetic implements Formula
{
	private static final long serialVersionUID = 1L;

	public ArithmeticLess()
	{
		super.addSign("<");
		super.addSign("lt");
		super.setPriority(50);
		super.setFuntion(false);
	}
	
	public ArithmeticLess(List paramList)
	{
		super.setParameter(paramList);
	}
	
	public Object run(Factors getter)
	{
		Value v1 = Value.valueOf(super.getParameter(0), getter);
		Value v2 = Value.valueOf(super.getParameter(1), getter);
		
		Object result = null;
		
		if (v1.isDecimal() && v2.isDecimal())
		{
			result = new Boolean(v1.toDecimal().compareTo(v2.toDecimal()) == -1);
		}
		else
		{
			throw new CalculateException("“小于”逻辑计算要求两方都是数字类型。 v1 = " + v1.getValue() + "，v2 = " + v2.getValue());
		}
		
		return result;
	}
}
