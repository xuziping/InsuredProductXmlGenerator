package com.xuzp.insuredxmltool.core.tool.formula.aries.arithmetic;

import com.xuzp.insuredxmltool.core.tool.formula.CalculateException;
import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Formula;
import com.xuzp.insuredxmltool.core.tool.formula.Value;

import java.util.List;

public class ArithmeticMultiply extends Arithmetic implements Formula
{
	private static final long serialVersionUID = 1L;

	public ArithmeticMultiply()
	{
		super.addSign("*");
		super.setPriority(200);
		super.setFuntion(false);
	}
	
	public ArithmeticMultiply(List paramList)
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
			result = v1.toDecimal().multiply(v2.toDecimal());
		}
		else
		{
			throw new CalculateException("乘法计算要求两方都是数字类型。");
		}
		
		return result;
	}
}
