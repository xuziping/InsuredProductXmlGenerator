package com.xuzp.insuredxmltool.excel.model;

import lombok.Data;
import lombok.ToString;

/**
 * @author za-xuzhiping
 * @Date 2019/1/18
 * @Time 17:32
 */
@Data
@ToString
public class 万能险配置 implements 信息 {

    public String 低档结算利率;
    public String 中档结算利率;
    public String 高档结算利率;
    public String 趸交初始费用;
    public String 追加初始费用;
    public String 转入初始费用;
    public String 退保费用;
    public String 保单管理费;
    public String 持续奖金;
    public String 年金;
    public String 年金首次领取日;
    public String 现金价值;
    public String 提示信息;
}
