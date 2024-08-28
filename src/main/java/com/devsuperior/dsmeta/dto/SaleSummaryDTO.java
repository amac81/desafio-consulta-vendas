package com.devsuperior.dsmeta.dto;

public class SaleSummaryDTO {
	private String sellerName;
	private Double total;
	
	public SaleSummaryDTO (String name, Double sum) {
		sellerName = name;
		total = sum;
	}

	public String getSellerName() {
		return sellerName;
	}

	public Double getTotal() {
		return total;
	}
}
