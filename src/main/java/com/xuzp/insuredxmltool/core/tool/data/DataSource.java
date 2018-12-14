package com.xuzp.insuredxmltool.core.tool.data;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;

import java.io.Serializable;

public interface DataSource extends Serializable
{
	public DataRecord search(Factors param, String groupName);
	
	public String[] getGroupsName();
}
