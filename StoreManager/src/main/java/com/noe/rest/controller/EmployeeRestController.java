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

import com.noe.data.Employee;
import com.noe.repository.IRepositoryEmployee;
import com.noe.rest.assembly.EmployeeResourceAssembler;
import com.noe.rest.exception.EmployeeNotFoundException;


@RestController
@RequestMapping("api")
public class EmployeeRestController {
	@Autowired
	private IRepositoryEmployee repository;
	
	@Autowired 
	private EmployeeResourceAssembler assembler;
	
	@GetMapping("/employees")
	public CollectionModel<EntityModel<Employee>> all(){
		
		List<EntityModel<Employee>> collection = this.repository.findAll()
				.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return new CollectionModel<>(collection, 
				linkTo(methodOn(EmployeeRestController.class).all()).withSelfRel());
	}
	
	@PostMapping("/employees")
	public ResponseEntity<?> create(@RequestBody Employee newEmployee)  throws URISyntaxException{
	    EntityModel<Employee> entity = assembler.toModel(repository.save(newEmployee));
	    
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}
	
	@GetMapping("/employees/{id}")
	public EntityModel<Employee> one(@PathVariable Long id){
		Employee entity = this.repository.findById(id).orElseThrow(()->new EmployeeNotFoundException(id));
		return assembler.toModel(entity);
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<?> replace(@RequestBody Employee newEmployee, @PathVariable Long id)throws URISyntaxException {
		Employee updated = repository.findById(id)
				.map(employee -> {
						employee.setCategory(newEmployee.getCategory());
						employee.setEmail(newEmployee.getEmail());
						employee.setLastname(newEmployee.getLastname());
						employee.setName(newEmployee.getName());
						employee.setPassword(newEmployee.getPassword());
						employee.setPhone(newEmployee.getPhone());
						employee.setRfc(newEmployee.getRfc());
						employee.setSucursal(newEmployee.getSucursal());
						return repository.save(employee);
				})
				.orElseGet(() -> {
						newEmployee.setId(id);
						return repository.save(newEmployee);
				});
		EntityModel<Employee> entity = assembler.toModel(updated);
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		 repository.deleteById(id);
		 return ResponseEntity.noContent().build();
	}
}
