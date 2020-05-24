package com.noe.rest.assembly;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; 
import org.springframework.hateoas.EntityModel; //Resource
import org.springframework.hateoas.server.RepresentationModelAssembler; //ResourceAssembler
import org.springframework.stereotype.Component;

import com.noe.data.Employee;
import com.noe.rest.controller.EmployeeRestController; 

@Component
public class EmployeeResourceAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>>{

	@Override
	public EntityModel<Employee> toModel(Employee entity) {
		return new EntityModel<>(entity, 
				linkTo(methodOn(EmployeeRestController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(EmployeeRestController.class).all()).withRel("employees")
		);
	}

}
