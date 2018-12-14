package com.xuzp.insuredxmltool.core.insurance.product;

import java.util.Date;

public interface AgeCalculator
{
	public int getAge(Date birthday, Date now);
}
