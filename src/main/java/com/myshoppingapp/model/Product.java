package com.myshoppingapp.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Product")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Category", discriminatorType = DiscriminatorType.STRING)
public class Product implements Comparable<Product> {

	@Id
	@GeneratedValue
	@Column(name = "productId")
	private Integer productId;

	@Column(name = "prodName")
	private String prodName;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "price")
	private Double price;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "cart_id")
	@JsonBackReference
	private Cart cart;

	@Transient
	public Class<? extends Product> getDecriminatorValue() {
		return this.getClass();
	}

	public Integer getProductId() {
		return this.productId;
	}

	public void setProductId(final Integer productId) {
		this.productId = productId;
	}

	public String getProdName() {
		return this.prodName;
	}

	public void setProdName(final String prodName) {
		this.prodName = prodName;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(final Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	public Cart getCart() {
		return this.cart;
	}

	public void setCart(final Cart cart) {
		this.cart = cart;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.cart == null) ? 0 : this.cart.hashCode());
		result = prime * result + ((this.price == null) ? 0 : this.price.hashCode());
		result = prime * result + ((this.prodName == null) ? 0 : this.prodName.hashCode());
		result = prime * result + ((this.productId == null) ? 0 : this.productId.hashCode());
		result = prime * result + ((this.quantity == null) ? 0 : this.quantity.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Product other = (Product) obj;
		if (this.cart == null) {
			if (other.cart != null) {
				return false;
			}
		} else if (!this.cart.equals(other.cart)) {
			return false;
		}
		if (this.price == null) {
			if (other.price != null) {
				return false;
			}
		} else if (!this.price.equals(other.price)) {
			return false;
		}
		if (this.prodName == null) {
			if (other.prodName != null) {
				return false;
			}
		} else if (!this.prodName.equals(other.prodName)) {
			return false;
		}
		if (this.productId == null) {
			if (other.productId != null) {
				return false;
			}
		} else if (!this.productId.equals(other.productId)) {
			return false;
		}
		if (this.quantity == null) {
			if (other.quantity != null) {
				return false;
			}
		} else if (!this.quantity.equals(other.quantity)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Product [productId=" + this.productId + ", prodName=" + this.prodName + ", quantity=" + this.quantity
				+ ", price=" + this.price + ", cart=" + this.cart + "]";
	}

	@Override
	public int compareTo(final Product arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
