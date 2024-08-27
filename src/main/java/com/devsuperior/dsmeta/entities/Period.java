package com.devsuperior.dsmeta.entities;

import java.time.LocalDate;

public class Period {
	
	private LocalDate minDate;
	private LocalDate maxDate;
	
	public Period(LocalDate minDate, LocalDate maxDate) {
		this.minDate = minDate;
		this.maxDate = maxDate;
	}

	public LocalDate getMinDate() {
		return minDate;
	}

	public void setMinDate(LocalDate minDate) {
		this.minDate = minDate;
	}

	public LocalDate getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(LocalDate maxDate) {
		this.maxDate = maxDate;
	}

	@Override
	public String toString() {
		return "Period [minDate=" + minDate + ", maxDate=" + maxDate + "]";
	}

}
