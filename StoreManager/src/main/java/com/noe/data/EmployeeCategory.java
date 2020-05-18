package com.noe.data;

import javax.persistence.Entity;

@Entity
public class EmployeeCategory extends Category{
	
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

	public EmployeeCategory() {
		super();
	}

	@Override
	public String toString() {
		return "EmployeeCategory [toString()=" + super.toString() + "]";
	}
	
	
}
