package com.noe.rest.assembly;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; 
import org.springframework.hateoas.EntityModel; //Resource
import org.springframework.hateoas.server.RepresentationModelAssembler; //ResourceAssembler
import org.springframework.stereotype.Component;

import com.noe.data.SalesDetail;
import com.noe.rest.controller.SalesDetailRestController; 

@Component
public class SalesDetailResourceAssembler implements RepresentationModelAssembler<SalesDetail, EntityModel<SalesDetail>>{

	@Override
	public EntityModel<SalesDetail> toModel(SalesDetail entity) {
		return new EntityModel<>(entity, 
				linkTo(methodOn(SalesDetailRestController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(SalesDetailRestController.class).all()).withRel("salesDetails")
		);
	}

}
