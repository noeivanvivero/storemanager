package com.noe.rest.assembly;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; 
import org.springframework.hateoas.EntityModel; //Resource
import org.springframework.hateoas.server.RepresentationModelAssembler; //ResourceAssembler
import org.springframework.stereotype.Component;

import com.noe.data.Product;
import com.noe.rest.controller.ProductRestController; 

@Component
public class ProductResourceAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>>{

	@Override
	public EntityModel<Product> toModel(Product entity) {
		return new EntityModel<>(entity, 
				linkTo(methodOn(ProductRestController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(ProductRestController.class).all()).withRel("products")
		);
	}

}
