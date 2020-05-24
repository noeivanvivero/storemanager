package com.noe.rest.assembly;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; 
import org.springframework.hateoas.EntityModel; //Resource
import org.springframework.hateoas.server.RepresentationModelAssembler; //ResourceAssembler
import org.springframework.stereotype.Component;

import com.noe.data.Sucursal;
import com.noe.rest.controller.SucursalRestController; 

@Component
public class SucursalResourceAssembler implements RepresentationModelAssembler<Sucursal, EntityModel<Sucursal>>{

	@Override
	public EntityModel<Sucursal> toModel(Sucursal entity) {
		return new EntityModel<>(entity, 
				linkTo(methodOn(SucursalRestController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(SucursalRestController.class).all()).withRel("sucursals")
		);
	}

}
