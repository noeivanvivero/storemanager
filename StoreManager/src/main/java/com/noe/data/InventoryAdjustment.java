package com.noe.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class InventoryAdjustment {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@ManyToOne
	private Inventory inventory;
	@ManyToOne
	private Employee employee;
	@ManyToOne
	private AdjustmentType type;
	private Long quantity;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public AdjustmentType getType() {
		return type;
	}
	public void setType(AdjustmentType type) {
		this.type = type;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long cantidad) {
		this.quantity = cantidad;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((employee == null) ? 0 : employee.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((inventory == null) ? 0 : inventory.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		InventoryAdjustment other = (InventoryAdjustment) obj;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (employee == null) {
			if (other.employee != null)
				return false;
		} else if (!employee.equals(other.employee))
			return false;
		if (id != other.id)
			return false;
		if (inventory == null) {
			if (other.inventory != null)
				return false;
		} else if (!inventory.equals(other.inventory))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "InventoryAdjustment [id=" + id + ", inventory=" + inventory + ", employee=" + employee + ", type="
				+ type + ", cantidad=" + quantity + "]";
	}
	public InventoryAdjustment(Inventory inventory, Employee employee, AdjustmentType type, Long cantidad) {
		super();
		this.inventory = inventory;
		this.employee = employee;
		this.type = type;
		this.quantity = cantidad;
	}
	public InventoryAdjustment() {
		super();
	}
	
	
}
