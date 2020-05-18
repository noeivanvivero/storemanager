package com.noe.data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
public class Sucursal extends Address {
	
	private String code;
	@ManyToOne
	private ComercialUnit comercial_unit;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public ComercialUnit getComercial_unit() {
		return comercial_unit;
	}
	public void setComercial_unit(ComercialUnit comercial_unit) {
		this.comercial_unit = comercial_unit;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((comercial_unit == null) ? 0 : comercial_unit.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sucursal other = (Sucursal) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (comercial_unit == null) {
			if (other.comercial_unit != null)
				return false;
		} else if (!comercial_unit.equals(other.comercial_unit))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Sucursal [code=" + code + ", comercial_unit=" + comercial_unit + ", toString()=" + super.toString()
				+ "]";
	}
	
	
	public Sucursal(String country, String city, String urbanization, String street, String exterior, String interior,
			String description, String code, ComercialUnit comercial_unit) {
		super(country, city, urbanization, street, exterior, interior, description);
		this.code = code;
		this.comercial_unit = comercial_unit;
	}
	public Sucursal() {
		super();
	}
	
	
	
}
