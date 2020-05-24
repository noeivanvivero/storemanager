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

import com.noe.data.Sucursal;
import com.noe.repository.IRepositorySucursal;
import com.noe.rest.assembly.SucursalResourceAssembler;
import com.noe.rest.exception.SucursalNotFoundException;


@RestController
@RequestMapping("api")
public class SucursalRestController {
	@Autowired
	private IRepositorySucursal repository;
	
	@Autowired 
	private SucursalResourceAssembler assembler;
	
	@GetMapping("/sucursals")
	public CollectionModel<EntityModel<Sucursal>> all(){
		
		List<EntityModel<Sucursal>> collection = this.repository.findAll()
				.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return new CollectionModel<>(collection, 
				linkTo(methodOn(SucursalRestController.class).all()).withSelfRel());
	}
	
	@PostMapping("/sucursals")
	public ResponseEntity<?> create(@RequestBody Sucursal newSucursal)  throws URISyntaxException{
	    EntityModel<Sucursal> entity = assembler.toModel(repository.save(newSucursal));	    
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}
	
	@GetMapping("/sucursals/{id}")
	public EntityModel<Sucursal> one(@PathVariable Long id){
		Sucursal entity = this.repository.findById(id).orElseThrow(()->new SucursalNotFoundException(id));
		return assembler.toModel(entity);
	}
	
	@PutMapping("/sucursals/{id}")
	public ResponseEntity<?> replace(@RequestBody Sucursal newSucursal, @PathVariable Long id)throws URISyntaxException {
		Sucursal updated = repository.findById(id)
				.map(sucursal -> {
						sucursal.setCity(newSucursal.getCity());
						sucursal.setCode(newSucursal.getCode());
						sucursal.setCountry(newSucursal.getCountry());
						sucursal.setComercial_unit(newSucursal.getComercial_unit());
						sucursal.setDescription(newSucursal.getDescription());
						sucursal.setExterior(newSucursal.getExterior());
						sucursal.setInterior(newSucursal.getInterior());
						sucursal.setStreet(newSucursal.getStreet());
						sucursal.setUrbanization(newSucursal.getUrbanization());
						return repository.save(sucursal);
				})
				.orElseGet(() -> {
						newSucursal.setId(id);
						return repository.save(newSucursal);
				});
		EntityModel<Sucursal> entity = assembler.toModel(updated);
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}

	@DeleteMapping("/sucursals/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		 repository.deleteById(id);
		 return ResponseEntity.noContent().build();
	}
}
