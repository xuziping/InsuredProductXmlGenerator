package com.xuzp.insuredxmltool.core.insurance.plan.function;

import com.xuzp.insuredxmltool.core.insurance.plan.Plan;

public interface FunctionPlan
{
	public String getName();
	
	public abstract Object runPlan(Plan p, Object[] v);
}
