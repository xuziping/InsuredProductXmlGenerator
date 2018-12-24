<?xml version="1.0" encoding="UTF-8" ?>
<declare>
    <product
            id="${内部标识}"
            corporation_id="${公司编码}"
            code="${保险编码}"
            name="${保险名称}"
            name_abbr="${保险简称}"
            unit="${计算单位}"
            type_code="${保险类别}"
            sequence="${保险次序}"
            sale_begin_date="2017-01-01"
            sale_end_date=""
    <#if 主附险标记=='rider'>
            is_main="no"
            is_rider="yes"
    </#if>
            quantity="1"
            input="${输入类目}"
    <#if 输入类目=='premium'>
            amount="try(Q*DS.VALUE[0][0],0)"
    <#elseif 输入类目=='amount'>
            premium="try(Q*DS.RATE[0][0],0)"
    </#if>
            last_modify_date="2017-01-01">

        <data>
            <item parser="dds" value="${内部标识}"/>
        </data>

        <param>
        <#if 交费方式列表??>
            <pay_freq>
                <#list 交费方式列表 as 交费方式>
                <item code="${交费方式}"/>
                </#list>
            </pay_freq>
        </#if>
        <#if 交费年期列表??>
            <pay>
                <#list 交费年期列表 as 交费年期>
                <item code="${交费年期}"/>
                </#list>
            </pay>
        </#if>
        <#if 保险期间列表??>
            <insure>
                <#list 保险期间列表 as 保险期间>
                <item code="${保险期间}"/>
                </#list>
            </insure>
        </#if>
        <#if 祝寿金领取年龄列表??>
            <birthday_payment_age>
                <#list 祝寿金领取年龄列表 as 祝寿金领取年龄>
                <item code="${祝寿金领取年龄}"/>
                </#list>
            </birthday_payment_age>
        </#if>
        </param>

        <input>
            <#if 缴费方式列表??>
            <item name="PAY_FREQ" label="交费方式" widget="select">buildOption('pay_freq')</item>
            </#if>
            <#if 交费年期列表??>
            <item name="PAY" label="交费年期" widget="select">buildOption('pay')</item>
            </#if>
            <#if 保险期间列表??>
            <item name="INSURE" label="保险期间" widget="select">buildOption('insure')</item>
            </#if>
            <#if 输入类目=='premium'>
            <item name="PREMIUM" label="首年保费" widget="input"></item>
            <#elseif 输入类目=='amount'>
            <item name="AMOUNT" label="基本保险金额" widget="input"></item>
            </#if>
            <#if 祝寿金领取年龄列表??>
            <item name="BIRTHDAY_PAYMENT_AGE" label="祝寿金领取年龄" widget="select">buildOption('birthday_payment_age')</item>
            </#if>
            <#if 输入类目=='premium'>
            <item name="AMOUNT" label="基本保险金额" widget="label">AMOUNT</item>
            <#elseif 输入类目=='amount'>
            <item name="PREMIUM" label="首年保费" widget="label">PREMIUM</item>
            </#if>
        </input>

    <#if 主附险标记=='rider'>
        <rider>
            <!-- 暂不支持 -->
        </rider>
    </#if>

        <init>
        <#if 输入类目=='premium'>
            <item name="Q" value="PREMIUM/UNIT"/>
        <#elseif 输入类目=='amount'>
            <item name="Q" value="AMOUNT/UNIT"/>
        </#if>
        </init>

        <interest>
            <var name="PRM" param="A1" formula="A1>=PAY_PERIOD?0:PREMIUM" /><!--年交保费-->
            <var name="PRM_T" param="A1" formula="(A1>0?IT.PRM_T(A1-1):0)+IT.PRM(A1)" /><!--累计保险费-->
            <var name="AMT" param="A1" formula="A1+1+AGE >= 18?AMOUNT:IT.PRM_T(A1)" /><!--身故保障-->
            <var name="CSV" param="A1" formula="try(Q * (DS.DATA[A1][0]), 0)" /><!--现价-->
            <var name="SVN" param="A1" formula="PREMIUM*PAY_PERIOD-IT.PRM_T(A1)" /><!--重大疾病或轻症疾病豁免保险费-->
        </interest>

        <attachment>
            <benefit_table filter="table" parser="table">
                <table>
                    <row type="title">
                        <blank row="1">'保单年度'</blank>
                        <blank row="1">'年龄'</blank>
                        <blank row="1">'年交保险费（元）'</blank>
                        <blank row="1">'累计保险费（元）'</blank>
                        <blank row="1">'重大疾病保障（元）'</blank>
                        <blank row="1">'身故保障（元）'</blank>
                        <blank row="1">'轻症疾病保障（元）'</blank>
                        <blank row="1">'现金价值（元）'</blank>
                        <blank row="1">'日开销（元）'</blank>
                    </row>
                    <loop from="0" to="INSURE_PERIOD-1" step="1" name="I">
                        <row>
                            <blank style="##0">I+1</blank>
                            <blank style="##0">AGE+I+1</blank>
                            <blank align="right" style="########0.00">round(IT.PRM(I),2)</blank>
                            <blank align="right" style="########0.00">round(IT.PRM_T(I),2)</blank>
                            <blank align="right" style="########0.00">round(AMOUNT)</blank>
                            <blank align="right" style="########0.00">round(IT.AMT(I),2)</blank>
                            <blank align="right" style="########0.00">round(AMOUNT*0.2)</blank>
                            <blank align="right" style="########0.00">round(IT.CSV(I))</blank>
                            <blank align="right" style="########0.00">round(IT.PRM(I)/365,2)</blank>
                        </row>
                    </loop>
                </table>
            </benefit_table>

            <#if 责任给付列表??>
            <!-- 责任给付 -->
            <liability filter="liability" parser="liability">
                <#list 责任给付列表 as 责任给付>
                <paragraph title="${责任给付.保障类别名称}">
                    <#if 责任给付.给付原因标准和限额??>
                    <table>
                        <#assign keys = 责任给付.给付原因标准和限额?keys>
                        <#list keys as key>
                        <row type="title">
                            <blank>${key}</blank>
                            <blank>${责任给付.给付原因标准和限额[key]}</blank>
                        </row>
                        </#list>
                    </table>
                    </#if>
                </paragraph>
                </#list>
            </liability>
            </#if>

            <#if 责任免除列表??>
            <!-- 责任免除  -->
            <responsibility filter="table" parser="table">
                <table>
                    <#list 责任免除列表 as 责任免除>
                    <row>
                        <blank>${责任免除}</blank>
                    </row>
                    </#list>
                </table>
            </responsibility>
            </#if>
        </attachment>

        <rule skip="rule_0002">
        <#if 最小投保人年龄??>
            <if condition="APPLICANT.AGE lt ${最小投保人年龄}"  type="customer">
                投保人未满${最小投保人年龄}岁，不可做为投保人。
            </if>
        </#if>
        <#if 最大投保人年龄??>
            <if condition="APPLICANT.AGE gt ${最大投保人年龄}"  type="customer">
                投保人大于${最大投保人年龄}岁，不可做为投保人。
            </if>
        </#if>
        <#if 最小被保人年龄??>
            <if condition="AGE lt ${最小被保人年龄}" type="customer">
                被保人未满${最小被保人年龄}岁，不可做为被保人。
            </if>
        </#if>
        <#if 最大被保人年龄??>
            <if condition="AGE gt ${最大被保人年龄}" type="customer">
                被保人大于${最大被保人年龄}岁，不可做为被保人。
            </if>
        </#if>
        <#if 限制职业??>
            <if condition="OCCUPATION_CATEGORY>4 or OCCUPATION_CATEGORY lt 1" type="customer">
                仅限职业风险等级为1-4级的客户投保。
            </if>
        </#if>
        </rule>
    </product>
</declare>
