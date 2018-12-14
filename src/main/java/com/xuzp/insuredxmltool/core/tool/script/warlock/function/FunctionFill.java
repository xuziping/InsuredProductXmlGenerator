package com.xuzp.insuredxmltool.core.tool.script.warlock.function;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Function;
import com.xuzp.insuredxmltool.core.tool.formula.Value;

public class FunctionFill implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		Object value = v[1];
		
		Object x = v[0];
		if (x instanceof double[])
		{
			double[] xx = (double[])x;
			double val = Value.doubleOf(value);
			for (int i = 0; i < xx.length; i++)
				xx[i] = val;
			
			return x;
		}
		else if (x instanceof Object[])
		{
			Object[] val = (Object[])value;
			Object[] src = (Object[])x;
			Object[] res = new Object[val.length];
			
			for (int i=0;i<src.length;i++)
				res[i] = src[i];
			for (int i=src.length;i<res.length;i++)
				res[i] = val[i];
			
			return res;
		}
		
		return null;
	}
}
