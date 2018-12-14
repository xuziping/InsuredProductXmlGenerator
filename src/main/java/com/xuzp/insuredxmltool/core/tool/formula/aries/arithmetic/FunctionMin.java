package com.xuzp.insuredxmltool.core.tool.formula.aries.arithmetic;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Formula;
import com.xuzp.insuredxmltool.core.tool.formula.Value;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

public class FunctionMin extends Arithmetic implements Formula
{
	private static final long serialVersionUID = 1L;

	public FunctionMin()
	{
		super.addSign("min");
		super.setPriority(1000);
	}
	
	public FunctionMin(List paramList)
	{
		super.setParameter(paramList);
	}
	
	public Object run(Factors getter)
	{
		BigDecimal r = null;

		int size = paramList.size();
		if (size == 1)
		{
			Value key = Value.valueOf((Formula)paramList.get(0), getter);
			if (key.getType() == Value.TYPE_ARRAY)
			{
				if (key.getValue() instanceof BigDecimal[][])
				{
					BigDecimal[][] array = (BigDecimal[][])key.getValue();
					for (int i=0;i<array.length;i++)
					{
						for (int j=0;j<array[0].length;j++)
						{
							BigDecimal bd = array[i][j];
							if (r == null || bd.compareTo(r) < 0)
								r = bd;
						}
					}
				}
				else if (key.getValue() instanceof BigInteger[][])
				{
					BigInteger[][] array = (BigInteger[][])key.getValue();
					for (int i=0;i<array.length;i++)
					{
						for (int j=0;j<array[0].length;j++)
						{
							BigInteger bd = array[i][j];
							if (r == null || bd.compareTo(r.toBigInteger()) < 0)
								r = new BigDecimal(bd);
						}
					}
				}
				else if (key.getValue() instanceof BigDecimal[])
				{
					BigDecimal[] array = (BigDecimal[])key.getValue();
					for (int i=0;i<array.length;i++)
					{
						BigDecimal bd = array[i];
						if (r == null || bd.compareTo(r) < 0)
							r = bd;
					}
				}
				else if (key.getValue() instanceof BigInteger[])
				{
					BigInteger[] array = (BigInteger[])key.getValue();
					for (int i=0;i<array.length;i++)
					{
						BigInteger bd = array[i];
						if (r == null || bd.compareTo(r.toBigInteger()) < 0)
							r = new BigDecimal(bd);
					}
				}
			}
			else if (key.getType() == Value.TYPE_LIST)
			{
				List list = (List)key.getValue();
				for (Iterator iter = list.iterator(); iter.hasNext();)
				{
					Object obj = iter.next();
					Value v = Value.valueOf(obj);
					if (v.isDecimal())
					{
						if (r == null || v.toDecimal().compareTo(r) < 0)
							r = v.toDecimal();
					}
				}
			}
		}
		else
		{
			for (int i=0;i<size;i++)
			{
				Value key = Value.valueOf((Formula)paramList.get(i), getter);
				
				if (r == null || key.toDecimal().compareTo(r) < 0)
					r = key.toDecimal();
			}
		}
		
		return r;
	}
}
