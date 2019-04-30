package com.xuzp.insuredxmltool.core.tool.script.warlock.statement;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.script.Stack;
import com.xuzp.insuredxmltool.core.tool.script.SyntaxException;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Code;
import com.xuzp.insuredxmltool.core.tool.script.warlock.Reference;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Expression;
import com.xuzp.insuredxmltool.core.tool.script.warlock.analyse.Words;

import java.util.Map;

public class ArithmeticPointKey implements Code, Reference
{
	Code l;
	String key;
	
	public ArithmeticPointKey(Words ws, int i)
	{
		l = Expression.expressionOf(ws.cut(0, i));
		
		if (ws.getType(i + 1) != Words.KEY)
			throw new SyntaxException("POINT-KEY运算右侧没有找到KEY");
		
		key = ws.getWord(i + 1);
	}

	/**
	 * 如果结果是Formula，这里不能直接二次计算
	 * 
	 * 对于y.xxx[a]，y.xxx如果是Formula，本应是一个函数指针。
	 * 执行了Formula返回后数组部分会识别错误，抛出异常。
	 */
	public Object run(Factors factors)
	{
		Object v = l.run(factors);

		if (v == null)
			throw new RuntimeException("空指针");
		
		if (v instanceof Factors)
			return ((Factors)v).get(key);
		if (v instanceof Map)
			return ((Map)v).get(key);
		
		throw new RuntimeException("POINT-KEY运算要求左侧值为参数表或Map类型");
	}

	public void let(Factors factors, Object value)
	{
		Object v = l.run(factors);
		if (v instanceof Stack)
			((Stack)v).set(key, value);
		else if (v instanceof Map)
			((Map)v).put(key, value);
		else
			throw new RuntimeException("赋值时，被赋值一方的POINT运算的左侧不是有效类型");
	}

	public String toText(String space)
	{
		return l.toText("") + "." + key;
	}
}
