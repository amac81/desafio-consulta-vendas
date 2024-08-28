package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	/* native SQL - H2 example
	 * 
	 * SELECT TB_SELLER.NAME AS SELLERNAME, SUM(AMOUNT) AS TOTAL 
	   FROM TB_SALES JOIN TB_SELLER ON (TB_SALES.SELLER_ID = TB_SELLER.ID) 
       WHERE DATE >= '2022-01-01' AND DATE <= '2022-06-30' 
       GROUP BY TB_SELLER.NAME 
	 * 
	 * */
		
	@Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(obj.seller.name, SUM(obj.amount)) "
			+ "FROM Sale obj "
			+ "WHERE obj.date >=(:minDate) AND obj.date <= (:maxDate) "			
			+ "GROUP BY obj.seller.name")
	List<SaleSummaryDTO> summarySearch(LocalDate minDate, LocalDate maxDate);

	
	/*
	 * SELECT TB_SALES.ID, TB_SALES.DATE, TB_SALES.AMOUNT, TB_SELLER.NAME AS sellerName
	   FROM TB_SALES JOIN TB_SELLER ON (TB_SALES.SELLER_ID = TB_SELLER.ID) 
       WHERE DATE >= '2022-05-01' AND DATE <= '2022-05-31'  AND TB_SELLER.NAME LIKE '%Odinson%'
	 * 
	 * */
	
	@Query("SELECT new com.devsuperior.dsmeta.dto.SaleReportDTO(obj.id, obj.date, obj.amount, obj.seller.name AS sellerName) "
			+ "FROM Sale obj "
			+ "WHERE obj.date BETWEEN (:minDate) AND obj.date (:maxDate) AND obj.seller.name LIKE UPPER(:name)")
	List<SaleReportDTO> reportSearch(LocalDate minDate, LocalDate maxDate, String name);
	
}

