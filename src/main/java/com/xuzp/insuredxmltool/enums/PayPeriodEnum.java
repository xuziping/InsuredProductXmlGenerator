package com.xuzp.insuredxmltool.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author za-xuzhiping
 * @Date 2018/12/18
 * @Time 17:51
 */
@Getter
public enum PayPeriodEnum implements IEnum {

    趸交("single", new String[]{"趸交"}),
    年交("year", new String[]{"年交"}),
    季交("season", new String[]{"季交"}),
    月交("month", new String[]{"月交"}),
    半年交("half_year", new String[]{"半年交"}),
    ;

    private String code;
    private String[] names;

    PayPeriodEnum(String code, String[] names){
        this.code = code;
        this.names = names;
    }

    @Override
    public String getCodeByName(String name) {
        for(PayPeriodEnum e : PayPeriodEnum.values()){
            if(Arrays.stream(e.names).filter(x->x.equals(name)).findAny().isPresent()){
                return e.code;
            }
        }
        return "";
    }

}
