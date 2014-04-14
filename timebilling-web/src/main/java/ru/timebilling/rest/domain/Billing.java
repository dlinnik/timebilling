package ru.timebilling.rest.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

/**
 * Виртуальная сущность для биллинга
 * @author vshmelev
 *
 */
public class Billing {
	
	private Date startDate;
	private Date endDate;
	
	private List<BillingItem> items;

	public Billing(Date startDate, Date endDate, List<BillingItem> items) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.items = items;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public List<BillingItem> getItems() {
		return items;
	}
	
	

}
