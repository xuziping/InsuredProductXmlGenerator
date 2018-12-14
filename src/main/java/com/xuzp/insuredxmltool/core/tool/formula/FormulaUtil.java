package com.xuzp.insuredxmltool.core.tool.formula;

import com.xuzp.insuredxmltool.core.tool.script.Script;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Words;

public class FormulaUtil
{
	private static FormulaEngine formulaEngine;
//	private static FormulaEngine formulaEngine = new FormulaAries();
	
	public static FormulaEngine getFormulaEngine() 
	{
		if (formulaEngine == null)
		{
			formulaEngine = new FormulaEngine()
			{
				@Override
				public Formula formulaOf(String formula)
				{
					if (formula == null || "".equals(formula.trim()))
						return null;

					return new Script(Words.wordsOf(formula), true);
				}
			};

		}
		
		return formulaEngine;
	}

	public static void setFormulaEngine(FormulaEngine formulaEngine) 
	{
		FormulaUtil.formulaEngine = formulaEngine;
	}
	
	public static Formula formulaOf(String formulaText)
	{
		return getFormulaEngine().formulaOf(formulaText);
	}
}
