import com.xuzp.insuredxmltool.core.insurance.plan.Commodity;
import com.xuzp.insuredxmltool.core.insurance.plan.InsuranceCustomer;
import com.xuzp.insuredxmltool.core.insurance.plan.Plan;
import com.xuzp.insuredxmltool.core.insurance.plan.filter.liability.Liability;
import com.xuzp.insuredxmltool.core.insurance.product.Company;
import com.xuzp.insuredxmltool.core.insurance.product.Insurance;
import com.xuzp.insuredxmltool.core.insurance.product.Purchase;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.coverage.Coverage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author za-xuzhiping
 * @Date 2019/4/8
 * @Time 20:18
 */
public class CheckXML extends ProductSupport{

    public static void main(String[] arg) throws Exception {

        Company hq = load("cardif");

        System.out.println("--- 可选的产品列表 ---");
        List<Insurance> l = hq.getProductList();
        for (Insurance p : l)
            System.out.println("<" + p.getId() + "> " + (p.isMain() ? "<主>" : "<附>") + " " + p.getName());
        System.out.println();

        //新建一个建议书对象
        Proposal proposal = new Proposal();

        //投保人
        InsuranceCustomer applicant = new Customer("1996-02-11", InsuranceCustomer.GENDER_MALE);
        proposal.setApplicant(applicant);


        //被保险人
        InsuranceCustomer insurant = new Customer("1989-01-01", InsuranceCustomer.GENDER_MALE,"","01");


        //投保计划
        Plan plan = proposal.newPlan(insurant);
////      //被保险人
//        InsuranceCustomer insurant1 = new Customer("1992-01-03", InsuranceCustomer.GENDER_MALE,"","03");
//        plan.addInsurant(insurant1);
        //被保险人
//        InsuranceCustomer insurant2 = new Customer("1999-01-03", InsuranceCustomer.GENDER_MALE);
//        plan.addInsurant(insurant2);
//        plan.getInsurants();
        System.out.println(plan.getFactors().get("INSURANTS"));

        //获取一个产品，添加到计划
        Commodity main;

        main = plan.newCommodity(hq.getProduct("SITC"));
//        main.setAdditional("TOTAL_PREMIUM", 10000);
        main.setAmount(1000000);
//        main.setInput("occupation_level", "1");
        main.setInput("pay_freq", "single");

//        main.setInput("medicare", "no");
//        main.setInput("insurance", "insurance_50000");
//        main.setInput("assurance_program", "plan_five");
        main.setInput("insure", "term_20");
        main.setInput("pay", "term_1");
//        main.setValue("loan_amount", 1000000);
        main.setInput("loan_grace_period", "3");
        main.setInput("loan_term", "term_20");
        main.setInput("pay_method", "loan");

        double[][] array = new double[3][];
        array[0] = new double[]{1,12,0.018,0.018};
        array[1] = new double[]{2,24,0.02,0.02};
        array[2] = new double[]{25,240,0.023,0.023};
        main.setValue("rate_info",array);
        main.setValue("loan_amount", 1000000);
        main.setValue("loan_term", 20);
        main.setValue("loan_grace_period", 3);
//        main.setInput("birthday_payment_age", "75");
//        main.setInput("get_annuity_age", "55");

//        Commodity bind = plan.getCommodityByProductId("SH000009");

//        main.setInput("plan_select", "1");
//        main.setInput("medicare", "yes");
//        Commodity a =  plan.newCommodity(hq.getProduct("SH000017"),insurant1.getId());
//        main.setPremium(100000);

//        main.setAdditional("ACCIDENTAL_DEATH_AND_DISMEMBERMENT", 10000);
//        main.setAdditional("SUDDEN_ILLNESS_DEATH", 10000);
//        main.setAdditional("TERRORISM_AND_RIOTS_ACCIDENTAL_INJURY", 10000);
//        Commodity bind = plan.getCommodityByProductId("SH000009");
//        bind.setPremium(0);
//        bind.setAdditional("TOTAL_PREMIUM", 10000);
//        bind.setAmount(10000);
//        Object a = FormulaUtil.formulaOf("PARENT").run(bind.getFactors());
//        main.setInput("pay_freq", "year");
//        main.setInput("pay", "term_3");
//        main.setInput("insure", "month_2");
//        main.setAdditional("TEST_A", "0");
//        a.setInput("pay", "term_5");
//        Insurance rider = l.get(16);
//        main.setInput("hospital_package_select", "0");
//        main.setInput("clinic_package_select", "0");
//        main.setInput("hospital_deductible", "0");
//        main.setInput("clinic_deductible", "0");
//        main.setInput("clinic_deductible_second", "0");
//        main.setInput("clinic_deductible_second_type", "0");
//        main.setInput("since_the_pay_scale", "0");
//        main.setInput("maternal", "0");
//        main.setInput("delivery_and_newborn_protection", "0");
//        a.setInput("maternal", "-1");
//        a.setInput("delivery_and_newborn_protection", "-1");
//        main.setInput("place_of_birth", "0");
//        main.setInput("social_ins", "0");
//        main.setInput("plan_select", "1");
//        main.setInput("phase_arr", "2");
//        main.setInput("type_arr", "1");
//        main.setAdditional("BC_DRUGS", "0");
//        main.setInput("deductible", "1");
//        a.setInput("deductible", "3");
//
//        Commodity riderC = plan.newCommodity(main,rider);

//        System.out.println(riderC.getAmount());
//
//        System.out.println(riderC.getPremium());

//        CommodityDuty commodityDuty = new CommodityDuty(main.getProduct().getDutyList(),main.getFactors());
//
//        for (int i = 0;i < commodityDuty.size();i++){
//            System.out.println(commodityDuty.get(i));
//        }

//        main.setInput("pay_freq", "year");
//        main.setInput("pay", "term_30");
//        main.setAmount(50000);
//        main.setPremium(1000);

//        plan.newCommodity()

//        RuleUtil.check()

//        Formula formula = FormulaUtil.getFormulaEngine().formulaOf("DS.RATE[0,0]");
//        System.out.println("=======" + formula.run(main.getFactors()));


//        FormulaUtil.getFormulaEngine().formulaOf("DS.LEVEL[0][0]");
        if (checkRule(plan)) //检查投保规则
            print(plan);

        System.out.println("保额："+main.getAmount());
        System.out.println("保费："+main.getPremium());
//        System.out.println("保额："+riderC.getAmount());
//        System.out.println("保费："+riderC.getPremium());
    }

