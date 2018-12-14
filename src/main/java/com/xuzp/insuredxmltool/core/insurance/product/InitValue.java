package com.xuzp.insuredxmltool.core.insurance.product;


import com.xuzp.insuredxmltool.core.tool.formula.Formula;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InitValue implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	String insurantId; //被保险人不是默认的被保险人时，设定这个
	
	Formula premium;
	Formula amount;
	Formula quantity;
	
	List optionType;
	
	Map value = new HashMap();
	
	public InitValue()
	{
	}
	
	public List getOptionList()
	{
		return optionType;
	}
	
	public void setOption(String key, Formula option)
	{
		if (optionType == null)
			optionType = new ArrayList();
		
		optionType.add(key);
		
		set("option:" + key, option);
	}
	
	public Formula getOption(String key)
	{
		return (Formula)get("option:" + key);
	}
	
	public void set(String name, Object v)
	{
		value.put(name, v);
	}
	
	public Object get(String name)
	{
		return value.get(name);
	}

	public Formula getPremium()
	{
		return premium;
	}

	public void setPremium(Formula premium)
	{
		this.premium = premium;
	}

	public Formula getAmount()
	{
		return amount;
	}

	public void setAmount(Formula amount)
	{
		this.amount = amount;
	}

	public Formula getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Formula quantity)
	{
		this.quantity = quantity;
	}

	public String getInsurantId()
	{
		return insurantId;
	}

	public void setInsurantId(String insurantId)
	{
		this.insurantId = insurantId;
	}
}
