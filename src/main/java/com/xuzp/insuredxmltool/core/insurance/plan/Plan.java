package com.xuzp.insuredxmltool.core.insurance.plan;

/**
 * 投保计划对象
 * - 建立：2010/07/14 - 李新豪
 * <p>
 * 投保计划对应于投保单，虽然存在一个投保计划对应多个投保单的情况，但是一般情形下投保计划和投保单
 * 是一一对应的，而且一对多的情况下，多份投保单也可以合并为一份投保单，所以可以将一个投保计划理解为一
 * 个投保单。
 * 由于新增一个险种时可能会连带增加其他险种，所以不能直接使用类似ｎｅｗ　Ｐｒｏｄｕｃｔ（．．．）
 * 的方法来新增产品，因此在投保计划对象中加入了专门为计划新增产品的方法，共如下３个：
 * １．传入产品定义，按照默认参数新增产品。
 * ２．传入虚拟产品对象，按照虚拟产品对象的参数设置新增产品。
 * ３．传入产品组合定义，按照组合中的描述新增产品。
 *
 * @author 李新豪
 * <p>
 * 修改历史：
 * <p>
 * 增加了设置附加信息的方法(setAdditional/getAdditional)
 * - 李新豪 2011/07/07
 * 主险可以绑定投保自己的附加险，选择主险时自动带出
 * - 李新豪 2011/10/13
 */

import com.xuzp.insuredxmltool.core.insurance.plan.filter.FilterNotFoundException;
import com.xuzp.insuredxmltool.core.insurance.plan.filter.FilterPlan;
import com.xuzp.insuredxmltool.core.insurance.product.Company;
import com.xuzp.insuredxmltool.core.insurance.product.InitValue;
import com.xuzp.insuredxmltool.core.insurance.product.Insurance;
import com.xuzp.insuredxmltool.core.insurance.product.rule.RuleUtil;
import com.xuzp.insuredxmltool.core.tool.formula.Formula;
import com.xuzp.insuredxmltool.core.tool.formula.Value;

import java.io.Serializable;
import java.util.*;

public class Plan implements Serializable {
    private static final long serialVersionUID = 1L;

    String id;
    String vendor;

    String prefix;
    int sequence;

    InsuranceCustomer applicant;
    InsuranceCustomer insurant;

//	Map otherInsurants, insurantsRelative;

    Date insureTime;

    SequenceList commodityList = new SequenceList();

    PlanFactors factors;

    String applicantId;

    Map value; //这是个用户设置的，需要保存
    Map additional; //这个是自动带入的，不需要保存

    String creator;

    public Plan(InsuranceCustomer applicant, InsuranceCustomer insurant) {
        this.insurant = insurant;
        this.applicant = applicant;

        this.insureTime = new Date();
        this.prefix = new Date().getTime() + "_" + new Random().nextInt(10000);

//		factors = new PlanFactors(this); 考虑投被保人更换的问题，这里的factors经常需要变化。
    }

    public InsuranceCustomer getInsurant() {
        return insurant;
    }

    public InsuranceCustomer getApplicant() {
        return applicant;
    }

    /**
     * 添加次要的被保险人
     * 由于内部使用被保险人id定位关联，因此要求添加被保险人的id不能为空
     *
     * @param relative
     * @param otherInsurant
     */
    public void addInsurant(String relative, InsuranceCustomer otherInsurant) {
        if (otherInsurant == null || otherInsurant.getId() == null)
            return;

//		if (otherInsurants == null)
//			otherInsurants = new HashMap();
//		if (insurantsRelative == null)
//			insurantsRelative = new HashMap();

        this.setAdditional("OIC/" + otherInsurant.getId(), otherInsurant);
        this.setAdditional("OIR/" + otherInsurant.getId(), relative);
    }

    public void addInsurant(InsuranceCustomer otherInsurant) {
        if (otherInsurant == null || otherInsurant.getId() == null)
            return;

        this.setAdditional("OIC/" + otherInsurant.getId(), otherInsurant);
    }

