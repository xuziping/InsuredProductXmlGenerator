package com.xuzp.insuredxmltool.core.insurance.plan.function;

import com.xuzp.insuredxmltool.core.insurance.plan.Commodity;
import com.xuzp.insuredxmltool.core.insurance.plan.Plan;

import java.io.Serializable;

/**
 * 获取保费（剔除传入id以外的）
 * 如果是险种，则返回该险种和它所有附加险保费和。
 * 如果是计划，则返回整个计划的保费和。
 * 
 * 如：premium_except('001010001', '001010002')
 * 按照规则计算保费，忽略001010001, 001010002两个险种
 * 
 * @author lerrain
 */
public class PremiumExcept implements FunctionCommodity, FunctionPlan, Serializable
{
	private static final long serialVersionUID = 1L;

	public String getName()
	{
		return "PremiumExcept";
	}

	private boolean has(Object[] param, String p)
	{
		if (param == null || param.length == 0)
			return false;

		for (int i = 0; i < param.length; i++)
		{
			if (p.equals(param[i]))
				return true;
		}

		return false;
	}

	public Object runPlan(Plan plan, Object[] param)
	{
		double premium = 0;

		for (int i = 0; i < plan.size(); i++)
		{
			Commodity r = plan.getCommodity(i);
			if (!has(param, r.getProduct().getId()))
				premium += r.getPremium();
		}

		return Double.valueOf(premium);
	}

	public Object runCommodity(Commodity p, Object[] param)
	{
		double premium = 0;

		Plan plan = p.getPlan();
		for (int i = 0; i < plan.size(); i++)
		{
			Commodity r = plan.getCommodity(i);
			if (r.getParent() == p || r == p)
			{
				if (!has(param, r.getProduct().getId()))
					premium += r.getPremium();
			}
		}

		return Double.valueOf(premium);
	}
}
