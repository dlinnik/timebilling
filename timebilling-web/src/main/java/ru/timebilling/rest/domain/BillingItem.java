package ru.timebilling.rest.domain;

import java.util.Calendar;
import java.util.Date;

import ru.timebilling.model.domain.Project;

/**
 * Запись (период) в биллинге по проекту
 * @author vshmelev
 *
 */
public class BillingItem {
	
	private Project project;
	private Date periodStartDate;
	private Date periodEndDate;
	private int month;
	private int year;

	private Float timeSum = 0f;
	private Float expSum = 0f;
	
	public BillingItem(Project project, Date periodStartDate,
			Date periodEndDate, Float timeSum, Float expSum) {
		super();
		this.project = project;
		this.periodStartDate = periodStartDate;
		this.periodEndDate = periodEndDate;
		this.timeSum = timeSum;
		this.expSum = expSum;
	}
	
	public BillingItem(Project project, int month,
			int year) {
		super();
		this.project = project;
		this.month = month;
		this.year = year;
		this.periodStartDate = getStartDateByPeriod(month, year);
		this.periodEndDate = getEndDateByPeriod(month, year);
	}

	public BillingItem(Project project, int month,
			int year, Float timeSum, Float expSum) {
		this(project, month, year);
		this.timeSum = timeSum;
		this.expSum = expSum;
	}
	
	private static Date getStartDateByPeriod(int month, int year){
		Calendar c = Calendar.getInstance();
		c.set(year, month, 1);
		return c.getTime();
	}

	private static Date getEndDateByPeriod(int month, int year){
		Calendar c = Calendar.getInstance();
		c.setLenient(false);
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
		return c.getTime();
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
//	public Date getPeriodStartDate() {
//		return periodStartDate;
//	}

	/**
	 * Дата завершения периода
	 * @return
	 */
//	public Date getPeriodEndDate() {
//		return periodEndDate;
//	}
	
	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
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

	public void setTimeSum(Float timeSum) {
		this.timeSum = timeSum;
	}

	public void setExpSum(Float expSum) {
		this.expSum = expSum;
	}
	
	

	
	
	
	
}
