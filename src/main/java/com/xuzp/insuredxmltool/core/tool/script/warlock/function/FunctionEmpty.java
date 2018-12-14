package com.xuzp.insuredxmltool.core.tool.script.warlock.function;

import com.xuzp.insuredxmltool.core.tool.formula.Factors;
import com.xuzp.insuredxmltool.core.tool.formula.Function;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FunctionEmpty implements Function {
    public Object run(Object[] v, Factors factors) {
        if (v.length == 1) {
            int num = 0;

            Object r = v[0];
            if (r == null)
                num = 0;
            else if (r instanceof Object[]) //Object[]对于Object[][]也是true，但是double[]对于double[][]不是
                num = ((Object[]) r).length;
            else if (r instanceof double[])
                num = ((double[]) r).length;
            else if (r instanceof double[][])
                num = ((double[][]) r).length;
            else if (r instanceof int[])
                num = ((int[]) r).length;
            else if (r instanceof int[][])
                num = ((int[][]) r).length;
            else if (r instanceof Collection)//优化不仅仅是list
                num = ((List) r).size();
            else if (r instanceof String)
                num = ((String) r).length();
            else if (r instanceof Map)//增加对map的判断
                num = ((Map)r).size();
            return num == 0;
        }

        throw new RuntimeException("错误的empty运算");
    }

}
