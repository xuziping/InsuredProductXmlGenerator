package com.xuzp.insuredxmltool.enums;

/**
 * @author za-xuzhiping
 * @Date 2018/12/20
 * @Time 18:30
 */
public interface IEnum<T>{
    String getCode();
//    String[] getNames();
    String getCodeByName(String name);
}
