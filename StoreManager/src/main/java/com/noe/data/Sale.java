package com.noe.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Sale {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@ManyToOne
	private Employee employee;
	@ManyToOne
	private Sucursal sucursal;
	
	private LocalDate date;
	@ManyToOne
	private Customer customer;
	@OneToMany(mappedBy = "sale",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<SalesDetail> details = new ArrayList<>();
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<SalesDetail> getDetails() {
		return details;
	}
	public void setDetails(List<SalesDetail> details) {
		this.details = details;
		this.details.forEach((SalesDetail detail)->{
			detail.setSale(this);
		});
	}
	
	public void addDetail(SalesDetail detail) {
		this.details.add(detail);
		detail.setSale(this);
	}
	public void removeDetail(SalesDetail detail) {
		this.details.remove(detail);
		detail.setSale(null);
	}
	@Override
	public String toString() {
		return "Sale [id=" + id + ", employee=" + employee + ", sucursal=" + sucursal + ", date=" + date + ", customer="
				+ customer + ", details=" + details + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + ((employee == null) ? 0 : employee.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((sucursal == null) ? 0 : sucursal.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sale other = (Sale) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (employee == null) {
			if (other.employee != null)
				return false;
		} else if (!employee.equals(other.employee))
			return false;
		if (id != other.id)
			return false;
		if (sucursal == null) {
			if (other.sucursal != null)
				return false;
		} else if (!sucursal.equals(other.sucursal))
			return false;
		return true;
	}
	public Sale(Employee employee, Sucursal sucursal, LocalDate date, Customer customer, List<SalesDetail> details) {
		super();
		this.employee = employee;
		this.sucursal = sucursal;
		this.date = date;
		this.customer = customer;
		this.details = details;
	}
	public Sale() {
		super();
	}
	
}
