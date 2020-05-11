package com.myshoppingapp.service;

import java.util.List;

import com.myshoppingapp.exceptions.DBTransactionException;
import com.myshoppingapp.model.Cart;
import com.myshoppingapp.model.Product;

public interface CartService {

	Cart addProductToCart(Cart cart) throws DBTransactionException;

	void updateProductByName(String prodName, Long cartId, int quantity, int productId) throws DBTransactionException;

	void deleteSpecificProductFromCart(Long cartId, String prodName) throws DBTransactionException;

	Product retrieveProductFromUserCart(Long cartId, String bookname) throws DBTransactionException;

	List<Product> retrieveAllProducts(Long cartId) throws DBTransactionException;

}
