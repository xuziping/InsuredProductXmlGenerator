package com.xuzp.insuredxmltool.enums;

import lombok.Getter;

/**
 * @author za-xuzhiping
 * @Date 2018/12/21
 * @Time 14:22
 */
@Getter
public enum MainRiderEnum implements IEnum{

    主险("main", new String[]{"主险"}),
    附险("rider", new String[]{"副险", "附险"}),
    ;

    private String code;
    private String[] names;

    MainRiderEnum(String code, String[] names){
        this.code = code;
        this.names = names;
    }

    @Override
    public String getCodeByName(String name) {
        for(MainRiderEnum e : MainRiderEnum.values()){
            for(String n: e.names) {
                if(n.equals(name)) {
                    return e.code;
                }
            }
        }
        return "";
    }
}


