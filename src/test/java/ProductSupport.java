import com.xuzp.insuredxmltool.core.insurance.plan.Commodity;
import com.xuzp.insuredxmltool.core.insurance.plan.Plan;
import com.xuzp.insuredxmltool.core.insurance.plan.filter.StaticText;
import com.xuzp.insuredxmltool.core.insurance.plan.filter.liability.Liability;
import com.xuzp.insuredxmltool.core.insurance.plan.filter.table.Blank;
import com.xuzp.insuredxmltool.core.insurance.plan.filter.table.Table;
import com.xuzp.insuredxmltool.core.insurance.plan.filter.table.TableText;
import com.xuzp.insuredxmltool.core.insurance.plan.filter.tgraph.TGraphItem;
import com.xuzp.insuredxmltool.core.insurance.product.Company;
import com.xuzp.insuredxmltool.core.insurance.product.Insurance;
import com.xuzp.insuredxmltool.core.insurance.product.InsuranceMgr;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.coverage.Coverage;
import com.xuzp.insuredxmltool.core.insurance.product.attachment.coverage.CoverageParagraph;
import com.xuzp.insuredxmltool.core.insurance.product.rule.Rule;

import java.util.List;
import java.util.Map;


public class ProductSupport {
    /**
     * 初始化部分在启动的时候运行一次即可，返回结果保存至变量中，需要计算的时候直接拿来使用
     *
     * @return
     */
    public static Company load(String companyName) {

        //装载器
//        InsuranceMgr ins = InsuranceMgr.managerOf("/Users/yangzhilei/Desktop/建议书/demo-product/hq-product/product/", "insurance.xml");

        InsuranceMgr ins = InsuranceMgr.managerOf("D:\\myworkspace\\InsuredProductXmlGenerator\\src\\main\\resources\\product-fb", "insurance.xml");



        //读入数据
        Map data = ins.load();

        Company company = (Company) data.get(companyName);

        return company;
    }

    public static boolean checkRule(Plan plan) {
        boolean pass = true;

        List<Rule> r = plan.check();
        if (r != null) {
            for (Rule rule : r) {
                if (rule.getLevel() == Rule.LEVEL_FAIL)
                    pass = false;
                System.out.println(rule.getDesc());
            }
        }

        for (int i = 0; i < plan.size(); i++) {
            Commodity c = plan.getCommodity(i);

            r = c.check();
            if (r != null) {
                for (Rule rule : r) {
                    if (rule.getLevel() == Rule.LEVEL_FAIL)
                        pass = false;
                    System.out.println(c.getProduct().getName() + " - " + rule.getDesc().trim());
                }
            }
        }

        return pass;
    }

    static boolean isEmpty(Object object) {
        if (object instanceof String) {
            return "".equals(object) || object == null;
        }
        return object == null;
    }

    /**
     * 打印产品的利益表
     */
    public static String printDocument(Object elements) {
        StringBuilder stringBuilder = new StringBuilder();
        if (elements == null || !(elements instanceof List)) //该险种没有利益表
        {
            System.out.println("无\n");
            return "无<br/>";
        }

        List list = (List) elements;
        if (list.isEmpty())
            return "";

        for (int i = 0; i < list.size(); i++) {
            Object val = list.get(i);
            if (isEmpty(val)) {
                continue;
            }
            if (val instanceof Table) {
                System.out.println(stringOf((Table) list.get(i)));
                stringBuilder.append(stringOf((Table) list.get(i)));
            } else if (val instanceof TableText) {
                TableText tt = ((TableText) val);
                System.out.println(tt.getText() + "\n");
                stringBuilder.append(tt.getText() + "<br/>");
            } else if (val instanceof StaticText) {
                StaticText tt = ((StaticText) val);
                System.out.println(tt.getText() + "\n");
                stringBuilder.append(tt.getText() + "<br/>");
            }
        }

        return stringBuilder.toString();
    }

