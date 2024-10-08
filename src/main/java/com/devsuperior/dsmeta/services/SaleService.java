package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.exceptions.BusinessException;
import com.devsuperior.dsmeta.exceptions.ParseException;
import com.devsuperior.dsmeta.exceptions.ResourceNotFoundException;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService<T> {

	@Autowired
	private SaleRepository repository;
	
	@Transactional(readOnly = true)
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.orElseThrow(
				()-> new ResourceNotFoundException("Recurso não encontrado"));
		
		
		return new SaleMinDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public Page<SaleMinDTO> findAll(Pageable pageable) {
		
		Page<Sale> result = repository.findAll(pageable);
		
		//with lambda expression
		return result.map(x -> new SaleMinDTO(x));
	}
	
	@Transactional(readOnly = true)
	public Page<SaleMinDTO> find(PageRequest pageRequest) {
		Page<Sale> list = repository.findAll(pageRequest);
		return list.map(x -> new SaleMinDTO(x));
	}
	
	@Transactional(readOnly = true)
	public List<?> findAllBetweenDates(Map <String, String> params) {
		
		LocalDate maxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate minDate = maxDate.minusYears(1L);		
				
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		try {	
			if(params.containsKey("maxDateStr")) 
			{			
				if(!params.get("maxDateStr").isBlank()) {
					maxDate = LocalDate.parse(params.get("maxDateStr"), dtf);
				}
			} 
		
			if(params.containsKey("minDateStr")){
				if(!params.get("minDateStr").isBlank()) {
					minDate = LocalDate.parse(params.get("minDateStr"), dtf); 
				}						
			}
			
			if(maxDate.isBefore(minDate) || minDate.isAfter(maxDate)) {
				throw new BusinessException("Período inválido");
			}
							
		}catch(DateTimeParseException e) {
			throw new ParseException("Formato de data inválido.");
		}
			
		
		if(params.containsKey("report")) {
			return repository.reportSearch(minDate, maxDate, params.get("name"));
		}
		
		return repository.summarySearch(minDate, maxDate);
		
	}
		
}
