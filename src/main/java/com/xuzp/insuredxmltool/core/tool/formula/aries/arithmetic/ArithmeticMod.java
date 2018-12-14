package com.xuzp.insuredxmltool.core.tool.formula.aries.arithmetic;

import com.xuzp.insuredxmltool.core.tool.formula.CalculateException;
import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Formula;
import com.xuzp.insuredxmltool.core.tool.formula.Value;

import java.util.List;

public class ArithmeticMod extends Arithmetic implements Formula
{
	private static final long serialVersionUID = 1L;

	public ArithmeticMod()
	{
		super.addSign("%");
		super.setPriority(200);
		super.setFuntion(false);
	}
	
	public ArithmeticMod(List paramList)
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
			result = new Integer(v1.toDecimal().intValue() % v2.toDecimal().intValue());
		}
		else
		{
			throw new CalculateException("取余计算要求两方都是数字类型。");
		}
		
		return result;
	}
}
