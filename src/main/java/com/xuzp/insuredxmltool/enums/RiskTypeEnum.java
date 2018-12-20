package com.xuzp.insuredxmltool.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author za-xuzhiping
 * @Date 2018/12/20
 * @Time 16:18
 */
@Getter
public enum RiskTypeEnum implements IEnum{

    寿险("life", new String[]{"寿险"}),
    投资("invest", new String[]{"投资", "投资险"}),
    重疾("thunder", new String[]{"重疾", "重疾险"}),
    医疗("medical", new String[]{"医疗", "医疗险"}),
    健康("health", new String[]{"健康", "健康险"}),
    意外("accident", new String[]{"意外", "意外险"}),
    万能("universal", new String[]{"万能", "万能险"}),
    投连("linked", new String[]{"投连", "投连险"}),
    分红("dividend", new String[]{"分红", "分红险"}),
    豁免("exempt", new String[]{"豁免", "豁免险"}),
    ;

    private String code;
    private String[] names;

    RiskTypeEnum(String code, String[] names){
        this.code = code;
        this.names = names;
    }

    @Override
    public String getCodeByName(String name) {
        for(RiskTypeEnum e : RiskTypeEnum.values()){
            if(Arrays.stream(e.names).filter(x->x.contains(name)).findFirst().isPresent()){
                return e.code;
            }
        }
        return "";
    }
}
