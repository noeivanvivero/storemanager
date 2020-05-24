package com.noe.data;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Address {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String country;
	private String city;
	private String urbanization;
	private String street;
	private String exterior;
	private String interior;
	private String description;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getUrbanization() {
		return urbanization;
	}
	public void setUrbanization(String urbanization) {
		this.urbanization = urbanization;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getExterior() {
		return exterior;
	}
	public void setExterior(String exterior) {
		this.exterior = exterior;
	}
	public String getInterior() {
		return interior;
	}
	public void setInterior(String interior) {
		this.interior = interior;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((exterior == null) ? 0 : exterior.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((interior == null) ? 0 : interior.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((urbanization == null) ? 0 : urbanization.hashCode());
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
		Address other = (Address) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (exterior == null) {
			if (other.exterior != null)
				return false;
		} else if (!exterior.equals(other.exterior))
			return false;
		if (id != other.id)
			return false;
		if (interior == null) {
			if (other.interior != null)
				return false;
		} else if (!interior.equals(other.interior))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (urbanization == null) {
			if (other.urbanization != null)
				return false;
		} else if (!urbanization.equals(other.urbanization))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Address [id=" + id + ", country=" + country + ", city=" + city + ", urbanization=" + urbanization
				+ ", street=" + street + ", exterior=" + exterior + ", interior=" + interior + ", description="
				+ description + "]";
	}
	
	public Address(String country, String city, String urbanization, String street, String exterior, String interior,
			String description) {
		super();
		this.country = country;
		this.city = city;
		this.urbanization = urbanization;
		this.street = street;
		this.exterior = exterior;
		this.interior = interior;
		this.description = description;
	}
	public Address() {
		super();
	}
	
	
	
}