    public InsuranceCustomer getInsurant(String customerId) {
        if (customerId == null || customerId.equals(insurant.getId()))
            return insurant;

        return (InsuranceCustomer) this.getAdditional("OIC/" + customerId);
    }

    /**
     * @param customerId
     * @return java.lang.String
     * @Description: 与第一被保人的关系
     * @author za-shaojian
     * @create 15:50 2018/7/8
     * @last modify by [za-shaojian 15:50 2018/7/8 ]
     */
    public String getInsurantRelation(String customerId) {
        if (customerId == null || customerId.equals(insurant.getId()))
            return "self";

        return (String) this.getAdditional("OIR/" + customerId);
    }

    /**
     * @param customerId
     * @return java.lang.String
     * @Description: 与投保人的关系
     * @author za-shaojian
     * @create 15:50 2018/7/8
     * @last modify by [za-shaojian 15:50 2018/7/8 ]
     */
    public String getApplicantRelation(String customerId) {
        if (customerId == null || customerId.equals(applicant.getId()))
            return "self";
        //如果是第一被保人
        if (customerId.equals(insurant.getId())) {
            return insurant.get("RELATION_APPLICANT").toString();
        }
        return (String) this.getAdditional("OIR/" + customerId);
    }

    public List getInsurants() {
        List r = new ArrayList();
        r.add(insurant);

        if (additional != null) {
            Iterator iter = additional.keySet().iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                if (key != null && key.startsWith("OIC/"))
                    r.add(this.getAdditional(key));
            }
        }

        return r;
    }

    public void removeInsurant(String insurantId) {
        if (insurantId.equals(insurant.getId())) {
            removeAll();
        } else {
            for (int i = 0; i < commodityList.size(); i++) {
                Commodity c = commodityList.get(i);
                if (insurantId.equals(c.getInsurantId()))
                    remove(c);
            }

            additional.remove("OIC/" + insurantId);
            additional.remove("OIR/" + insurantId);
        }
    }

//	public Commodity newCommodity(Product product)
//	{
//		if (product instanceof Insurance)
//			return newCommodity((Insurance)product);
//		else if (product instanceof PortfolioInsurance)
//			newCommodity((PortfolioInsurance)product);
//
//		return null;
//	}

    /**
     * 新增一个主险
     * 主险可以绑定投保自己的附加险
     *
     * @param product 产品定义
     * @return 产品
     */
    public Commodity newCommodity(Insurance product) {
        return newCommodity(product, null);
    }

    public Commodity newCommodity(Insurance product, String insurantId) {
        return newCommodity(product, null, insurantId);
    }

    public Commodity newCommodity(Insurance product, String applicantId, String insurantId) {
        if (!(product instanceof Insurance))
            return null;

        if (insurantId != null) {
            if (insurantId.equals(insurant.getId())) {
                insurantId = null;
            } else {
                if (this.getInsurant(insurantId) == null)
                    return null;
            }
        }

        Commodity commodity = addCommodity(null, (Insurance) product, applicantId, insurantId, null);

        List list = ((Insurance) product).getBindList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                String defId = (String) list.get(i);
                /*
                 * Company与ProductManager并不是一一对应关系。
				 * ProductManager可以管理多个Company的所有Product
				 * 自动追加的产品要求与主要产品同一公司，所以这里用Company
				 */
                Insurance def = (Insurance) product.getCompany().getProduct(defId);
                Commodity p = addCommodity(def.isRider() ? commodity : null, def, applicantId, insurantId, null);
                p.setAdditional("relation", commodity.getId());
                p.setAuto(true);
            }
        }

        Map bindMap = product.getBindMap();
        if (bindMap != null) {
            Iterator iter = bindMap.keySet().iterator();
            while (iter.hasNext()) {
                String productId = (String) iter.next();

                boolean pass = true;
                if (bindMap.containsKey(productId)) {
                    Formula c = (Formula) bindMap.get(productId);
                    pass = Value.booleanOf(c, commodity.getFactors());
                }

                if (pass) {
                    Insurance def = (Insurance) product.getCompany().getProduct(productId);
                    Commodity p = addCommodity(def.isRider() ? commodity : null, def, applicantId, insurantId, null);
                    p.setAdditional("relation", commodity.getId());
                    p.setAuto(true);
                }
            }
        }

        return commodity;
    }

    /**
     * @param parent
     * @param product
     * @Description: 新增一个附加险
     * @author za-shaojian
     * @create 18:15 2018/5/23
     * @last modify by [za-shaojian 18:15 2018/5/23 ]
     */
    public Commodity newCommodity(Commodity parent, Insurance product) {
        return newCommodity(parent, product, null);
    }

    public Commodity newCommodity(Commodity parent, Insurance product, String insurantId) {
        if (!(product instanceof Insurance))
            return null;

        if (insurantId != null) {
            if (insurantId.equals(insurant.getId())) {
                insurantId = null;
            } else {
                if (this.getInsurant(insurantId) == null)
                    return null;
            }
        }

        return addCommodity(parent, (Insurance) product, insurantId, null);
    }

    /**
     * 当某个险种A自动带出其他险种B时：
     * 1 A是主险，B是主险
     * 2 A是主险，B是A的附加险
     * 3 A是主险，B不是A的附加险 （错误）
     * 4 A是附加险，B是主险
     * 5 A是附加险，B是A的主险的附加险
     * 6 A是附加险，B是A的附加险
     * 7 A是附加险，B是附加险，但是和A毫无关系 （错误）
     *
     * @param parent
     * @param product
     * @return
     */
    private Commodity addCommodity(Commodity parent, Insurance product, String insurantId, InitValue initValue) {
        return this.addCommodity(parent, product, null, insurantId, initValue);
    }

    private Commodity addCommodity(Commodity parent, Insurance product, String applicantId, String insurantId, InitValue initValue) {
        Commodity commodity = new Commodity(this, parent, product, insurantId, initValue);
        commodity.setId(prefix + "_" + (product.isMain() ? "M" : "R") + (int) (sequence++));
        commodityList.addCommodity(parent, commodity);
        commodity.setApplicantId(applicantId);
        return commodity;
    }