    private static String stringOf(Table table) {
        StringBuffer s = new StringBuffer("<table border=1>");

        int cols = table.getMaxCol();

        for (int j = 0; j < table.getTitleHeight(); j++) {
            s.append("<tr>");
            for (int k = 0; k < cols; k++) {
                Blank b = table.getTitleBlank(j, k);
                if (b == null || b.getText() == null)
                    continue;

                s.append("<td rowspan='" + b.getRowspan() + "' colspan='" + b.getColspan() + "'>");
                s.append(b.getText().replaceAll("[\n]", "<br/>"));
                s.append("</td>");
            }

            s.append("</tr>\n");
        }

        for (int j = 0; j < table.getMaxRow(); j++) {
            s.append("<tr>");
            for (int k = 0; k < cols; k++) {
                s.append("<td>");
                Blank blank = table.getBlank(j, k);
                if (blank != null && blank.getText() != null)
                    s.append(blank.getText().replaceAll("[\n]", "<br/>"));
                s.append("</td>");
            }
            s.append("</tr>\n");
        }

        s.append("</table>\n");

        return s.toString();
    }

    public static String printCoverage(Coverage c) {
        StringBuilder stringBuilder = new StringBuilder();
        if (c == null)
            return "";

        for (int i = 0; i < c.getParagraphCount(); i++) {
            CoverageParagraph p = c.getParagraph(i);
            System.out.println(p.getTitle());
            stringBuilder.append(p.getTitle());
            for (int j = 0; j < p.size(); j++) {
                if (p.getType(j) == CoverageParagraph.TABLE) {
                    System.out.println(stringOf((Table) p.getContent(j)));
                    stringBuilder.append(stringOf((Table) p.getContent(j)));
                } else {
                    System.out.println(p.getContent(j) + "\n");
                    stringBuilder.append(p.getContent(j) + "<br/>");
                }
            }
        }
        return stringBuilder.toString();
    }

    static String printSingleLiability(Liability c) {
        StringBuilder stringBuilder = new StringBuilder();
        
        System.out.println(c.getTitle() + "\n");
        stringBuilder.append(c.getTitle() + "<br/>");

        for (int i = 0; i < c.size(); i++) {
            Liability p = c.getParagraph(i);
            if (p.getTitle() != null) {
                System.out.println(p.getTitle() + "\n");
                stringBuilder.append(p.getTitle() + "<br/>");
            }
            if (p.getType() == Liability.TYPE_TABLE) {
                System.out.println(stringOf((Table) p.getContent()));
                stringBuilder.append(stringOf((Table) p.getContent()));
            } else if (p.getType() == Liability.TYPE_TEXT) {
                System.out.println(p.getContent() + "\n");
                stringBuilder.append(p.getContent() + "<br/>");
            } else if (p.getType() == Liability.TYPE_GROUP) {
                stringBuilder.append(printSingleLiability(p));
            }
        }
        return stringBuilder.toString();
    }

    public static String printLiability(Liability c) {
        if (c == null)
            return "--无--\n";
        return printSingleLiability(c);
    }

    public static void printTGraph(List<TGraphItem> list) {
        if (list == null) {
            System.out.println("无\n");
            return;
        }

        StringBuffer s = new StringBuffer("<table border=1>");

        for (TGraphItem item : list) {
            s.append("<tr>");
            s.append("<td colspan='" + (item.hasValue() ? 1 : 2) + "'>" + item.getText() + "</td>");
            if (item.hasValue())
                s.append("<td>" + item.getValue() + "</td>");
            s.append("</tr>");
        }

        s.append("</table>\n");
        System.out.println(s.toString());
    }

    public static String getCurrency(Insurance p) {
        if (p.getCurrency() == Insurance.CURRENCY_CNY)
            return "人民币";
        if (p.getCurrency() == Insurance.CURRENCY_TWD)
            return "新台币";
        if (p.getCurrency() == Insurance.CURRENCY_USD)
            return "美元";

        return "";
    }
}
