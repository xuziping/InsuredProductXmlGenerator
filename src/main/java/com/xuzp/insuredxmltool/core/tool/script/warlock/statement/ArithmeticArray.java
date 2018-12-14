package com.xuzp.insuredxmltool.core.tool.script.warlock.statement;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Function;
import com.xuzp.insuredxmltool.core.tool.formula.Value;
import com.xuzp.insuredxmltool.core.tool.script.Stack;
import com.xuzp.insuredxmltool.core.tool.script.SyntaxException;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Code;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Reference;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Wrap;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Expression;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Words;

import java.util.List;
import java.util.Map;

public class ArithmeticArray implements Code, Reference
{
	String type = null;
	
	Code v, a;
	
//	ArithmeticArray pv;
	
	public ArithmeticArray(Words ws, int i)
	{
		if (ws.getType(i) != Words.BRACKET || ws.getType(ws.size() - 1) != Words.BRACKET_R)
			throw new SyntaxException("找不到数组的右括号");
		
		if (i > 0) //否则为Object数组定义 [a, b, c, ...]
		{
			if (i == 1 && "double".equals(ws.getWord(0))) // double[a, b, c, ...]
				type = "double";
			else
				v = Expression.expressionOf(ws.cut(0, i));
		}
		
		a = Expression.expressionOf(ws.cut(i + 1, ws.size() - 1));
		
//		//objectiveC 下NSArray效率过低，这里对二维数组做优化
//		if (v instanceof ArithmeticArray)
//			pv = (ArithmeticArray)v;
	}

	public Object run(Factors factors)
	{
		if (v == null) // [a, b, c, ...] 等同于java的 new Object[] {a, b, c, ...}
		{
			if (type == null) // [a, b, c, ...] 
				return Wrap.arrayOf(a, factors);
			else // double[a, b, c, ...] 
				return Wrap.doubleArrayOf(a, factors);
		}

		Object val = v.run(factors);
		Object pos = a.run(factors);
		
		int i, j;
		
		if (pos instanceof Wrap) //旧的写法IT.ABC[i, j]，向下兼容一下，最多2维，3维以上无视
		{
			Object[] p = ((Wrap)pos).toArray();
			i = Value.intOf(p[0]);
			j = Value.intOf(p[1]);
			
			if (val instanceof int[][])
			{
				return Integer.valueOf(((int[][])val)[i][j]);
			}
			else if (val instanceof double[][])
			{
				return Double.valueOf(((double[][])val)[i][j]);
			}
			else if (val instanceof Object[][])
			{
				return ((Object[][])val)[i][j];
			}
			else if (val instanceof Object[])
			{
				val = ((Object[])val)[i];
				
				if (val instanceof int[])
				{
					return Integer.valueOf(((int[])val)[j]);
				}
				else if (val instanceof double[])
				{
					return Double.valueOf(((double[])val)[j]);
				}
				else if (val instanceof Object[])
				{
					return ((Object[])val)[j];
				}
			}
			else if (val instanceof Function) //旧的IT.XXX[A, B]，前面的IT.XXX实际是个函数，这里做一次转译
			{
				return ((Function)val).run(p, factors);
			}
			
			throw new RuntimeException("无法处理的旧版2维数组运算");
		}
		
		if (pos instanceof String)
		{
			if (val instanceof Map)
				return ((Map<?, ?>)val).get(pos);
			if (val instanceof Factors)
				return ((Factors)val).get((String)pos);
			
			throw new RuntimeException("index为string要求数组为map或factors");
		}
		
		//现在都是这种写法IT.ABC[i][j]
		i = Value.intOf(pos);
		
//		if (val instanceof Formula) //函数
//		{
//			Stack stack = new Stack(factors);
//
//			if (wrap != null)
//			{
//				stack.set("#0", new Integer(wrap.length));
//				for (int i = 0; i < wrap.length; i++)
//				{
//					stack.set("#" + (i + 1), wrap[i]);
//				}
//			}
//			
//			return ((Formula)val).run(stack);
//		}
		
		if (val instanceof int[]) //1维数组
		{
			return Integer.valueOf(((int[])val)[i]);
		}
		else if (val instanceof double[]) //1维数组
		{
			return Double.valueOf(((double[])val)[i]);
		}
		else if (val instanceof int[][]) //2维数组
		{
			return ((int[][])val)[i];
		}
		else if (val instanceof double[][]) //2维数组
		{
			return ((double[][])val)[i];
		}
//		else if (val instanceof Object[][])
//		{
//			return ((Object[][])val)[i];
//		}
		else if (val instanceof Object[])
		{
//			System.out.println("*****" + ((Object[])val)[i]);
			return ((Object[])val)[i];
		}
		else if (val instanceof List)
		{
			return ((List)val).get(i);
		}
		
		throw new RuntimeException("无法处理的数组运算 - " + toText("") + " is " + val);
	}

	public String toText(String space)
	{
		return (v == null ? "OBJECT" : v.toText("")) + "[" + a.toText("") + "]";
	}

	public void let(Factors factors, Object value)
	{
//		//objectiveC 下NSArray效率过低，这里对二维数组做优化
//		if (pv != null)
//		{
//			Object val = pv.v.run(factors);
//			
//			Object[] wrap1 = Wrap.arrayOf(pv.a, factors);
//			Object[] wrap2 = Wrap.arrayOf(a, factors);
//			
//			if (val instanceof int[][]) //2维数组
//			{
//				((int[][])val)[Value.intOf(wrap1[0])][Value.intOf(wrap2[0])] = Value.intOf(value);
//			}
//			else if (val instanceof double[][]) //2维数组
//			{
//				((double[][])val)[Value.intOf(wrap1[0])][Value.intOf(wrap2[0])] = Value.doubleOf(value);
//			}
//			
//			return;
//		}
		
		Object val = v.run(factors);
//		System.out.println("*****" + val);
		
		Object[] wrap = Wrap.arrayOf(a, factors);
		
		if (wrap == null || wrap.length != 1)
		{
			throw new RuntimeException("无法处理的数组赋值运算，数组下标无法计算");
		}
		
		Object pos = wrap[0];
		if (pos instanceof String)
		{
			if (val instanceof Map)
				((Map<Object, Object>)val).put((String)pos, value);
			else if (val instanceof Stack)
				((Stack)val).set((String)pos, value);
			else
				throw new RuntimeException("下标为string时，只可以给map或stack赋值");
		}
		else if (val instanceof int[][]) //2维数组
		{
			((int[][])val)[Value.intOf(wrap[0])] = (int[])value;
		}
		else if (val instanceof double[][]) //2维数组
		{
			((double[][])val)[Value.intOf(wrap[0])] = (double[])value;
		}
		else if (val instanceof double[]) //1维数组
		{
			((double[])val)[Value.intOf(wrap[0])] = Value.doubleOf(value);
		}
		else if (val instanceof int[]) //1维数组
		{
			((int[])val)[Value.intOf(wrap[0])] = Value.intOf(value);
		}
		else if (val instanceof Object[]) //1维数组
		{
			((Object[])val)[Value.intOf(wrap[0])] = value;
		}
		else if (val instanceof Object[][]) //2维数组
		{
			((Object[][])val)[Value.intOf(wrap[0])] = (Object[])value;
		}
		else if (val instanceof List) //1维数组
		{
			((List)val).set(Value.intOf(wrap[0]), value);
		}
		else
		{
			throw new RuntimeException("无法处理的数组赋值运算 - " + val + "[" + wrap[0] + "] = " + value);
		}
	}
}
