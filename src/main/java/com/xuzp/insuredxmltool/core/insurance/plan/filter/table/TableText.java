package com.xuzp.insuredxmltool.core.insurance.plan.filter.table;


import com.xuzp.insuredxmltool.core.insurance.plan.filter.StaticText;

public class TableText extends StaticText
{
	private static final long serialVersionUID = 1L;

	boolean count = true;

	public boolean isCount()
	{
		return count;
	}

	public void setCount(boolean count)
	{
		this.count = count;
	}
}
