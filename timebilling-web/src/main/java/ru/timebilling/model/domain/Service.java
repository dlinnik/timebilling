package ru.timebilling.model.domain;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Service extends BaseRecordEntity{

	@Column(name = "spenttime", nullable = false, scale = 2)
	private BigDecimal spentTime;

	@Column(name = "spentmoney", scale = 2)
	private BigDecimal spentMoney;
	
	public BigDecimal getSpentTime() {
		return spentTime;
	}

	public void setSpentTime(BigDecimal spentTime) {
		this.spentTime = spentTime;
	}

	public BigDecimal getSpentMoney() {
		return spentMoney;
	}

	public void setSpentMoney(BigDecimal spentMoney) {
		this.spentMoney = spentMoney;
	}
	
	
}
