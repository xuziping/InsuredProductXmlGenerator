package com.xuzp.insuredxmltool.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author za-xuzhiping
 * @Date 2018/12/20
 * @Time 13:46
 */
@Getter
public enum PayEnum implements IEnum{

    single("single",new String[]{"一次交清"}),
    term_1("term_1",new String[]{"1年期", "1年"}),
    term_2("term_2",new String[]{"2年期", "2年"}),
    term_3("term_3",new String[]{"3年期", "3年"}),
    term_5("term_5",new String[]{"5年期", "5年"}),
    term_6("term_6",new String[]{"6年期", "6年"}),
    term_10("term_10",new String[]{"10年期", "10年"}),
    term_15("term_15",new String[]{"15年期", "15年"}),
    term_19("term_19",new String[]{"19年期", "19年"}),
    term_20("term_20",new String[]{"20年期", "20年"}),
    term_25("term_25",new String[]{"25年期", "25年"}),
    term_30("term_30",new String[]{"30年期", "30年"}),

    to_12("to_12",new String[]{"交至12岁"}),
    to_15("to_15",new String[]{"交至15岁"}),
    to_18("to_18",new String[]{"交至18岁"}),
    to_45("to_45",new String[]{"交至45岁"}),
    to_50("to_50",new String[]{"交至50岁"}),
    to_55("to_55",new String[]{"交至55岁"}),
    to_60("to_60",new String[]{"交至60岁"}),
    to_65("to_65",new String[]{"交至65岁"}),
    to_70("to_70",new String[]{"交至70岁"}),
    to_75("to_75",new String[]{"交至75岁"}),
    to_88("to_88",new String[]{"交至88岁"}),
    to_105("to_105",new String[]{"交至105岁"}),

    none("none",new String[]{"-"}),

    ;

    private String code;
    private String[] names;

    PayEnum(String code, String[] names){
        this.code = code;
        this.names = names;
    }

    @Override
    public String getCodeByName(String name) {
        for(PayEnum e : PayEnum.values()){
            if(Arrays.stream(e.names).filter(x->x.equals(name)).findAny().isPresent()){
                return e.code;
            }
        }
        return "";
    }
}
