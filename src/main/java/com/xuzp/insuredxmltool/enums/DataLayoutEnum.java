package com.xuzp.insuredxmltool.enums;

import lombok.Getter;

/**
 * @author za-xuzhiping
 * @Date 2019/1/25
 * @Time 17:22
 */
@Getter
public enum DataLayoutEnum implements IEnum {

    无("无", new String[]{"无","不存在","没有"}),
    横向("横向", new String[]{"横向"}),
    纵向("纵向", new String[]{"纵向"}),
    ;

    private String code;
    private String[] names;

    DataLayoutEnum(String code, String[] names){
        this.code = code;
        this.names = names;
    }

    @Override
    public String getCodeByName(String name) {
        for(DataLayoutEnum e : DataLayoutEnum.values()){
            for(String n: e.names) {
                if(n.equals(name)) {
                    return e.code;
                }
            }
        }
        return "";
    }
}
