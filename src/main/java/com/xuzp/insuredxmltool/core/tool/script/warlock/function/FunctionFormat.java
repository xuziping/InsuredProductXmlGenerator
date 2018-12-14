package com.xuzp.insuredxmltool.core.tool.script.warlock.function;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Function;

import java.util.Date;

public class FunctionFormat implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 1)
		{
			Object val = v[0];
			if (val == null)
				return "";
			else if (val instanceof Date)
				return val.toString();
			else
				return val.toString();
		}
		
		if (v.length == 2)
		{
//			String style = (String)v[1];
			Object val = v[0];
			
			if (val == null)
				return "";
			else if (val instanceof Date)
				return val.toString();
			else
				return val.toString();
		}
		
		throw new RuntimeException("错误的format运算");
	}
}
