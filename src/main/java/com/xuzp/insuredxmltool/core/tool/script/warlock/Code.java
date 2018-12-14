package com.xuzp.insuredxmltool.core.tool.script.warlock;


import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Formula;

public interface Code extends Formula
{
	public Object run(Factors factors);
	
	public String toText(String space);
}
