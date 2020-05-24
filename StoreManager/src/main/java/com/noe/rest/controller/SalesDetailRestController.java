package com.noe.rest.controller;



import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; 
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noe.data.SalesDetail;
import com.noe.repository.IRepositorySalesDetail;
import com.noe.rest.assembly.SalesDetailResourceAssembler;
import com.noe.rest.exception.SalesDetailNotFoundException;


@RestController
@RequestMapping("api")
public class SalesDetailRestController {
	@Autowired
	private IRepositorySalesDetail repository;
	
	@Autowired 
	private SalesDetailResourceAssembler assembler;
	
	@GetMapping("/salesDetails")
	public CollectionModel<EntityModel<SalesDetail>> all(){
		
		List<EntityModel<SalesDetail>> collection = this.repository.findAll()
				.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return new CollectionModel<>(collection, 
				linkTo(methodOn(SalesDetailRestController.class).all()).withSelfRel());
	}
	
	@PostMapping("/salesDetails")
	public ResponseEntity<?> create(@RequestBody SalesDetail newSalesDetail)  throws URISyntaxException{
	    EntityModel<SalesDetail> entity = assembler.toModel(repository.save(newSalesDetail));
	    
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}
	
	@GetMapping("/salesDetails/{id}")
	public EntityModel<SalesDetail> one(@PathVariable Long id){
		SalesDetail entity = this.repository.findById(id).orElseThrow(()->new SalesDetailNotFoundException(id));
		return assembler.toModel(entity);
	}
	
	@PutMapping("/salesDetails/{id}")
	public ResponseEntity<?> replace(@RequestBody SalesDetail newSalesDetail, @PathVariable Long id)throws URISyntaxException {
		SalesDetail updated = repository.findById(id)
				.map(salesDetail -> {
					salesDetail.setDiscount(newSalesDetail.getDiscount());
					salesDetail.setProduct(newSalesDetail.getProduct());
					salesDetail.setQuantity(newSalesDetail.getQuantity());
					salesDetail.setSale(newSalesDetail.getSale());
					
					return repository.save(salesDetail);
				})
				.orElseGet(() -> {
					newSalesDetail.setId(id);
					return repository.save(newSalesDetail);
				});
		EntityModel<SalesDetail> entity = assembler.toModel(updated);
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}

	@DeleteMapping("/salesDetails/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		 repository.deleteById(id);
		 return ResponseEntity.noContent().build();
	}
}
