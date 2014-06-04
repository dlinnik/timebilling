package ru.timebilling.rest.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import ru.timebilling.model.domain.Project;

/**
 * Виртуальная сущность для биллинга
 * @author vshmelev
 *
 */
public class Billing {
	
	private Date startDate;
	private Date endDate;
	
	private List<BillingItem> items = new ArrayList<BillingItem>();
	
	public static enum BillingType{
		TIME,
		EXP
	};

	public Billing(Date startDate, Date endDate, List<BillingItem> items) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.items = items;
	}
	
	public Billing(){
		
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	
	public void add(Project p, Float sum, int month, int year, BillingType type) {
		BillingItem item = null;
		for(BillingItem i : items){
			if(i.getMonth() == month && i.getYear() == year && i.getProject().getId() == p.getId()){
				//уже есть запись на этот период
				item = i;
				break;
			}
		}
			
		if(item==null){
			item = new BillingItem(p, month, year);
			items.add(item);
		}
		
		switch (type) {
		case TIME:
			item.setTimeSum(sum);
			break;

		case EXP:
			item.setExpSum(sum);
			break;
		}
	}

	public List<BillingItem> getItems() {
		return items;
	}
	
	

}
