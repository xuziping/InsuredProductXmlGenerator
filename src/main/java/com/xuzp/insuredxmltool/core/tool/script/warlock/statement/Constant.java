package com.xuzp.insuredxmltool.core.tool.script.warlock.statement;


import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Code;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Words;

public class Constant implements Code
{
	int type;
	String text;
	
	Object v;
	
	public Constant(int type, String text)
	{
		this.type = type;
		this.text = text;
		
		if (type == Words.NULLPT)
		{
			v = null;
		}
		else if (type == Words.TRUE)
		{
			v = Boolean.TRUE;
		}
		else if (type == Words.FALSE)
		{
			v = Boolean.FALSE;
		}
		else if (type == Words.NUMBER)
		{
			v = Double.valueOf(text);
		}
		else if (type == Words.STRING)
		{
			v = text.substring(1, text.length() - 1);
		}
		else
		{
			throw new RuntimeException("无法识别的常量");
		}
	}
	
	public Object run(Factors factors)
	{
		return v;
	}

	public String toText(String space)
	{
		if (v == null)
			return "NULL";
		
		return v instanceof String ? "'" + v + "'" : v.toString();
	}
	
	public String toString()
	{
		return v == null ? null : v.toString();
	}

//	public double calculate(Factors p)
//	{
//		return vd;
//	}
}
