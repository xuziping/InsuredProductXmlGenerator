package com.xuzp.insuredxmltool.core.tool.data.source;

import com.xuzp.insuredxmltool.core.tool.data.DataNotFoundException;
import com.xuzp.insuredxmltool.core.tool.data.DataParser;
import com.xuzp.insuredxmltool.core.tool.data.DataRecord;
import com.xuzp.insuredxmltool.core.tool.data.DataSource;
import com.xuzp.insuredxmltool.core.tool.formula.Factors;

import java.util.Map;

public class DatabaseDS implements DataParser, DataSource
{
	private static final long serialVersionUID = 1L;

	public DatabaseDS(Map param)
	{
	}

	@Override
	public DataRecord search(Factors param, String groupName) throws DataNotFoundException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getGroupsName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataSource newSource(Map param)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
