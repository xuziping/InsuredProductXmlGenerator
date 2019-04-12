import com.xuzp.insuredxmltool.core.insurance.plan.InsuranceCustomer;
import com.xuzp.insuredxmltool.core.insurance.plan.Time;

import java.util.Date;


public class Customer implements InsuranceCustomer
{
	static int testId = 1;
	
	String id;
	String name;
	String job;
	int social_ins;
	int demo;
	
	Date birthday;
	
	int gender;
	
	private String  relation;//与投保人关系

	private String relationFristInsure; //与第一被保人关系
	
	public Customer(String date, int gender)
	{
		this.id = testId++ + "";
		this.gender = gender;
		this.birthday = Time.getDate(date);
		if (gender == Customer.GENDER_MALE)
			this.name = new String[] {"高澄", "王翦", "高长恭", "李绩", "李泰"}[testId % 5];
		else
			this.name = new String[] {"李令月", "王昭君", "上官婉儿", "李清照", "武瞾"}[testId % 5];
		this.social_ins = 8;
		this.demo = 0;
	}
	
	public Customer(String date, int gender,String job)
	{
		this.id = testId++ + "";
		this.gender = gender;
		this.job = job;
		this.birthday = Time.getDate(date);
		if (gender == Customer.GENDER_MALE)
			this.name = new String[] {"高澄", "王翦", "高长恭", "李绩", "李泰"}[testId % 5];
		else
			this.name = new String[] {"李令月", "王昭君", "上官婉儿", "李清照", "武瞾"}[testId % 5];
	}
	
	public Customer(String date, int gender,String job,String relation)
	{
		this.id = testId++ + "";
		this.gender = gender;
		this.job = job;
		this.birthday = Time.getDate(date);
		this.relation = relation;
		if (gender == Customer.GENDER_MALE)
			this.name = new String[] {"高澄", "王翦", "高长恭", "李绩", "李泰"}[testId % 5];
		else
			this.name = new String[] {"李令月", "王昭君", "上官婉儿", "李清照", "武瞾"}[testId % 5];
	}
	
	public Object get(String name)
	{
		if ("NAME".equals(name))
			return this.name;
		if ("OCCUPATION_CODE".equals(name)) //职业代码
			return "0010001";
		if ("SOCIAL_INS".equals(name)) //SOCIAL_INS : 0 无社保 1 有社保 null 未录入
			return this.social_ins;
		if ("DEMO".equals(name))
			return this.demo;
		if("RELATION_FRIST_INSURE".equals(name))
			return relationFristInsure;
		if("RELATION_APPLICANT".equals(name))
			return relation;
		return null;
	}
	
	

	public String getId()
	{
		return id;
	}

	public Date getBirthday()
	{
		return birthday;
	}

	public int getGenderCode()
	{
		return gender;
	}
	
	public int getOccupationCategory(String typeCode) //职业分类等级
	{
//		return Integer.parseInt(job);
		return 1;
	}
	
}