//	/**
//	 * 新增一个险种组合
//	 * @param bundle 产品组合定义
//	 * @return 该险种组合的产品列表
//	 */
//	public void newCommodity(PortfolioInsurance group)
//	{
//		Map parent = new HashMap();
//
//		Iterator iter = group.iterator();
//		while (iter.hasNext())
//		{
//			Insurance p = (Insurance)iter.next();
//			InitValue iv = group.getInitValue(p);
//			
//			Commodity commodity = addCommodity((Commodity)parent.get(group.getParent(p)), p, iv);
//			parent.put(p, commodity);
//		}
//	}

    /**
     * 是否为空投保计划
     *
     * @return
     */
    public boolean isEmpty() {
        return insurant == null || commodityList == null || commodityList.isEmpty();
    }

    /**
     * 获取该投保计划的产品列表
     *
     * @return 产品列表
     */
    public List toList() {
        return commodityList.toList();
    }

//	public List toList(int type)
//	{
//		List list = new ArrayList();
//		
//		int s = commodities.size();
//		for (int i=0;i<s;i++)
//		{
//			Commodity p = (Commodity)commodities.get(i);
//			if ((p.getType() & type) != 0)
//			{
//				list.add(p);
//			}
//		}
//		return commodities;
//	}

    public Commodity primaryCommodity() {
        return commodityList == null || commodityList.isEmpty() ? null : (Commodity) commodityList.get(0);
    }

    public Commodity getCommodity(int index) {
        return (Commodity) commodityList.get(index);
    }

    /**
     * @param productId
     * @Description: 根据产品ID得到产品实例
     * @author za-shaojian
     * @create 17:21 2018/5/23
     * @last modify by [za-shaojian 17:21 2018/5/23 ]
     */
    public Commodity getCommodityByProductId(String productId) {
        for (int i = 0; i < commodityList.size(); i++) {
            Commodity p = (Commodity) commodityList.get(i);
            if (productId.equals(p.getProduct().getId())) {
                return p;
            }
        }
        return null;
    }

    /**
     * @param productId
     * @Description: 根据产品ID和被保人ID得到产品实例
     * @author za-shaojian
     * @create 17:21 2018/5/23
     * @last modify by [za-shaojian 17:21 2018/5/23 ]
     */
    public Commodity getCommodityByProductIdAndInsureId(String productId, String insureId) {
        for (int i = 0; i < commodityList.size(); i++) {
            Commodity p = (Commodity) commodityList.get(i);
            if (productId.equals(p.getProduct().getId()) && insureId.equals(p.getInsureId())) {
                return p;
            }
        }
        return null;
    }

    /**
     * @param id
     * @Description: 根据主键得到产品实例
     * @author za-shaojian
     * @create 17:23 2018/5/23
     * @last modify by [za-shaojian 17:23 2018/5/23 ]
     */
    public Commodity getCommodityById(String id) {
        for (int i = 0; i < commodityList.size(); i++) {
            Commodity p = (Commodity) commodityList.get(i);
            if (id.equals(p.getId())) {
                return p;
            }
        }
        return null;
    }

    /**
     * @param productId
     * @Description: 根据产品ID得到附加险产品实例
     * @author za-shaojian
     * @create 17:21 2018/5/23
     * @last modify by [za-shaojian 17:21 2018/5/23 ]
     */
    public Commodity getRiderCommodityByProductId(String productId, String parentId) {
        for (int i = 0; i < commodityList.size(); i++) {
            Commodity p = (Commodity) commodityList.get(i);
            if (p.getProduct().isRider()) {
                if (productId.equals(p.getProduct().getId())) {
                    if (p.getParent() != null && parentId.equals(p.getParent().getProduct().getId())) {
                        return p;
                    }
                }
            }
        }
        return null;
    }


    /**
     * 从投保计划中移除一个产品
     * 1．该产品的附加险都会被移除。
     * 2．与该产品关联的产品也会被一并移除。
     * 3．如果该产品是产品组合中的一个，则整个组合的产品都会被移除。
     *
     * @param commodity
     */
    public void remove(Commodity commodity) {
        commodityList.removeAll(findLinkedCommodities(commodity));
        clearCache();
    }

    /**
     * 从投保计划中移除指定位置的产品
     * 1．该产品的附加险都会被移除。
     * 2．与该产品关联的产品也会被一并移除。
     * 3．如果该产品是产品组合中的一个，则整个组合的产品都会被移除。
     *
     * @param index
     */
    public void remove(int index) {
        remove(getCommodity(index));
    }

    /**
     * @param productId
     * @Description: 根据产品ID和被保人ID移除产品实例
     * @author za-shaojian
     * @create 17:21 2018/5/23
     * @last modify by [za-shaojian 17:21 2018/5/23 ]
     */
    public void removeCommodityByProductIdAndInsureId(String productId, String insureId) {
        for (int i = 0; i < commodityList.size(); i++) {
            Commodity commodity = (Commodity) commodityList.get(i);
            if (productId.equals(commodity.getProduct().getId()) && insureId.equals(commodity.getInsureId())) {
                commodityList.remove(commodity);
            }
        }
    }

    public List findLinkedCommodities(Commodity c) {
        List r = new ArrayList();

        if (c.getType() == Commodity.TYPE_IN_GROUP) {
            Commodity grp = (Commodity) c.getAdditional("product_group");
            r.add(grp);

            List group = (List) grp.getAdditional("group");
            if (group != null)
                r.addAll(group);
        } else if (c.getType() == Commodity.TYPE_GROUP) {
            r.add(c);

            List group = (List) c.getAdditional("group");
            if (group != null)
                r.addAll(group);
        } else //if (p.getType() == Product.TYPE_USUAL)
        {
            for (int i = commodityList.size() - 1; i >= 0; i--) {
                Commodity p1 = (Commodity) commodityList.get(i);
                if (p1.getParent() == c || p1 == c) {
                    r.add(p1);
                }
            }
        }

        return r;
    }

    public void removeAll() {
        commodityList.clear();
        factors = null;
    }

    /**
     * use removeAll() instead
     *
     * @deprecated
     */
    public void clear() {
        removeAll();
    }

    public FactorsSupport getFactors() {
        if (factors == null)
            factors = new PlanFactors(this);

        return factors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int size() {
        return commodityList == null ? 0 : commodityList.size();
    }

    public boolean hasFormat(String attachmentName) {
        Company ic = this.primaryCommodity().getCompany();
        return ic.getAttachment(attachmentName) != null;
    }

    public Object format(String attachmentName) {
        return format(attachmentName, null);
    }

    public Object format(String attachmentName, Object value) {
        if (this.primaryCommodity() == null)
            return null;

        Company ic = this.primaryCommodity().getCompany();

        if (ic.getAttachment(attachmentName) == null)
            return null;

        FilterPlan filter = ic.getPlanFilter(ic.getAttachmentFilterName(attachmentName));
        if (filter != null)
            return filter.filtrate(this, attachmentName);

        throw new FilterNotFoundException("filter for " + attachmentName + " is not found.");
    }

    /**
     * @param filterName
     * @return
     * @deprecated
     */
    public Object filtrate(String filterName) {
        FilterPlan filter = this.primaryCommodity().getCompany().getPlanFilter(filterName);
        if (filter != null)
            return filter.filtrate(this, filterName); //以前要求attachmentName和filterName同名，所以可以这么写，现在用format了

        throw new FilterNotFoundException("filter<" + filterName + "> is not found.");
    }

    public Object getFactor(String factorName) {
        return getFactors().get(factorName);
    }

    /**
     * @param name
     * @param v
     * @return void
     * @Description: 注意：不是都可以保存，只能保存基本对象，字符串和数字等。其他对象重新读取后会消失。
     * @author za-shaojian
     * @create 18:15 2018/5/23
     * @last modify by [za-shaojian 18:15 2018/5/23 ]
     */
    public void setValue(String name, Object v) {
        if (value == null)
            value = new HashMap();

        value.put(name, v);
        clearCache();
    }

    public Object getValue(String name) {
        if (value == null)
            return null;

        return value.get(name);
    }

    public Map getValue() {
        return value;
    }

    /**
     * 一般不要自己调用这个方法，设置参数请使用setValue
     *
     * @param name
     * @param value
     */
    public void setAdditional(String name, Object value) {
        if (additional == null)
            additional = new HashMap();

        additional.put(name, value);
    }

    public Object getAdditional(String name) {
        if (additional == null)
            return null;

        return additional.get(name);
    }

    public List check() {
        return RuleUtil.check(this);
    }

    /**
     * use getInsureTime() instead
     *
     * @deprecated
     */
    public Date getEffectiveTime() {
        return insureTime;
    }

    /**
     * use setInsureTime(Date insureTime) instead
     *
     * @deprecated
     */
    public void setEffectiveTime(Date effectiveTime) {
        this.insureTime = effectiveTime;

        clearCache();
    }

    public void clearCache() {
        getFactors().clearCache();

        for (int i = 0; i < commodityList.size(); i++)
            getCommodity(i).clearCache();
    }

    public void setApplicant(InsuranceCustomer applicant) {
        this.applicant = applicant;
        clearCache();

//		if (this.applicant == applicant)
//		{
//			clearCache();
//			return;
//		}
//		
//		this.applicant = applicant;
//		
//		//客户对象更换，产品里面关联的客户对象此时都指向旧的客户对象，全部清除简单处理，产品需要重新设定。
//		removeAll();
    }

    public void setInsurant(InsuranceCustomer insurant) {
        this.insurant = insurant;
        clearCache();

//		if (this.insurant == insurant)
//		{
//			clearCache();
//			return;
//		}
//		
//		this.insurant = insurant;
//		
//		//客户对象更换，产品里面关联的客户对象此时都指向旧的客户对象，全部清除简单处理，产品需要重新设定。
//		removeAll();
    }

    /**
     * 这个方法主要是用于读取生成计划
     *
     * @return
     */
    public SequenceList getCommodityList() {
        return commodityList;
    }

    public Date getInsureTime() {
        return insureTime;
    }

    public void setInsureTime(Date insureTime) {
        this.insureTime = insureTime;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
