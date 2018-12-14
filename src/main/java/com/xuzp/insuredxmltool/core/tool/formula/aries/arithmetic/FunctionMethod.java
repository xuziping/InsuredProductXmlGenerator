package com.xuzp.insuredxmltool.core.tool.formula.aries.arithmetic;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Formula;

import java.util.ArrayList;
import java.util.List;

public class FunctionMethod implements Formula
{
	String methodName;
	List parameterList;
	
	public FunctionMethod(String methodName, List parameterList)
	{
		this.methodName = methodName;
		this.parameterList = parameterList;
	}

	public Object run(Factors getter)
	{
//		Object object = getter.getValue(objectName);
//		Method method = object.getClass().getDeclaredMethod(functionName, new Class[] {});
//		Object result = method.invoke(object, new Object[] {});
//		return new LexValue(result);
		
		List result = new ArrayList();
		result.add(methodName);
		
		int s = parameterList == null ? 0 : parameterList.size();
		for (int i=0;i<s;i++)
		{
			Formula p = (Formula)parameterList.get(i);
			result.add(p.run(getter));
		}
		
		return result;
	}
}
