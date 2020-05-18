package com.noe.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Customer extends User{
	@OneToMany(mappedBy = "customer",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<CustomerAddress> addresses = new ArrayList<>();

	public List<CustomerAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<CustomerAddress> addresses) {
		this.addresses = addresses;
		this.addresses.forEach((CustomerAddress address)->{
			address.setCustomer(this);
		});
	}
	
	public void addAddress(CustomerAddress address) {
		this.addresses.add(address);
		address.setCustomer(this);
	}
	public void removeAddress(CustomerAddress address) {
		this.addresses.remove(address);
		address.setCustomer(null);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((addresses == null) ? 0 : addresses.hashCode());
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
		Customer other = (Customer) obj;
		if (addresses == null) {
			if (other.addresses != null)
				return false;
		} else if (!addresses.equals(other.addresses))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [addresses=" + addresses + ", toString()=" + super.toString() + "]";
	}

	

	public Customer(String name, String lastname, String phone, String email, String rfc, String password,
			List<CustomerAddress> addresses) {
		super(name, lastname, phone, email, rfc, password);
		this.addresses = addresses;
	}

	public Customer() {
		super();
	}
	
}
