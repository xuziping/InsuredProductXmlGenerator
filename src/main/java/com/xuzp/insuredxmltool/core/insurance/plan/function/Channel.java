package com.xuzp.insuredxmltool.core.insurance.plan.function;

import com.xuzp.insuredxmltool.core.insurance.plan.Commodity;
import com.xuzp.insuredxmltool.core.insurance.plan.Plan;

import java.io.Serializable;

public class Channel implements FunctionCommodity, FunctionPlan, Serializable
{
	private static final long serialVersionUID = 1L;

	public String getName()
	{
		return "Channel";
	}

	public Object runPlan(Plan p, Object[] param)
	{
		Object channel = p.getValue("CHANNEL");
		
		if (channel == null)
			throw new FunctionCalculateException("没有设置渠道信息");
		
		for (int i = 0; i < param.length; i++)
		{
			if (channel.equals(param[i]))
				return new Boolean(true);
		}
		
		return new Boolean(false);
	}

	public Object runCommodity(Commodity p, Object[] param)
	{
		Object channel = p.getPlan().getValue("CHANNEL");
		
		if (channel == null)
			throw new FunctionCalculateException("没有设置渠道信息");

		for (int i = 0; i < param.length; i++)
		{
			if (channel.equals(param[i]))
				return new Boolean(true);
		}
		
		return new Boolean(false);
	}
}
