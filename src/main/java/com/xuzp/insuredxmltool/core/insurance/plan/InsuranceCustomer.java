package com.xuzp.insuredxmltool.core.insurance.plan;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;

import java.util.Date;

public interface InsuranceCustomer extends Factors
{
	public static final int GENDER_MALE			= 1;
	public static final int GENDER_FEMALE		= 2;
	
	public String getId();

	public Date getBirthday();
	
	public int getGenderCode();
	
	public int getOccupationCategory(String typeCode);
}
