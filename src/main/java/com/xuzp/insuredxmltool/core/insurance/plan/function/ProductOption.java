package com.xuzp.insuredxmltool.core.insurance.plan.function;

import com.xuzp.insuredxmltool.core.insurance.plan.Commodity;

import java.io.Serializable;

public class ProductOption implements FunctionCommodity, Serializable
{
	private static final long serialVersionUID = 1L;

	public String getName()
	{
		return "ProductOption";
	}
	
	public Object runCommodity(Commodity p, Object[] param)
	{
		return p.getInput((String)param[0]);
	}
}
