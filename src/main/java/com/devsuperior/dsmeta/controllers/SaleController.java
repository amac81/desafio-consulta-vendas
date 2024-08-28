package com.devsuperior.dsmeta.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.services.SaleService;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}
	
	
	@GetMapping
	public ResponseEntity<Page<SaleMinDTO>> getAll(Pageable pageable) {
		Page<SaleMinDTO> page = service.findAll(pageable); 
		
		return ResponseEntity.ok(page);
	}
	

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleMinDTO>> getReport(
			@RequestParam(value = "minDate", defaultValue = "0") String minDate,
			@RequestParam(value = "maxDate", defaultValue = "10") String maxDate,
			@RequestParam(value = "name", defaultValue = "") String name) {
		
		//PageRequest pageRequest = PageRequest.of(minDate, maxDate, name);
		//Page<SaleMinDTO> list = service.find(pageRequest);
		
		//return ResponseEntity.ok(list);
		
		return null;
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List<SaleSummaryDTO>> getSummary(
			@RequestParam(value = "minDate", defaultValue = "") String minDateStr,
			@RequestParam(value = "maxDate", defaultValue = "") String maxDateStr, Pageable pageable) {

		
		List <SaleSummaryDTO> list = service.findAllBetweenDates(minDateStr, maxDateStr);
		
		
		return ResponseEntity.ok(list);
	}
}
