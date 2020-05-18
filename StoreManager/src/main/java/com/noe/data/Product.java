package com.noe.data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
	private Double salePrice;
	private Double buyPrice;
	@ManyToOne
	private ProductCategory category;
	@ManyToOne
	private UnitOfMeasure uom;
	@ManyToMany(cascade = {
		    CascadeType.PERSIST,
		    CascadeType.MERGE
	})
	@JoinTable(name = "product_tax",
	    joinColumns = @JoinColumn(name = "product_id"),
	    inverseJoinColumns = @JoinColumn(name = "tax_id")
	)
	private Set<Tax> taxes = new HashSet<>();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
	public Double getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}
	public ProductCategory getCategory() {
		return category;
	}
	public void setCategory(ProductCategory category) {
		this.category = category;
	}
	public UnitOfMeasure getUom() {
		return uom;
	}
	public void setUom(UnitOfMeasure uom) {
		this.uom = uom;
	}
	
	public Set<Tax> getTaxes() {
		return taxes;
	}
	public void setTaxes(Set<Tax> taxes) {
		this.taxes = taxes;
		this.taxes.forEach((Tax tax)->{
			tax.getProducts().add(this);
		});
	}
	
	public void addTax(Tax tax) {
		this.taxes.add(tax);
		tax.getProducts().add(this);
	}
	
	public void removeTax(Tax tax) {
		this.taxes.remove(tax);
		tax.getProducts().remove(this);
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", salePrice=" + salePrice + ", buyPrice=" + buyPrice
				+ ", category=" + category + ", uom=" + uom + ", taxes=" + taxes + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buyPrice == null) ? 0 : buyPrice.hashCode());
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((salePrice == null) ? 0 : salePrice.hashCode());
		result = prime * result + ((taxes == null) ? 0 : taxes.hashCode());
		result = prime * result + ((uom == null) ? 0 : uom.hashCode());
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
		Product other = (Product) obj;
		if (buyPrice == null) {
			if (other.buyPrice != null)
				return false;
		} else if (!buyPrice.equals(other.buyPrice))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (salePrice == null) {
			if (other.salePrice != null)
				return false;
		} else if (!salePrice.equals(other.salePrice))
			return false;
		if (taxes == null) {
			if (other.taxes != null)
				return false;
		} else if (!taxes.equals(other.taxes))
			return false;
		if (uom == null) {
			if (other.uom != null)
				return false;
		} else if (!uom.equals(other.uom))
			return false;
		return true;
	}
	public Product(String name, Double salePrice, Double buyPrice, ProductCategory category, UnitOfMeasure uom,
			Set<Tax> taxes) {
		super();
		this.name = name;
		this.salePrice = salePrice;
		this.buyPrice = buyPrice;
		this.category = category;
		this.uom = uom;
		this.taxes = taxes;
	}
	public Product() {
		super();
	}
	
	
	
	
}
