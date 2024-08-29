package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
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
	public List<?> findAllBetweenDates(String minDateStr, String maxDateStr, String name, String type, boolean periodIsPresent) {
		LocalDate maxDate;
		LocalDate minDate;
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		
		if(!periodIsPresent) {
			maxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			minDate = maxDate.minusYears(1L);			
		}else {	
			try {
				maxDate = maxDateStr.isBlank() ? 
						LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()) : 
						LocalDate.parse(maxDateStr, dtf);
				
				minDate = minDateStr.isBlank() ? maxDate.minusYears(1L) : LocalDate.parse(minDateStr, dtf); 
				
			}catch(DateTimeParseException e) {
				throw new ParseException("Formato de data inválido.");
			}
			
		}
		
		System.out.println(name);
		
		return type.equals("report") ? repository.reportSearch(minDate, maxDate, name) : repository.summarySearch(minDate, maxDate);		
		
	}
	
		
	
	
}
