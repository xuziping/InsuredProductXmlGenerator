package com.xuzp.insuredxmltool.core.insurance.plan;

import com.xuzp.insuredxmltool.core.insurance.plan.filter.FilterCommodity;
import com.xuzp.insuredxmltool.core.insurance.plan.filter.FilterNotFoundException;
import com.xuzp.insuredxmltool.core.insurance.product.*;
import com.xuzp.insuredxmltool.core.insurance.product.rule.RuleUtil;
import com.xuzp.insuredxmltool.core.tool.formula.Formula;
import com.xuzp.insuredxmltool.core.tool.formula.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * 产品对象
 * 描述一个实例化的产品，包含他的缴费、保障、领取、购买，以及它的计算参数表。
 * @author 李新豪
 *
 * 修改历史：
 * 
 * 增加了设置附加信息的方法(setAdditional/getAdditional)
 * - 李新豪 2011/07/07
 * 
 * 险种参数部分加上了缓冲，在调整缴费、保障、领取、购买的时候，缓冲需要清空。
 * - 李新豪 2011/06/02
 */

public class Commodity implements Serializable
{
	private static final long serialVersionUID				= 1L;
	
	public static final int TYPE_USUAL						= 1;
	public static final int TYPE_GROUP						= 2;
	public static final int TYPE_IN_GROUP					= 4;
	
	public static final int PREMIUM_RAW						= 1;
	public static final int PREMIUM_FIRST_TERM				= 2;
	
	public static final int COMMISSION_RATE					= 1;
	public static final int COMMISSION_TERM					= 2; //每期的佣金，入参为期数
	public static final int COMMISSION_YEAR					= 3; //当前缴费频率下，每年的佣金，入参为年数
	public static final int COMMISSION_PAY_FREQ_YEAR		= 4; //年交缴费频率下，每年的佣金，入参为年数（目前仅全球项目可用）
	public static final int COMMISSION_FIRST_TERM			= 5; //首期佣金（全球人寿月交首期保费是两个月一起的，佣金也是双倍） 
	
	String id;
	
	int type = TYPE_USUAL; //在投保计划中的类型，普通产品、组合、组合中的产品
	
	boolean autoAdd = false;

	Insurance product;
	
	Plan plan;

	String insurantId; //为空为主被保险人

	String insureId;//被保人ID

	String applicantId;//投保人ID

	Commodity parent;
	
	CommodityFactors factors;
	
	SequenceList children; //不是组合的话，这个值为空
	
	Map value; //这是个用户设置的，需要保存
	Map additional; //这个是自动带入的，不需要保存
	
	public Commodity(Plan plan, Commodity parent, Insurance product, String insurantId, InitValue initValue)
	{
		this.plan = plan;
		this.parent = parent;
		this.product = product;
		this.insurantId = insurantId;
		
		build(initValue == null ? product.getInitValue() : initValue);
	}
	
	private void setParameterByVirtual(InitValue pv)
	{
		//以自定义的公式替代默认的公式
		if (pv.getAmount() != null)
			factors.setFormula("AMOUNT_DTL", pv.getAmount());
		if (pv.getPremium() != null)
			factors.setFormula("PREMIUM_DTL", pv.getPremium());
		if (pv.getQuantity() != null)
			factors.setFormula("QUANTITY_DTL", pv.getQuantity());

		//设置选项
		List optionType = pv.getOptionList();
		for (int i = 0; optionType != null && i < optionType.size(); i++)
		{
			String type = (String)optionType.get(i);
			Formula f = pv.getOption(type);
			
			String code = Value.stringOf(f, factors);
			setInput(type, code);
		}
	}
	
