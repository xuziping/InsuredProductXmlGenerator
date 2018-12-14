package com.xuzp.insuredxmltool.core.insurance.plan.function;

import com.xuzp.insuredxmltool.core.insurance.plan.Commodity;
import com.xuzp.insuredxmltool.core.insurance.plan.Plan;

import java.io.Serializable;

public class CountMain implements FunctionPlan, Serializable
{
	private static final long serialVersionUID = 1L;

	public String getName()
	{
		return "CountMain";
	}

	public Object runPlan(Plan p, Object[] param)
	{
		int count = 0;
		int num = p.size();
		
		for (int i = 0; i < num; i++)
		{
			Commodity c = p.getCommodity(i);
			if (c.getParent() == null)
				count++;
		}
		
		return new Integer(count);
	}
}
