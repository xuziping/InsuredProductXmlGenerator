package com.xuzp.insuredxmltool.core.tool.script.warlock.function;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Function;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FunctionSize implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 1)
		{
			int num = -1;
			
			Object r = v[0];
			if (r == null)
				num = -1;
			else if (r instanceof Object[]) //Object[]对于Object[][]也是true，但是double[]对于double[][]不是
				num = ((Object[])r).length;
			else if (r instanceof double[])
				num = ((double[])r).length;
			else if (r instanceof double[][])
				num = ((double[][])r).length;
			else if (r instanceof int[])
				num = ((int[])r).length;
			else if (r instanceof int[][])
				num = ((int[][])r).length;
			else if (r instanceof Collection)
				num = ((List)r).size();
			else if (r instanceof Map)
				num =((Map)r).size();
			
			if (num < -1)
				throw new RuntimeException("求size的对象无法识别为数组 - " + r.getClass());
			
			return Integer.valueOf(num);
		}
		
		throw new RuntimeException("错误的size运算");
	}
}
