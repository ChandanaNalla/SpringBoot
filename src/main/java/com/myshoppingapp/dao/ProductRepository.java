package com.myshoppingapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.myshoppingapp.model.Cart;
import com.myshoppingapp.model.Product;

public interface ProductRepository extends JpaRepository<Cart, Long> {

	@Query("SELECT prod FROM Product prod WHERE prod.prodName = :prodName AND prod.cart.cartId = :cartId")
	Product getProductByName(@Param("cartId") Long cartId, @Param("prodName") String name);

	@Query("UPDATE Product prod SET prod.quantity= :quantity WHERE prod.prodName = :prodName AND prod.cart.cartId= :cartId and prod.productId = :prodId")
	public void updateProductByName(@Param("prodName") String prodName, @Param("cartId") Long cartId,
			@Param("quantity") int quantity, @Param("prodId") int prodId);

	@Modifying
	@Transactional
	@Query("DELETE FROM Product prod WHERE prod.cart.cartId = :cartId AND prod.prodName = :prodName")
	public void deleteSpecificProductFromCart(@Param("cartId") Long cartId, @Param("prodName") String prodName);

	@Query("SELECT prod FROM Product prod WHERE prod.cart.cartId = :cartId")
	public List<Product> findAllProducts(Long cartId);

}
