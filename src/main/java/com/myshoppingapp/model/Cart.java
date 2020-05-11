package com.myshoppingapp.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Cart")
public class Cart implements Comparable<Cart> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cart_id")
	private Long cartId;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "cart", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Product> productList;

	@Transient
	private Double totalPrice;

	public Long getCartId() {
		return this.cartId;
	}

	public void setCartId(final Long cartId) {
		this.cartId = cartId;
	}

	public List<Product> getProductList() {
		return this.productList;
	}

	public void setProductList(final List<Product> productList) {
		this.productList = productList;
	}

	public Double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(final Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.cartId == null) ? 0 : this.cartId.hashCode());
		result = prime * result + ((this.productList == null) ? 0 : this.productList.hashCode());
		result = prime * result + ((this.totalPrice == null) ? 0 : this.totalPrice.hashCode());
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
		final Cart other = (Cart) obj;
		if (this.cartId == null) {
			if (other.cartId != null) {
				return false;
			}
		} else if (!this.cartId.equals(other.cartId)) {
			return false;
		}
		if (this.productList == null) {
			if (other.productList != null) {
				return false;
			}
		} else if (!this.productList.equals(other.productList)) {
			return false;
		}
		if (this.totalPrice == null) {
			if (other.totalPrice != null) {
				return false;
			}
		} else if (!this.totalPrice.equals(other.totalPrice)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Cart [cartId=" + this.cartId + ", productList=" + this.productList + ", totalPrice=" + this.totalPrice
				+ "]";
	}

	@Override
	public int compareTo(final Cart arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
