package com.xuzp.insuredxmltool.core.tool.script.warlock.statement;


import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.script.Stack;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Code;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Reference;

public class Variable implements Code, Reference
{
	String varName;
	
	public Variable(String name)
	{
		this.varName = name;
	}

	public Object run(Factors factors)
	{
		if ("timems".equals(varName))
			return Double.valueOf((double)System.currentTimeMillis());
		
		return factors.get(varName);
	}
	
	public void let(Factors factors, Object value)
	{
		((Stack)factors).set(varName, value);
	}
	
	public String toString()
	{
		return varName;
	}

	public String toText(String space)
	{
		return varName;
	}
}
