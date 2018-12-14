package com.xuzp.insuredxmltool.core.insurance.plan.function;

import com.xuzp.insuredxmltool.core.insurance.plan.Commodity;

public interface FunctionCommodity
{
	public String getName();

	public abstract Object runCommodity(Commodity p, Object[] v);
}
