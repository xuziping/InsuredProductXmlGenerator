package com.xuzp.insuredxmltool.core.tool.data.source;

import com.xuzp.insuredxmltool.core.tool.data.DataParser;
import com.xuzp.insuredxmltool.core.tool.data.DataRecord;
import com.xuzp.insuredxmltool.core.tool.data.DataSource;
import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.FormulaUtil;
import com.xuzp.insuredxmltool.core.tool.script.Script;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Text;

import java.io.File;
import java.util.Map;

public class ScriptWL implements DataParser, DataSource
{
	private static final long serialVersionUID = 1L;
	
	public ScriptWL()
	{
	}

	@Override
	public DataSource newSource(Map param)
	{
		String nameStr = (String)param.get("name");
		String scriptStr = (String)param.get("script");
		File file = (File)param.get("file");
		
		ScriptWL swl = null;
		
		if (scriptStr != null)
		{
			swl = new ScriptWL(nameStr.split(","), scriptStr);
		}
		else if (file != null && file.exists() && file.isFile())
		{
			swl = new ScriptWL(nameStr.split(","), Text.read(file));
		}
		
		return swl;
	}
	
	Script script;
	
	String[] groupName;
	
	public ScriptWL(String[] groupName, String script)
	{
		this.script = (Script) FormulaUtil.formulaOf(script);
		this.groupName = groupName;
	}

	public DataRecord search(Factors factors, String groupName)
	{
		return null;
	}

	public String[] getGroupsName()
	{
		return groupName;
	}
}