	/**
	 * 产品参数设置
	 */
	private void build(InitValue pv)
	{
		factors = new CommodityFactors(this);
		
		//定义中的初始值全部放入
		Map map = product.getAdditional();
		if (map != null)
		{
			additional = new HashMap();
			additional.putAll(map);
			
//			Iterator iter = map.keySet().iterator();
//			while (iter.hasNext())
//			{
//				String key = (String)iter.next();
//				Object val = map.get(key);
//				setAdditional(key, val);
//			}
		}
		
		//带有预设值的初始化
		if (pv != null)
			setParameterByVirtual(pv);

		//初始化必要的值，如果已经在预设值中初始化过了，那么跳过
		int inputMode = product.getInputMode();
		if (inputMode == Purchase.QUANTITY)
		{
			if ((pv == null || pv.getQuantity() == null) && this.getValue("QUANTITY") == null)
				setQuantity(1);
		}
		else if (inputMode == Purchase.AMOUNT)
		{
			if ((pv == null || pv.getAmount() == null) && this.getValue("AMOUNT") == null)
				setAmount(10000);
		}
		else if (inputMode == Purchase.PREMIUM)
		{
			if ((pv == null || pv.getPremium() == null) && this.getValue("PREMIUM") == null)
				setPremium(10000);
		}
		else if (inputMode == Purchase.PREMIUM_AND_AMOUNT)
		{
			if ((pv == null || pv.getPremium() == null) && this.getValue("PREMIUM") == null)
				setPremium(1000);
			if ((pv == null || pv.getAmount() == null) && this.getValue("AMOUNT") == null)
				setAmount(10000);
		}
		else if (inputMode == Purchase.RANK_AND_QUANTITY)
		{
			if ((pv == null || pv.getQuantity() == null) && this.getValue("QUANTITY") == null)
				setQuantity(1);
		}
		
		//初始化选择项，默认选择第一项，如果已经在预设值中初始化过了，那么跳过
		List optionType = product.getOptionType();
		for (int i = 0; optionType != null && i < optionType.size(); i++)
		{
			String type = (String)optionType.get(i);
			List optionList = product.getOptionList(type);
			if (this.getInput(type) == null && optionList != null && !optionList.isEmpty())
			{
				setInput(type, (Option)optionList.get(0));
			}
		}
		
		//如果这个产品是一个组合
		Portfolio portfolio = product.getPortfolio();
		if (portfolio != null)
		{
			children = new SequenceList();
			
			Iterator iter = portfolio.iterator();
			while (iter.hasNext())
			{
				InsuranceRecom ir = (InsuranceRecom)iter.next();
				
				Commodity parent = children.getCommodity(ir.getInsurance());
				Commodity child = new Commodity(this.getPlan(), parent, ir.getInsurance(), insurantId, ir.getInitValue());
				child.setId(children.size() + 1 + "");
				
				children.addCommodity(parent, child);
			}
		}
	}
	
	public Company getCompany()
	{
		return (Company)this.getProduct().getCompany();
	}
	
	public Input setInput(String type, String optionCode)
	{
		return setInput(type, getCompany().getOption(type, optionCode));
	}
	
	public Input setInput(String type, Option option)
	{
		if (option == null)
			return null;
		
//		this.setValue("option/" + type, option.getCode());
		
//		String name = getCompany().getOptionVariable(type);
//		if (name == null)
//			return null;
		
		Input input = new Input(option, this);
		this.setAdditional(type, input);
		
		//映射一个值，用处不是很大，考虑未来删除
		String name = getCompany().getOptionVariable(type); 
		if (name != null)
			this.setAdditional(name, input);
		
		clearCache();
		
		return input;
	}
	
	public Input getInput(String type)
	{
//		return (Input)this.getAdditional(getCompany().getOptionVariable(type));
		return (Input)this.getAdditional(type);
		
//		String name = getCompany().getOptionVariable(type);
//		if (name == null)
//			return null;
//		
//		Input input = (Input)this.getAdditional(name);
//		if (input != null)
//			return input;
//		
//		String code = (String)this.getValue("option/" + type);
//		if (code != null)
//		{
//			input = new Input(getCompany().getOption(type, code), this);
//			this.setAdditional(name, input);
//		}
//
//		return input;
	}

	public Insurance getProduct()
	{
		return product;
	}
	
	/**
	 * 除了险种重新载入导致险种定义对象指向错误的情况以外，不要使用这个方法。
	 * @param product
	 */
	public void setProduct(Insurance product)
	{
		this.product = product;
	}
	
	public boolean hasFormat(String attachmentName)
	{
		return product.getAttachment(attachmentName) != null;
	}
	
	public Object format(String attachmentName, Object value)
	{
		if (product.getAttachment(attachmentName) == null)
			return null;
		
		FilterCommodity filter = this.getCompany().getCommodityFilter(product.getAttachmentFilterName(attachmentName));
		if (filter != null)
			return filter.filtrate(this, attachmentName);
		
		throw new FilterNotFoundException("filter for " + attachmentName + " is not found.");
	}
	
	public Object format(String attachmentName)
	{
		return format(attachmentName, null);
	}
	
