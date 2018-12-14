package com.xuzp.insuredxmltool.core.insurance.product.attachment.table;


import com.xuzp.insuredxmltool.core.insurance.plan.filter.table.TableText;
import com.xuzp.insuredxmltool.core.tool.formula.Formula;

/**
 * @deprecated
 * @author lerrain
 *
 */
public class TableTextDef extends TableText
{
	private static final long serialVersionUID = 1L;
	
	Formula condition;

	public Formula getCondition()
	{
		return condition;
	}

	public void setCondition(Formula condition)
	{
		this.condition = condition;
	}
}
