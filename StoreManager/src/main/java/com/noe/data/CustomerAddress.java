package com.noe.data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class CustomerAddress extends Address{
	
	@ManyToOne
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer user) {
		this.customer = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
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
		CustomerAddress other = (CustomerAddress) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "CustomerAddress [customer=" + customer + ", toString()=" + super.toString() + "]";
	}

	public CustomerAddress(String country, String city, String urbanization, String street, String exterior,
			String interior, String description, Customer customer) {
		super(country, city, urbanization, street, exterior, interior, description);
		this.customer = customer;
	}
	public CustomerAddress() {
		super();
	}
	
}