	/**
	 * @deprecated
	 * @param filterName
	 * @return
	 * @throws FilterNotFoundException
	 */
	public Object filtrate(String filterName) throws FilterNotFoundException
	{
		FilterCommodity filter = this.getCompany().getCommodityFilter(filterName);
		if (filter != null)
			return filter.filtrate(this, filterName); //以前要求attachmentName和filterName同名，所以可以这么写，现在用format了
		
		throw new FilterNotFoundException("filter<" + filterName + "> is not found.");
	}

	public FactorsSupport getFactors()
	{
		return factors;
	}
	
	public Object getFactor(String factorName)
	{
		return factors.get(factorName);
	}

	public Plan getPlan()
	{
		return plan;
	}

	/**
	 * 如果是附加险，返回主险对象；否则返回null
	 * @return
	 */
	public Commodity getParent()
	{
		return parent;
	}
	
	/**
	 * 获取该产品的所有附加险中，指定产品定义id的产品对象。
	 * @param productId
	 * @return
	 */
	public Commodity getRider(String productId)
	{
		Plan plan = this.getPlan();
		
		for (int i = 0; i < plan.size(); i++)
		{
			Commodity commodity = plan.getCommodity(i);
			if (commodity.getParent() == this && commodity.getProduct().getId().equals(productId))
			{
				return commodity;
			}
		}
		
		return null;
	}
	
	public Commodity getChild(String productId)
	{
		if (children == null)
			return null;
		
		return children.getCommodity(productId);
	}

	public void setQuantity(double quantity)
	{
		this.setValue("QUANTITY", Double.valueOf(quantity));
		this.clearCache();
	}
	
	public void setPremium(double premium)
	{
		this.setValue("PREMIUM", Double.valueOf(premium));
		this.clearCache();
	}
	
	public void setAmount(double amount)
	{
		this.setValue("AMOUNT", Double.valueOf(amount));
		this.clearCache();
	}

	/**
	 * 返回首年的总计保费
	 * 不仅仅包含普通保费，还包括追加的保费
	 * 这个一般用于展示界面
	 * @deprecated
	 * @return
	 */
	public double getPremiumFirstYear()
	{
		return round(Value.doubleOf(factors.get("PREMIUM_FY")), 2);
	}
	
	/**
	 * PREMIUM_RAW
	 * 每期保费的计算值
	 * PREMIUM_FIRST_TERM
	 * 返回首期的应交保费
	 * 不仅仅包含普通保费，还包括追加的保费，以及按规则应该在首期多交的保费（有些公司要求第二期的保费在第一期时候一起交了）
	 * @return
	 */
	public double getPremium(int type)
	{
		if (type == Commodity.PREMIUM_FIRST_TERM)
			return round(Value.doubleOf(factors.get("PREMIUM_FT")), 2);
		else if (type == Commodity.PREMIUM_RAW)
			return round(Value.doubleOf(factors.get("PREMIUM")), 2);
		
		return 0;
	}

	public double getPremium()
	{
//		return Value.doubleOf(factors.get("PREMIUM"));
		return round(Value.doubleOf(factors.get("PREMIUM")), 2);
	}

	public double getAmount()
	{
//		return Value.doubleOf(factors.get("AMOUNT"));
		return round(Value.doubleOf(factors.get("AMOUNT")), 2);
	}
	
	public double getQuantity()
	{
//		return Value.doubleOf(factors.get("QUANTITY"));
		return round(Value.doubleOf(factors.get("QUANTITY")), 2);
	}
	
	public String getId()
	{
		return id;
	}

	public void setInsureId(String insureId)
	{
		this.insureId = insureId;
	}

	public String getInsureId()
	{
		return insureId;
	}

	public void setInsurantId(String insurantId)
	{
		this.insurantId = insurantId;
	}

	public String getInsurantId()
	{
		return insurantId;
	}

	public void setId(String id)
	{
		this.id = id;
	}
	
	public void clearCache()
	{
		clearCache(null);
	}

