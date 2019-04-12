import com.xuzp.insuredxmltool.core.insurance.plan.InsuranceCustomer;
import com.xuzp.insuredxmltool.core.insurance.plan.Plan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 建议书类
 */

public class Proposal implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	InsuranceCustomer applicant;
	
	List<Plan> planList;
	
	public Proposal()
	{
		this(null);
	}
	
	public Proposal(InsuranceCustomer applicant)
	{
		this.applicant = applicant;
	}

	/**
	 * 是否为空建议书
	 * @return
	 */
	public boolean isEmpty()
	{
		return planList == null || planList.isEmpty();
	}
	
	/**
	 * 获取投保计划列表
	 * @return 投保计划列表
	 */
	public List<Plan> getPlanList()
	{
		return planList;
	}
	
	public Plan getPlan(int index)
	{
		return (Plan)planList.get(index);
	}
	
	public Plan getPlan(InsuranceCustomer insured)
	{
		for (int i = 0; i < getPlanCount(); i++)
		{
			Plan plan = getPlan(i);
			if (plan != null && plan.getInsurant() == insured)
			{
				return plan;
			}
		}
		return null;
	}
	
	public int getPlanCount()
	{
		return isEmpty() ? 0 : planList.size();
	}
	
	public Plan newPlan(InsuranceCustomer insured)
	{
		Plan plan = new Plan(applicant, insured);
		plan.setValue("CHANNEL", "agy");
		plan.setValue("AGENT_ZONE", "18610");

		if (planList == null)
			planList = new ArrayList<Plan>();
		
		planList.add(plan);
		
		return plan;
	}
	
	/**
	 * 清空建议书
	 */
	public void clear()
	{
		planList = null;
	}
	
	/**
	 * 删除一个投保计划
	 * @param insured 该投保计划的被保人
	 */
	public boolean removePlan(InsuranceCustomer insured)
	{
		for (int i = 0; i < getPlanCount(); i++)
		{
			Plan plan = getPlan(i);
			if (plan != null && plan.getInsurant() == insured)
			{
				planList.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	public void removePlan(int index)
	{
		planList.remove(index);
	}
	
	public void removeAllPlans()
	{
		planList.clear();
	}

	public InsuranceCustomer getApplicant()
	{
		return applicant;
	}

	public void setApplicant(InsuranceCustomer applicant)
	{
		this.applicant = applicant;
		
		for (int i=0; i<this.getPlanCount(); i++)
		{
			Plan plan = this.getPlan(i);
			plan.setApplicant(applicant);
		}
	}
}
