package com.xuzp.insuredxmltool.excel.model;

import lombok.Data;
import lombok.ToString;

/**
 * @author za-xuzhiping
 * @Date 2018/12/12
 * @Time 16:20
 */
@Data
@ToString
public class 投保规则 implements 信息{

    public String 限制职业;
    public String 投保要求;
    public String 最小投保人年龄;
    public String 最大投保人年龄;
    public String 最小被保人年龄;
    public String 最大被保人年龄;
    public String 保费保额;
    public String 最低保额;
    public String 最高保额;
    public String 最低保费;
    public String 最高保费;
    public String 交费方式;
    public String 交费年期;
    public String 保险期间;
}
