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

import com.noe.data.EmployeeCategory;
import com.noe.repository.IRepositoryEmployeeCategory;
import com.noe.rest.assembly.EmployeeCategoryResourceAssembler;
import com.noe.rest.exception.EmployeeCategoryNotFoundException;


@RestController
@RequestMapping("api")
public class EmployeeCategoryRestController {
	@Autowired
	private IRepositoryEmployeeCategory repository;
	
	@Autowired 
	private EmployeeCategoryResourceAssembler assembler;
	
	@GetMapping("/employeeCategories")
	public CollectionModel<EntityModel<EmployeeCategory>> all(){
		
		List<EntityModel<EmployeeCategory>> collection = this.repository.findAll()
				.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return new CollectionModel<>(collection, 
				linkTo(methodOn(EmployeeCategoryRestController.class).all()).withSelfRel());
	}
	
	@PostMapping("/employeeCategories")
	public ResponseEntity<?> create(@RequestBody EmployeeCategory newEmployeeCategory)  throws URISyntaxException{
	    EntityModel<EmployeeCategory> entity = assembler.toModel(repository.save(newEmployeeCategory));
	    
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}
	
	@GetMapping("/employeeCategories/{id}")
	public EntityModel<EmployeeCategory> one(@PathVariable Long id){
		EmployeeCategory entity = this.repository.findById(id).orElseThrow(()->new EmployeeCategoryNotFoundException(id));
		return assembler.toModel(entity);
	}
	
	@PutMapping("/employeeCategories/{id}")
	public ResponseEntity<?> replace(@RequestBody EmployeeCategory newEmployeeCategory, @PathVariable Long id)throws URISyntaxException {
		EmployeeCategory updated = repository.findById(id)
				.map(employeeCategory -> {
					employeeCategory.setCode(newEmployeeCategory.getCode());
					employeeCategory.setDescription(newEmployeeCategory.getDescription());
						return repository.save(employeeCategory);
				})
				.orElseGet(() -> {
						newEmployeeCategory.setId(id);
						return repository.save(newEmployeeCategory);
				});
		EntityModel<EmployeeCategory> entity = assembler.toModel(updated);
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}

	@DeleteMapping("/employeeCategories/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		 repository.deleteById(id);
		 return ResponseEntity.noContent().build();
	}
}
