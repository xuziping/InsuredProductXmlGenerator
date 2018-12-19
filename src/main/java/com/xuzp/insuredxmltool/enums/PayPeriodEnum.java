package com.xuzp.insuredxmltool.enums;

/**
 * @author za-xuzhiping
 * @Date 2018/12/18
 * @Time 17:51
 */
public enum PayPeriodEnum {

    趸交("single", "趸交"),
    年交("year", "年交"),
    季交("season", "季交"),
    月交("month", "月交"),
    半年交("half_year", "半年交"),
    ;

    private String code;
    private String name;

    PayPeriodEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getCode(String name) {
        for(PayPeriodEnum e : PayPeriodEnum.values()){
            if(e.name.indexOf(name)!=-1){
                return e.code;
            }
        }
        return "";
    }

}
