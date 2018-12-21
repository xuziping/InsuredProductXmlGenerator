package com.xuzp.insuredxmltool.enums;

import lombok.Getter;

/**
 * @author za-xuzhiping
 * @Date 2018/12/21
 * @Time 11:32
 */
@Getter
public enum InputTypeEnum implements IEnum {

    保费算保额("premium", new String[]{"保费算保额"}),
    保额算保费("amount", new String[]{"保额算保费"}),
    ;

    private String code;
    private String[] names;

    InputTypeEnum(String code, String[] names){
        this.code = code;
        this.names = names;
    }

    @Override
    public String getCodeByName(String name) {
        for(InputTypeEnum e : InputTypeEnum.values()){
            for(String n: e.names) {
                if(n.equals(name)) {
                    return e.code;
                }
            }
        }
        return "";
    }
}
