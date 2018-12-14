package com.xuzp.insuredxmltool.core.insurance.product;


public class InsuranceRecom
{
	Insurance parent;
	Insurance insurance;
	InitValue initValue;
	String desc;
	
	public InsuranceRecom(Insurance insurance, InitValue initValue, String desc)
	{
		this(null, insurance, initValue, desc);
	}
	
	public InsuranceRecom(Insurance parent, Insurance insurance, InitValue initValue, String desc)
	{
		this.parent = parent;
		this.initValue = initValue;
		this.insurance = insurance;
		this.desc = desc;
	}
	
	public Insurance getInsurance()
	{
		return insurance;
	}

	public InitValue getInitValue()
	{
		return initValue;
	}
	
	public String getDesc()
	{
		return desc;
	}
}
