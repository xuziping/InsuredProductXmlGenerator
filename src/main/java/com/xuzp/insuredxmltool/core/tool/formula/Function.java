package com.xuzp.insuredxmltool.core.tool.formula;

import java.io.Serializable;

/**
 * <p>2014-06-14 李新豪</p>
 * <p>
 * 这个接口一般是用作自定义函数。<br>
 * 当编译器在公式中发现一个函数写法，但是又找不到对应的函数时，就会把这个函数标记为自定义函数。<br>
 * 运算时，从参数表中get(函数名)，如果找不到或者类型不是FunctionUser就抛错，找到就把这个FunctionUser对象作为函
 * 数计算，指针函数的做法。<br>
 * </p>
 * <p>
 * 同时这个接口由于不需要设定函数名（函数名和函数体通过factors关联），也可以用作仿数组函数的计算<br>
 * 如IT.ABC[X,Y]，写法类似数组，其实和前面一种情况是基本相同的，更像是指针函数。<br>
 * 只不过上一种是通过编译器识别为函数（小括号）。而这种由于前面有point运算，如果用小括号，会被识别为对象的方法。
 * 这里识别为数组，运算时仍然是指针函数。<br>
 * 之所以支持这种写法，是由于金融计算中经常遇到一个年期数组，其值是通过递归计算得出，定义为变量比定义大量方法要合
 * 适的多，同时数组也可以比较容易的保存每一次运算结果，计算后面的年期就不需要在递归计算到最前面。<br>
 * </p>
 * 
 * @author lerrain
 * 
 *
 */
public interface Function extends Serializable
{
	public Object run(Object[] v, Factors p);
}
