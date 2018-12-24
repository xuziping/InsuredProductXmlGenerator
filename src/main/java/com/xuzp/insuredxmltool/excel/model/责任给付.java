package com.xuzp.insuredxmltool.excel.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author za-xuzhiping
 * @Date 2018/12/18
 * @Time 11:31
 */
@Data
@ToString
public class 责任给付 implements 信息 {

    public List<责任信息> 责任列表;
}
