package com.xuzp.insuredxmltool.core.insurance.product.load;

import com.xuzp.insuredxmltool.core.insurance.product.attachment.axachart.AxaChartParser;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.chart.ChartParser;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.combo.ComboChartDefParser;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.combo.ComboDefParser;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.combo.ComboParser;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.coverage.CoverageParser;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.document.DocumentParser;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.liability.LiabilityParser;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.table.TableParser;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.tgraph.TGraphParser;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Loader
{
	String resDir;
	String xmlPath;
	
	Map attachmentParsers;
	
	Map companys;
	
	public Loader()
	{
		this("./", "insurance.xml");
	}
	
	public Loader(String resDir, String xmlFile)
	{
		initAttachments();
		
		this.resDir = resDir;

		if (resDir == null)
			resDir = "";
		else if (!resDir.endsWith("/") && !resDir.endsWith("\\"))
			resDir = resDir + File.separator;
		
		this.xmlPath = resDir + xmlFile;
	}
	
	private void initAttachments()
	{
		attachmentParsers = new HashMap();
		attachmentParsers.put("table", new TableParser());
		attachmentParsers.put("coverage", new CoverageParser());
		attachmentParsers.put("liability", new LiabilityParser());
		attachmentParsers.put("combo", new ComboParser());
		attachmentParsers.put("combo_def", new ComboDefParser());
		attachmentParsers.put("combo_chart_def", new ComboChartDefParser());
		attachmentParsers.put("chart", new ChartParser());
		attachmentParsers.put("chart@axa", new AxaChartParser());
		attachmentParsers.put("tgraph", new TGraphParser());
		attachmentParsers.put("document", new DocumentParser());
	}
	
	protected Map getAttachmentParsers()
	{
		return attachmentParsers;
	}
	
	protected Map getCompanys()
	{
		return companys;
	}
	
	public Map load()
	{
		if (companys == null)
		{
			companys = new HashMap();

			try
			{
				read(XmlNode.nodeOf(new File(xmlPath)));
			}
			catch (Exception e)
			{
				throw new ProductParseException("主配置文件解析失败：" + xmlPath, e);
			}
		}
		
		return companys;
	}
	
	private void read(XmlNode e)
	{
		for (Iterator i = e.getChildren().iterator(); i.hasNext(); )
		{
			XmlNode n1 = (XmlNode)i.next();
			if ("company".equalsIgnoreCase(n1.getName()) || "corporation".equalsIgnoreCase(n1.getName()))
			{
				CompanyLoader cl = new CompanyLoader(resDir, this);
				
				String parent = n1.getAttribute("extends");
				cl.loadCompany(parent, n1);
			}
		}
	}
}
