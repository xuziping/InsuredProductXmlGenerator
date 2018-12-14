package com.xuzp.insuredxmltool.core.insurance.plan.filter;

import com.xuzp.insuredxmltool.core.insurance.plan.Plan;

import java.io.Serializable;


public interface FilterPlan extends Serializable
{
	public Object filtrate(Plan plan, String attachmentName);
}
