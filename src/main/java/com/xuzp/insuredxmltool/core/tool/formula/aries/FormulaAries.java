package com.xuzp.insuredxmltool.core.tool.formula.aries;

import com.xuzp.insuredxmltool.core.tool.formula.Formula;
import com.xuzp.insuredxmltool.core.tool.formula.FormulaEngine;
import com.xuzp.insuredxmltool.core.tool.formula.FormulaException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FormulaAries implements FormulaEngine, Serializable
{
	private static final long serialVersionUID = 1L;

	private static Map formulaCompiledMap	= new HashMap();
	
	public Formula formulaOf(String formula) throws FormulaException
	{
		Formula formulaCompiled = (Formula)formulaCompiledMap.get(formula);
		
		if (formulaCompiled == null)
		{
			formulaCompiled = new FormulaCompiler(formula).compile();
			formulaCompiledMap.put(formula, formulaCompiled);
		}
		
		return formulaCompiled;
	}
}
