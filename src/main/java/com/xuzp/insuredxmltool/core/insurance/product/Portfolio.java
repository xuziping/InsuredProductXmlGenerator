package com.xuzp.insuredxmltool.core.insurance.product;

import java.util.ArrayList;

public class Portfolio extends ArrayList
{
	private static final long serialVersionUID = 1L;

	public void addProduct(Insurance parent, Insurance product, InitValue value, String desc)
	{
		this.add(new InsuranceRecom(parent, product, value, desc));
	}
	
	public void addProduct(Insurance product, InitValue value, String desc)
	{
		this.add(new InsuranceRecom(product, value, desc));
	}
}
