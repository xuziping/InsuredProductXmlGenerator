package com.xuzp.insuredxmltool.core.tool.formula.aries;


import com.xuzp.insuredxmltool.core.tool.formula.aries.arithmetic.*;

import java.util.ArrayList;
import java.util.List;

public class ArithmeticMgr
{
	private static List arithmeticList = null;
//	private static Map userFuncMap = new HashMap();
	
	private static void build()
	{
		arithmeticList = new ArrayList();
		
		arithmeticList.add(new ArithmeticAdd());
		arithmeticList.add(new ArithmeticAnd());
		arithmeticList.add(new ArithmeticHas());
		arithmeticList.add(new ArithmeticOr());
		arithmeticList.add(new ArithmeticSubtract());
		arithmeticList.add(new ArithmeticMultiply());
		arithmeticList.add(new ArithmeticDivide());
		arithmeticList.add(new ArithmeticPoint());
		arithmeticList.add(new ArithmeticMod());
		arithmeticList.add(new ArithmeticIn());
		arithmeticList.add(new ArithmeticGreater());
		arithmeticList.add(new ArithmeticGreaterEqual());
		arithmeticList.add(new ArithmeticLess());
		arithmeticList.add(new ArithmeticLessEqual());
		arithmeticList.add(new ArithmeticEqual());
		arithmeticList.add(new ArithmeticInequality());
		arithmeticList.add(new ArithmeticStartsWith());
		arithmeticList.add(new FunctionCase());
		arithmeticList.add(new FunctionTry());
		arithmeticList.add(new FunctionInt());
		arithmeticList.add(new FunctionRound());
		arithmeticList.add(new FunctionMax());
		arithmeticList.add(new FunctionMin());
		arithmeticList.add(new FunctionPow());
	}
	
	public static void addArithmetic(Arithmetic arithmetic)
	{
		if (arithmeticList == null)
			build();
		
		arithmeticList.add(arithmetic);
	}
	
	public static Arithmetic getArithmetic(String sign) throws Exception
	{
		if (arithmeticList == null)
			build();
		
		for (int i=0;i<arithmeticList.size();i++)
		{
			Arithmetic arithmetic = (Arithmetic)arithmeticList.get(i);
			List signList = arithmetic.getSigns();
			if (signList.indexOf(sign) >= 0)
			{
				return arithmetic;
			}
		}
		
		//throw new FormulaTranslateException("无法解读的运算符号");
		return null;
	}
	
//	public static void addFunctionUserDefine(UserFunc func)
//	{
//		userFuncMap.put(func.getName(), func);
//	}
//	
//	public static UserFunc getFunctionUserDefine(String name)
//	{
//		return (UserFunc)userFuncMap.get(name);
//	}
//	
//	public static void clearFunctionUserDefine()
//	{
//		userFuncMap.clear();
//	}
}
