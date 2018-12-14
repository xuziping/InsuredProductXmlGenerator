package com.xuzp.insuredxmltool.core.insurance.product;

import com.xuzp.insuredxmltool.core.insurance.product.load.Loader;
import com.xuzp.insuredxmltool.core.tool.data.DataParseException;
import com.xuzp.insuredxmltool.core.tool.data.source.DdsAuto;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

public class InsuranceMgr
{
	private Loader loader;
	
//	private String resourcePath = "";
	
	public static InsuranceMgr managerOf(String resPath, String xmlPath)
	{
		InsuranceMgr mgr = new InsuranceMgr();
		mgr.setLoader(resPath, xmlPath);
		
		return mgr;
	}

	/**
	 * 设置产品定义解析器
	 */
	public void setLoader(String resPath, String xmlPath)
	{
		loader = xmlPath == null ? new Loader() : new Loader(resPath, xmlPath);
	}
	
	/**
	 * 
	 * @return 返回Map[String id, Company c]
	 */
	public Map load()
	{	
		if (loader == null)
			setLoader(null, null);
		
		DdsAuto.setFileReader(new DdsAuto.FileReader()
		{
			public InputStream open(File file)
			{
				InputStream dis;
				
				String n = file.getAbsolutePath();
				
				try
				{
//					if (n.endsWith(".prd"))
//						dis = PackFile.open(file, n.substring(n.length() - 4) + ".dat");
//					else
						dis = new FileInputStream(file);
				}
				catch (Exception e)
				{
					throw new DataParseException("Can't open file<" + n + ">.", e);
				}

				return dis;
			}
		});
		
		return loader.load();
	}

//	public String getResourcePath()
//	{
//		return resourcePath;
//	}
//	
//	public void setResourcePath(String resourcePath) 
//	{
//		this.resourcePath = resourcePath;
//	}
//	
//	public static Formula formulaOf(String formula)
//	{
//		if (formula == null || "".equals(formula.trim()))
//			return null;
//		
//		/*
//		 * 用getFormulaEngine().formulaOf(formula)可以降一层函数调用
//		 * 用new Formula(formula)可以用toString()查看公式内容
//		 */
//		return FormulaUtil.formulaOf(formula);
//	}
}
