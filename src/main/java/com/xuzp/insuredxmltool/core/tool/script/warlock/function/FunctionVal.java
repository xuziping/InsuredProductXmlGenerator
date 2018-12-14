package com.xuzp.insuredxmltool.core.tool.script.warlock.function;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Function;
import com.xuzp.insuredxmltool.core.tool.formula.Value;
import com.xuzp.insuredxmltool.core.tool.script.Script;

import java.util.List;

public class FunctionVal implements Function
{
	public Object run(Object[] v, Factors factors)
	{
		if (v.length == 1) //字符串转对象 "[1,2,[3,4]]">>对象
		{ 
			if (v[0] == null)
				return null;
			
			Script r = Script.scriptOf(v[0].toString());
			return r.run(factors);
		}
		else if (v.length == 2) //这个考虑取消
		{
			int index = Value.intOf(v[1]);
			Object s = new Integer(0);
			
			Object r = v[0];
			if (r == null)
			{
			}
			else if (r instanceof double[])
			{
				if (index < ((double[])r).length)
					s = Double.valueOf(((double[])r)[index]);
			}
			else if (r instanceof int[])
			{
				if (index < ((int[])r).length)
					s = Integer.valueOf(((int[])r)[index]);
			}
			else if (r instanceof List)
			{
				if (index < ((List)r).size())
					s = ((List)r).get(index);
			}
			else if (r instanceof Object[])
			{
				if (index < ((Object[])r).length)
					s = ((Object[])r)[index];
			}
			
			return s;
		}
		
		throw new RuntimeException("错误的val运算");
	}
}
