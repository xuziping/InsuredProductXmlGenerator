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
public class 险种信息 implements 信息{

    public String 险种编码;
    public String 险种版本;
    public String 保险公司;
    public String 险种名称;
    public String 险种简称;
    public String 险种性质;
    public String 主附险标记;
    public String 险种类别;
    public String 险种页签;
    public String 产品类别;
    public String 设计类型;
    public String 犹豫期天数;
    public String 等待期;
    public String 开售日期;
    public String 停售日期;
}
