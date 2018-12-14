package com.xuzp.insuredxmltool.core.insurance.plan.filter;

import com.xuzp.insuredxmltool.core.insurance.plan.Commodity;

import java.io.Serializable;

public interface FilterCommodity extends Serializable
{
	public Object filtrate(Commodity product, String attachmentName);
}
