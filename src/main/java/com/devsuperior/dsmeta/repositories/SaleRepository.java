package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
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
	
	
	@Query("SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(obj) FROM Sale obj ")
	Page<SaleMinDTO> summarySearch(LocalDate minDate, LocalDate maxDate, Pageable pageable);

}

