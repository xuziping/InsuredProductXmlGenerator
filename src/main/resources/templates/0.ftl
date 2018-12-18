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
            quantity="1"
            input="${输入类目}"
    <#if 输入类目=='PREMIUM'>
            amount="try(Q*DS.VALUE[0][0],0)"
    <#elseif 输入类目=='AMOUNT'>
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
            <#if 输入类目=='PREMIUM'>
            <item name="PREMIUM" label="首年保费" widget="input"></item>
            <#elseif 输入类目=='AMOUNT'>
            <item name="AMOUNT" label="基本保险金额" widget="input"></item>
            </#if>
            <#if 祝寿金领取年龄列表??>
            <item name="BIRTHDAY_PAYMENT_AGE" label="祝寿金领取年龄" widget="select">buildOption('birthday_payment_age')</item>
            </#if>
            <#if 输入类目=='PREMIUM'>
            <item name="AMOUNT" label="基本保险金额" widget="label">AMOUNT</item>
            <#elseif 输入类目=='AMOUNT'>
            <item name="PREMIUM" label="首年保费" widget="label">PREMIUM</item>
            </#if>
        </input>

        <rider>
            <!-- 暂不支持 -->
        </rider>

        <init>
        <#if 输入类目=='PREMIUM'>
            <item name="Q" value="PREMIUM/UNIT"/>
        <#elseif 输入类目=='AMOUNT'>
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


            <liability filter="liability" parser="liability">
                <paragraph title="1.身故或全残保险金">
                    <table>
                        <row type="title">
                            <blank>'给付原因及标准'</blank>
                            <blank>'给付限额'</blank>
                        </row>
                        <row>
                            <blank>'本合同被保险人于本合同生效或最后复效之日起 90 日内（含90日当日）因疾病导致身故或全残，我们按本合同已交保险费给付身故或全残保险金，本合同终止。'</blank>
                            <blank>round(PREMIUM,2)+'元'</blank>
                        </row>
                        <row>
                            <blank>'合同生效或最后复效之日起 90 日后因疾病导致身故或全残，我们按本合同保险金额给付身故或全残保险金，本合同终止。'</blank>
                            <blank>round(AMOUNT,2)+'元'</blank>
                        </row>
                    </table>
                </paragraph>
            </liability>
        </attachment>

        <rule skip="rule_0002">
            <if condition="APPLICANT.AGE lt 18">
                投保人未满18岁，不可做为投保人。
            </if>
            <if condition="APPLICANT.AGE gt 70">
                投保人年龄不大于70周岁
            </if>
            <if condition="AGE lt 18 or AGE >65" type="customer">
                投保年龄：18-65周岁。
            </if>
            <if condition="(AGE lt 18 or AGE >50) and INSURE_PERIOD == 20" type="customer">
                保险期间:20年期，投保年龄:18-50岁。
            </if>
            <if condition="(AGE lt 18 or AGE >40) and INSURE_PERIOD == 30" type="customer">
                保险期间:30年期，投保年龄:18-40岁。
            </if>
            <if condition="(AGE lt 18 or AGE >40) and PAY_PERIOD == 20 and INSURE_PERIOD == 60" type="customer">
                保险期间:至60周岁,交费期间:20年交,投保年龄:18-40岁。
            </if>
            <if condition="(AGE lt 18 or AGE >50) and PAY_PERIOD != 20 and INSURE_PERIOD == 60" type="customer">
                保险期间:至60周岁,交费期间:趸交、3年交、5年交、10年交,投保年龄:18-50岁。
            </if>
            <if condition="(AGE lt 18 or AGE >55) and PAY_PERIOD != 20 and INSURE_PERIOD == 65" type="customer">
                保险期间:至65周岁,交费期间:趸交、3年交、5年交、10年交,投保年龄:18-55岁。
            </if>
            <if condition="(AGE lt 18 or AGE >45) and PAY_PERIOD == 20 and INSURE_PERIOD == 65" type="customer">
                保险期间:至65周岁,交费期间:20年交,投保年龄:18-45岁。
            </if>
            <if condition="(AGE lt 18 or AGE >60) and PAY_PERIOD != 20 and INSURE_PERIOD == 70" type="customer">
                保险期间:至70周岁,交费期间:趸交、3年交、5年交、10年交,投保年龄:18-60岁。
            </if>
            <if condition="(AGE lt 18 or AGE >50) and PAY_PERIOD == 20 and INSURE_PERIOD == 70" type="customer">
                保险期间:至70周岁,交费期间:20年交,投保年龄:18-50岁。
            </if>
            <if condition="AMOUNT lt 300000 or AMOUNT % 10000>0">
                最低保额：≥30万元，超出部分须为1万元的整数倍。
            </if>
            <!--<if condition="OCCUPATION_CATEGORY>4 or OCCUPATION_CATEGORY lt 1" type="customer">-->
            <!--仅限职业风险等级为1-4级的客户投保。-->
            <!--</if>-->
        </rule>
    </product>
</declare>
