package com.xuzp.insuredxmltool.constants;

/**
 * @author za-xuzhiping
 * @Date 2018/12/12
 * @Time 11:08
 */
public interface ExcelConstant {

    interface Excel标签{
        String 总表说明 = "总表说明";
        String 险种信息 = "险种信息";
        String 投保规则 = "投保规则";
        String 险种搭配 = "险种搭配";
        String 利益演示 = "利益演示";
        String 示例1 = "示例1";
        String 产品UI图片 = "产品UI图片";
    }

    interface 险种信息字段{
        String 险种编码 = "险种编码";
        String 险种版本 = "险种版本";
        String 保险公司 = "保险公司";
        String 险种名称 = "险种名称";
        String 险种简称 = "险种简称";
        String 险种性质 = "险种性质";
        String 主附险标记 = "主附险标记";
        String 险种类别 = "险种类别";
        String 险种页签 = "险种页签";
        String 产品类别 = "产品类别";
        String 设计类型 = "设计类型";
        String 犹豫期天数 = "犹豫期天数";
        String 等待期 = "等待期";
        String 开售日期 = "开售日期";
        String 停售日期 = "停售日期";
    }

    interface 投保规则字段{
        String 限制职业 = "限制职业";
        String 投保要求 = "投保要求";
        String 最小投保人年龄 = "最小投保人年龄";
        String 最大投保人年龄 = "最大投保人年龄";
        String 最小被保人年龄 = "最小被保人年龄";
        String 最大被保人年龄 = "最大被保人年龄";
        String 保费保额 = "保费/保额";
        String 最低保额 = "最低保额";
        String 最高保额 = "最高保额";
        String 最低保费 = "最低保费";
        String 最高保费 = "最高保费";
        String 交费方式 = "交费方式";
        String 交费年期 = "交费年期";
        String 保险期间 = "保险期间";


        String AMOUNT = "保额算保费";
        String PREMIUM = "保费算保额";
    }


}
