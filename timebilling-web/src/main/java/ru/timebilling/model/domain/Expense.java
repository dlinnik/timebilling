package ru.timebilling.model.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Expense extends BaseRecordEntity{
	
	@Column(name = "spentmoney", nullable = false, scale = 2)
	private BigDecimal spentMoney;
	  
	public BigDecimal getSpentMoney() {
		return spentMoney;
	}

	public void setSpentMoney(BigDecimal spentMoney) {
		this.spentMoney = spentMoney;
	}
}
