package com.xuzp.insuredxmltool.core.tool.script.warlock.statement;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Function;
import com.xuzp.insuredxmltool.core.tool.script.SyntaxException;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Code;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Wrap;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Expression;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Syntax;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Words;
import com.xuzp.insuredxmltool.core.tool.script.warlock.function.*;

/**
 * 
 * @author lerrain
 * 
 * 2014-08-11
 * 2014-08-10提到的这种形式，用?:来处理
 *
 * 2014-08-10
 * 由逗号分割开的多个表达式，通常是用作函数或数组的参数，并不是每个都需要计算的
 * 所以直接打包返回，处理的部分根据需要计算全部或者部分
 * 如：x[i] = case(i>0,x[i-1]+y,y); 
 * 如果每个都计算，那么这个函数是没办法运行的。
 * 
 */
public class ArithmeticFunction implements Code
{
	String name;
	
	Code params;
	
	Function fs;
	
	public ArithmeticFunction(Words ws, int i)
	{
		if (i > 0)
			throw new SyntaxException("不是一个有效的函数语法 - " + ws);
		
		name = ws.getWord(i);
		
		//内置函数，参数不直接运算
		if ("case".equals(name))
			fs = new FunctionCase();
		else if ("round".equals(name))
			fs = new FunctionRound();
		else if ("ceil".equals(name))
			fs = new FunctionCeil();
		else if ("floor".equals(name))
			fs = new FunctionFloor();
		else if ("format".equals(name))
			fs = new FunctionFormat();
		else if ("array".equals(name))
			fs = new FunctionArray();
		else if ("min".equals(name))
			fs = new FunctionMin();
		else if ("max".equals(name))
			fs = new FunctionMax();
		else if ("pow".equals(name))
			fs = new FunctionPow();
		else if ("size".equals(name))
			fs = new FunctionSize();
		else if ("trim".equals(name))
			fs = new FunctionTrim();
		else if ("empty".equals(name))
			fs = new FunctionEmpty();
		else if ("str_begin".equals(name))
			fs = new FunctionStrBegin();
		else if ("str_end".equals(name))
			fs = new FunctionStrEnd();
		else if ("str_index".equals(name))
			fs = new FunctionStrIndex();
		else if ("call".equals(name))
			fs = new FunctionCall();
		else if ("print".equals(name))
			fs = new FunctionPrint();
		else if ("fill".equals(name))
			fs = new FunctionFill();
		else if ("sum".equals(name))
			fs = new FunctionSum();
		else if ("val".equals(name))
			fs = new FunctionVal();
		else if ("find".equals(name))
			fs = new FunctionFind();
		else if ("str".equals(name))
			fs = new FunctionStr();
		else if ("str_split".equals(name))
			fs = new FunctionStrSplit();
		else if ("random".equals(name))
			fs = new FunctionRandom();
		
		int l = i + 1;
		int r = Syntax.findRightBrace(ws, l + 1);
		
		params = l + 1 == r ? null : Expression.expressionOf(ws.cut(l + 1, r));
	}

	public Object run(Factors factors)
	{
		if ("try".equals(name))
		{
			ArithmeticComma c = (ArithmeticComma)params;
			try
			{
				return c.left().run(factors);
			}
			catch (Exception e)
			{
				return c.right().run(factors);
			}
		}
		
		Function f = fs;
		
		Object v = factors.get(name);
		if (v != null && v instanceof Function)
			f = (Function)v;
		
		if (f == null)
		{
			if (v == null)
				throw new RuntimeException("未找到函数 - " + name);
			else
				throw new RuntimeException("该变量对应的值不是函数体 - " + name + " is " + v.getClass());
		}

		//用户自定义的函数，参数直接运算
		Object[] wrap = Wrap.arrayOf(params, factors);

//		Stack stack = new Stack(factors);
//		if (wrap != null)
//		{
//			stack.set("#0", new Integer(wrap.length));
//			for (int i = 0; i < wrap.length; i++)
//			{
//				stack.set("#" + (i + 1), wrap[i]);
//			}
//		}
		
		return f.run(wrap, factors);
	}

	public String toText(String space)
	{
		return name + "(" + (params == null ? "" : params.toText("")) + ")";
	}
}
