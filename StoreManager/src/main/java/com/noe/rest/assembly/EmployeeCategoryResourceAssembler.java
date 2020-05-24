package com.noe.rest.assembly;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; 
import org.springframework.hateoas.EntityModel; //Resource
import org.springframework.hateoas.server.RepresentationModelAssembler; //ResourceAssembler
import org.springframework.stereotype.Component;

import com.noe.data.EmployeeCategory;
import com.noe.rest.controller.EmployeeCategoryRestController; 

@Component
public class EmployeeCategoryResourceAssembler implements RepresentationModelAssembler<EmployeeCategory, EntityModel<EmployeeCategory>>{

	@Override
	public EntityModel<EmployeeCategory> toModel(EmployeeCategory entity) {
		return new EntityModel<>(entity, 
				linkTo(methodOn(EmployeeCategoryRestController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(EmployeeCategoryRestController.class).all()).withRel("employeeCategories")
		);
	}

}
