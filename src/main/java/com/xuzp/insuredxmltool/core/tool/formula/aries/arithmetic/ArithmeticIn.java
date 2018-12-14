package com.xuzp.insuredxmltool.core.tool.formula.aries.arithmetic;

import com.xuzp.insuredxmltool.core.tool.formula.CalculateException;
import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Formula;
import com.xuzp.insuredxmltool.core.tool.formula.Value;

import java.util.List;

public class ArithmeticIn extends Arithmetic implements Formula
{
	private static final long serialVersionUID = 1L;

	public ArithmeticIn()
	{
		super.addSign("in");
		super.setPriority(50);
		super.setFuntion(false);
	}
	
	public ArithmeticIn(List paramList)
	{
		super.setParameter(paramList);
	}
	
	public Object run(Factors getter)
	{
		Value v1 = Value.valueOf(super.getParameter(0), getter);
		Value v2 = Value.valueOf(super.getParameter(1), getter);
		
		Object result = null;
		
		if (v2.getType() == Value.TYPE_LIST)
		{
			result = new Boolean(((List)v2.getValue()).indexOf(v1.getValue()) >= 0);
		}
		else
		{
//			result = new LexValue(new Boolean(false));
			throw new CalculateException("in运算要求右侧为List类型。");
		}
		
		return result;
	}
}