	public String getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}

	/**
	 * 一般的产品内容发生变化后，只需要清除自己的缓存即可
	 * 如果产品有相关的产品，由于计算的关联，相关的产品的数据都需要清除
	 * 
	 * list是本次清除已经被清除过的产品id列表，解决清楚时互相关联造成的死锁问题
	 */
	private void clearCache(List list)
	{
		if (list != null && list.contains(id))
			return;
		
		factors.clearCache();
		
//		if (additional != null)
//		{
//			Iterator iter = additional.values().iterator();
//			while (iter.hasNext())
//			{
//				Object o = iter.next();
//				if (o instanceof Input) //清除选择项中的缓冲信息
//					((Input)o).clearCache();
//			}
//		}
		
		/*
		 * 由于主附险通常都是有关系
		 * 默认清除主附险的缓存
		 * 2015-02-01 李新豪
		 */
		if (list == null)
			list = new ArrayList();
		list.add(id);

		if (parent != null)
			parent.clearCache(list);
		
		for (int i=0;i<plan.size();i++)
		{
			Commodity c = plan.getCommodity(i);
			if (c.getParent() == this)
				c.clearCache(list);
		}
		
		InsuranceDepend depend = product.getDepend();
		if (depend != null)
		{
			if (parent != null)
			{
				if (depend.isParentMatched())
					parent.clearCache(list);
			}
			
			for (int i=0;i<plan.size();i++)
			{
				Commodity c = plan.getCommodity(i);

				if (depend.isAllRidersMatched() && c.getParent() == this)
					c.clearCache(list);
				
				if (depend.isMatched(c.getProduct().getId()))
				{
					//是主险那么直接视为关联，如果是个附加险，只有挂在这个险种下的才视为关联（也就是说其他产品的附加险不算）
					if (c.getParent() == null || c.getParent() == this)
						c.clearCache(list);
				}
			}
		}
		
//		//如果是产品组合，需要清空组合中所有产品的buffer
//		if (getAdditional("group") != null)
//		{
//			List list = (List)getAdditional("group");
//			for (int i=0;i<list.size();i++)
//			{
//				Product product = (Product)list.get(i);
//				product.clearBuffer();
//			}
//		}
	}
	
	/**
	 * 注意：不是都可以保存，只能保存基本对象，字符串和数字等。其他对象重新读取后会消失。
	 * @param name
	 * @param value
	 */
	public void setValue(String name, Object v)
	{
		if (value == null)
			value = new HashMap();
		
		value.put(name, v);
		clearCache();
	}
	
	public Object getValue(String name)
	{
		if (value == null)
			return null;
		
		return value.get(name);
	}
	
	public Map getValue()
	{
		return value;
	}
	
	/**
	 * 一般不要自己调用这个方法，设置参数请使用setValue
	 * @param name
	 * @param value
	 */
	public void setAdditional(String name, Object value)
	{
		if (additional == null)
			additional = new HashMap();
		
		additional.put(name, value);
	}
	
	public Object getAdditional(String name)
	{
		if (additional == null)
			return null;
		
		return additional.get(name);
	}
	
	public List check()
	{
		return RuleUtil.check(this);
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}
	
	public Date getInsureTime()
	{
		return plan.getInsureTime();
	}

	public Input getPay()
	{
		return getInput(Option.PAY);
	}

	public Input getInsure()
	{
		return getInput(Option.INSURE);
	}

	public Input getRank()
	{
		return getInput(Option.RANK);
	}

	public void setPay(Option pay)
	{
		setInput(Option.PAY, pay);
	}

	public void setInsure(Option insure)
	{
		setInput(Option.INSURE, insure);
	}

	public void setRank(Option rank)
	{
		setInput(Option.RANK, rank);
	}
	
	/**
	 * 获取产品的数额描述
	 * 返回结果不一定是保额，根据产品自动识别类型（保额档次份数）
	 * @return
	 */
	public String getAmountDesc()
	{
		int type = this.getProduct().getPurchaseMode();
		if (type == Purchase.AMOUNT)
		{
			Object desc = factors.get("AMOUNT_DESC");
			return desc == null ? Math.round(this.getAmount()) + "元" : desc.toString();
		}
		else if (type == Purchase.QUANTITY)
		{
			Object desc = factors.get("QUANTITY_DESC");
			return desc == null ? Math.round(this.getQuantity()) + "份" : desc.toString();
		}
		else if (type == Purchase.RANK)
		{
			return this.getRank().getDesc();
		}
		else if (type == Purchase.RANK_AND_QUANTITY)
		{
			Object desc = factors.get("QUANTITY_DESC");
			String str = desc == null ? Math.round(this.getQuantity()) + "份" : desc.toString();
			return this.getRank().getDesc() + str;
		}
		else if (type == Purchase.NONE)
		{
			return "-";
		}
		
		Object desc = factors.get("AMOUNT_DESC");
		return desc == null ? Math.round(this.getAmount()) + "元" : desc.toString();
	}

	public String getCode()
	{
		return product.getCode();
	}
	
	/**
	 * 全球人寿使用XAR20等在基础代码后面加上年期等的代号来作为完整的代码
	 * 这种情况下和传统的code不同，这个code是个动态值，此时可以使用fullcode来表示
	 * 比如XAR20，getCode返回“XAR”，getFullCode返回“XAR20”
	 * 如果不设置fullcode的值，那么默认等于code（对于传统方式使用静态代码的公司来说，他们没区别）
	 * @return
	 */
	public String getFullCode()
	{
		if (product.getFullCode() == null)
			return getCode();
		
		return Value.stringOf(product.getFullCode(), factors);
	}

	public SequenceList getChildren()
	{
		return children;
	}

	public InsuranceCustomer getInsurant()
	{
		return plan.getInsurant(insurantId);
	}

	/**
	 * 设置自动续保年龄
	 * @param age
	 * @param amount
	 */
	public void setRenewalAge(int age)
	{
		this.setValue("RENEWAL_AGE", new Integer(age));
		this.clearCache();
	}
	
	/**
	 * 佣金和很多要素有关，渠道、业务员本身的属性等
	 * 很大程度依赖于保险公司自己的内部属性，而不仅仅是产品本身，需要注入很多外部参数才可以计算
	 * @return
	 */
	public double getCommission(int type)
	{
		return getCommission(type, 0, 0);
	}
	
