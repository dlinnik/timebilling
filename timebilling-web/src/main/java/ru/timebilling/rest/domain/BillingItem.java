package ru.timebilling.rest.domain;

import java.util.Date;

import ru.timebilling.persistance.domain.Project;

/**
 * Запись (период) в биллинге по проекту
 * @author vshmelev
 *
 */
public class BillingItem {
	
	private Project project;
	private Date periodStartDate;
	private Date periodEndDate;
	private Float timeSum;
	private Float expSum;
	
	public BillingItem(Project project, Date periodStartDate,
			Date periodEndDate, Float timeSum, Float expSum) {
		super();
		this.project = project;
		this.periodStartDate = periodStartDate;
		this.periodEndDate = periodEndDate;
		this.timeSum = timeSum;
		this.expSum = expSum;
	}

	/**
	 * проект
	 * @return
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * Дата начала периода
	 * @return
	 */
	public Date getPeriodStartDate() {
		return periodStartDate;
	}

	/**
	 * Дата завершения периода
	 * @return
	 */
	public Date getPeriodEndDate() {
		return periodEndDate;
	}

	/**
	 * Сумма затраченного времени в деньгах 
	 * @return
	 */
	public Float getTimeSum() {
		return timeSum;
	}

	/**
	 * Сумма затрат в деньгах
	 * @return
	 */
	public Float getExpSum() {
		return expSum;
	}	
	
	
	
	
}
