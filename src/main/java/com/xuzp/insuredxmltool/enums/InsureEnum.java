package com.xuzp.insuredxmltool.enums;

import lombok.Getter;

/**
 * @author za-xuzhiping
 * @Date 2018/12/20
 * @Time 14:09
 */
@Getter
public enum InsureEnum implements IEnum{

    month_1("month_1",new String[]{"1个月"}),
    month_2("month_2",new String[]{"2个月"}),
    month_3("month_3",new String[]{"3个月"}),
    month_4("month_4",new String[]{"4个月"}),
    month_5("month_5",new String[]{"5个月"}),
    month_6("month_6",new String[]{"6个月"}),
    month_7("month_7",new String[]{"7个月"}),
    month_8("month_8",new String[]{"8个月"}),
    month_9("month_9",new String[]{"9个月"}),
    month_10("month_10",new String[]{"10个月"}),
    month_11("month_11",new String[]{"11个月"}),
    month_12("month_12",new String[]{"12个月"}),

    term_1("term_1",new String[]{"1年期", "1年", "一年期"}),
    term_5("term_5",new String[]{"5年期", "5年", "五年期"}),
    term_10("term_10",new String[]{"10年期", "10年", "十年期"}),
    term_15("term_15",new String[]{"15年期", "15年"}),
    term_20("term_20",new String[]{"20年期", "20年"}),
    term_25("term_25",new String[]{"25年期", "25年"}),
    term_30("term_30",new String[]{"30年期", "30年"}),

    to_14("to_14",new String[]{"保至14岁"}),
    to_17("to_17",new String[]{"保至17岁"}),
    to_21("to_21",new String[]{"保至21岁"}),
    to_25("to_25",new String[]{"保至25岁"}),
    to_28("to_28",new String[]{"保至28岁"}),
    to_40("to_40",new String[]{"保至40岁"}),
    to_45("to_45",new String[]{"保至45岁"}),
    to_50("to_50",new String[]{"保至50岁"}),
    to_55("to_55",new String[]{"保至55岁"}),
    to_60("to_60",new String[]{"保至60岁"}),
    to_65("to_65",new String[]{"保至65岁"}),
    to_70("to_70",new String[]{"保至70岁"}),
    to_75("to_75",new String[]{"保至75岁"}),
    to_80("to_80",new String[]{"保至80岁"}),
    to_85("to_85",new String[]{"保至85岁"}),
    to_88("to_88",new String[]{"保至88岁"}),
    to_100("to_100",new String[]{"保至100岁"}),
    to_105("to_105",new String[]{"保至105岁"}),
    to_full("to_full",new String[]{"终身", "终生"}),
    none("none",new String[]{"-"}),
    ;

    private String code;
    private String[] names;

    InsureEnum(String code, String[] names){
        this.code = code;
        this.names = names;
    }

    @Override
    public String getCodeByName(String name) {
        for(InsureEnum e : InsureEnum.values()){
            for(String n: e.names) {
                if(n.equals(name)) {
                    return e.code;
                }
            }
        }
        return "";
    }
}
