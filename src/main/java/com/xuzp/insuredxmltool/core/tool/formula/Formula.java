package com.xuzp.insuredxmltool.core.tool.formula;

import java.io.Serializable;

public interface Formula extends Serializable
{
	public Object run(Factors factors);
}