//	public double getCommission(int type, int time)
//	{
//		if (type == Commodity.COMMISSION_RATE) //time单位为月
//		{
//			return getCommissionRate(time);
//		}
//		else if (type == Commodity.COMMISSION_TERM) //time单位为缴费频率
//		{
//			return getPremium() * getCommissionRate(time * (12 / getPayFreqPerYear()));
//		}
//		else if (type == Commodity.COMMISSION_YEAR) //time单位为年
//		{
//			return getPremium() * getCommissionRate(time * 12) * getPayFreqPerYear();
//		}
//		
//		return 0;
//	}
	
	public double getCommission(int type, int time, int order)
	{
		if (type == Commodity.COMMISSION_RATE) //time单位为期
		{
			return getCommissionRate(time, order);
		}
		else if (type == Commodity.COMMISSION_TERM) //time单位为期
		{
			Object commission = factors.run("COMMISSION", new Object[] { new Integer(time), new Integer(order) });
			return commission == null ? 0 : round(Value.doubleOf(commission), 2);
		}
		else if (type == Commodity.COMMISSION_YEAR) //time单位为年
		{
			Object commission = factors.run("COMMISSION_YEAR", new Object[] { new Integer(time), new Integer(order) });
			return commission == null ? 0 : round(Value.doubleOf(commission), 2);
		}
		else if (type == Commodity.COMMISSION_PAY_FREQ_YEAR) //time单位为年，年缴下每年的佣金
		{
			Object commission = factors.run("COMMISSION_PAY_FREQ_YEAR", new Object[] { new Integer(time), new Integer(order) });
			return commission == null ? 0 : round(Value.doubleOf(commission), 2);
		}
		else if (type == Commodity.COMMISSION_FIRST_TERM) //time无意义
		{
			Object commission = factors.run("COMMISSION_FIRST_TERM", new Object[] { new Integer(order) });
			return commission == null ? 0 : round(Value.doubleOf(commission), 2);
		}
		
		return 0;
	}
	
	/**
	 * 获取某个时间点的佣金率
	 * @param month
	 * @return
	 */
	public double getCommissionRate(int month, int order)
	{
		Object cRate = factors.run("COMMISSION_RATE", new Object[] { new Integer(month), new Integer(order) });
		return cRate == null ? 0 : Value.doubleOf(cRate);
	}
	
	/**
	 * 获取每年缴费次数
	 * @return
	 */
	public int getPayFreqPerYear()
	{
		Input payFreq = this.getInput(Option.PAY_FREQ);
		if (payFreq != null)
		{
			int v = payFreq.getValue();
			if (v == 2)
				return 12;
			else if (v == 3)
				return 4;
			else if (v == 4)
				return 2;
		}
		
		return 1;
	}

	/**
	 * 该险种是否是自动带出的
	 * @return
	 */
	public boolean isAuto()
	{
		return autoAdd;
	}

	public void setAuto(boolean autoAdd)
	{
		this.autoAdd = autoAdd;
	}
	
	private static double round(double v, int scale)
	{
		return ((BigDecimal)BigDecimal.valueOf(v + 0.0000001)).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
