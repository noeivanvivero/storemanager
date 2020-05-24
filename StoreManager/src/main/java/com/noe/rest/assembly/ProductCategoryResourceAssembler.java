package com.noe.rest.assembly;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; 
import org.springframework.hateoas.EntityModel; //Resource
import org.springframework.hateoas.server.RepresentationModelAssembler; //ResourceAssembler
import org.springframework.stereotype.Component;

import com.noe.data.ProductCategory;
import com.noe.rest.controller.ProductCategoryRestController; 

@Component
public class ProductCategoryResourceAssembler implements RepresentationModelAssembler<ProductCategory, EntityModel<ProductCategory>>{

	@Override
	public EntityModel<ProductCategory> toModel(ProductCategory entity) {
		return new EntityModel<>(entity, 
				linkTo(methodOn(ProductCategoryRestController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(ProductCategoryRestController.class).all()).withRel("productCategories")
		);
	}

}
