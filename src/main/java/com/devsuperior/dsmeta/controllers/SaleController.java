package com.devsuperior.dsmeta.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.devsuperior.dsmeta.services.SaleService;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService<?> service;
	
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
	public ResponseEntity<?> getReport(
			@RequestParam(value = "minDate", required = false) String minDateStr,
			@RequestParam(value = "maxDate", required = false) String maxDateStr,
			@RequestParam(value = "name", defaultValue = "") String name) {
		
		Map <String, String> reportParams = new HashMap<>();
		
		reportParams.put("name", name);
		reportParams.put("report", "report");
		
		if(!(minDateStr == null && maxDateStr == null)) 
		{
			reportParams.put("minDateStr", minDateStr);
			reportParams.put("maxDateStr", maxDateStr);
		}
		
		List <?> list = service.findAllBetweenDates(reportParams);
		
		return ResponseEntity.ok(list);
	}
	
	/*@GetMapping(value = "/report")
	public ResponseEntity<?> getReport(
			@RequestParam(value = "minDate", required = false) String minDateStr,
			@RequestParam(value = "maxDate", required = false) String maxDateStr,
			@RequestParam(value = "name", defaultValue = "") String name) {
		
		List <?> list = null;
		
		if(minDateStr == null && maxDateStr == null) {
			list = service.findAllBetweenDates("", "", "", "report", false);
		}else {
			list = service.findAllBetweenDates(minDateStr, maxDateStr, name, "report", true);
		}
		
		return ResponseEntity.ok(list);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<?> getSummary(
			@RequestParam(value = "minDate", required = false) String minDateStr,
			@RequestParam(value = "maxDate", required = false ) String maxDateStr) {
		
		List<?> list = null;
		
		if(minDateStr == null && maxDateStr == null) {
			list = service.findAllBetweenDates("", "", "", "summary", false);
		}
		else {
			list = service.findAllBetweenDates(minDateStr, maxDateStr, "", "summary", true);
		}
		
		
		return ResponseEntity.ok(list);
	}
	*/
	
}
