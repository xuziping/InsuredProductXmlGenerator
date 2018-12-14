package com.xuzp.insuredxmltool.core.tool.script.warlock.function;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Function;

public class FunctionPrint implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		for (int i = 0; i < v.length; i++)
		{
			if (i != 0)
				System.out.print(", ");
			
			System.out.print(v[i]);
		}
		System.out.println();
		
		return null;
	}
}
