package com.xuzp.insuredxmltool.core.tool.formula.aries.arithmetic;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Formula;

import java.util.List;

public class ArrayNew implements Formula
{
	List arrayValue;
	
	public ArrayNew(List arrayValue)
	{
		this.arrayValue = arrayValue;
	}
	
	public int getArrayLength()
	{
		return arrayValue.size();
	}
	
	public Formula getValueAt(int i)
	{
		return (Formula)arrayValue.get(i);
	}

	public Object run(Factors getter)
	{
		Object[] v = new Object[getArrayLength()];
		for (int i=0;i<v.length;i++)
		{
			Formula p = (Formula)getValueAt(i);
			v[i] = p.run(getter);
		}
		
		return v;
	}
}
