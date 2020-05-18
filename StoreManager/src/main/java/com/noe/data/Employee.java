package com.noe.data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
public class Employee extends User {
	@ManyToOne
	private EmployeeCategory category;
	@ManyToOne
	private Sucursal sucursal;
	
	public EmployeeCategory getCategory() {
		return category;
	}
	public void setCategory(EmployeeCategory category) {
		this.category = category;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((sucursal == null) ? 0 : sucursal.hashCode());
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
		Employee other = (Employee) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (sucursal == null) {
			if (other.sucursal != null)
				return false;
		} else if (!sucursal.equals(other.sucursal))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Employee [category=" + category + ", sucursal=" + sucursal + ", toString()=" + super.toString() + "]";
	}
	
	public Employee(String name, String lastname, String phone, String email, String rfc, String password,
			EmployeeCategory category, Sucursal sucursal) {
		super(name, lastname, phone, email, rfc, password);
		this.category = category;
		this.sucursal = sucursal;
	}
	public Employee() {
		super();
	}
	
	
	
}
