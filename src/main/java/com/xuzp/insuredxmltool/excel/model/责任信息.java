package com.xuzp.insuredxmltool.excel.model;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * @author za-xuzhiping
 * @Date 2018/12/18
 * @Time 11:30
 */
@Data
@ToString
public class 责任信息 {

    public String 保障类别名称;

    public Map<String, String> 给付原因标准和限额;
}