    /**
     * 输出结果
     *
     * @param plan
     */
    public static void print(Plan plan) {
        StringBuilder builder = new StringBuilder();
        builder.append("--- 输出结果 ---\n");

        for (int i = 0; i < plan.size(); i++) {
            Commodity c = plan.getCommodity(i);

            builder.append("产品：" + c.getProduct().getName());

            //保额/档次/份数
            int mode = c.getProduct().getInputMode();
            if (mode == Purchase.AMOUNT)
                builder.append("，保额/档次/份数：" + c.getAmount() + "元");
            else if (mode == Purchase.RANK)
                builder.append("，保额/档次/份数：" + c.getRank().getDesc());
            else if (mode == Purchase.QUANTITY)
                builder.append("，保额/档次/份数：" + c.getQuantity() + "份");

            if (c.getInput("pay_freq") != null) {
                builder.append("，交费频次：" + c.getInput("pay_freq").getDesc());
            }

            if (c.getInsure() != null)
                builder.append("，保险期间：" + c.getInsure().getDesc());
            if (c.getPay() != null)
                builder.append("，交费年期：" + c.getPay().getDesc());

            //保费
            builder.append("，保费：" + c.getPremium() + "元" + "\n");
        }

        System.out.println(builder.toString());

        builder.append("<br/>");
        for (int i = 0; i < plan.size(); i++) {
            Commodity c = plan.getCommodity(i);

            //打印该产品的利益表
            System.out.println("--- " + c.getProduct().getName() + "的利益表 ---" + "\n");
            builder.append("--- " + c.getProduct().getName() + "的利益表 ---" + "<br/>");
            builder.append(printDocument(c.format("benefit_table")) + "\n");
//
//            //打印该产品的利益摘要
//            System.out.println("--- " + c.getProduct().getName() + "的利益摘要 ---" + "\n");
//            builder.append("--- " + c.getProduct().getName() + "的利益摘要 ---" + "<br/>");
//            builder.append(printDocument(c.format("summary")) + "\n");
//
            //打印该产品的保险责任
            System.out.println("--- " + c.getProduct().getName() + "的保险责任 ---" + "\n");
            builder.append("--- " + c.getProduct().getName() + "的保险责任 ---" + "<br/>");
            builder.append(printCoverage((Coverage) c.format("coverage")) + "\n");

            //打印该产品的保险利益
            System.out.println("--- " + c.getProduct().getName() + "的保险利益 ---" + "\n");
            builder.append("--- " + c.getProduct().getName() + "的保险利益 ---" + "<br/>");
            builder.append(printLiability((Liability) c.format("liability")) + "\n");
        }

        outHtmlFile(builder, "D:/test.html");
    }

    static void outHtmlFile(StringBuilder builder, String path) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(path);
            outputStream.write(builder.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
