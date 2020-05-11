package com.myshoppingapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshoppingapp.dao.ProductRepository;
import com.myshoppingapp.exceptions.DBTransactionException;
import com.myshoppingapp.model.Cart;
import com.myshoppingapp.model.Product;
import com.myshoppingapp.service.CartService;

@Service
@Transactional
public class CartServiceImpl implements CartService {

	@Autowired
	ProductRepository productRepository;

	@Override
	public Cart addProductToCart(final Cart cart) throws DBTransactionException {
		return this.productRepository.save(cart);
	}

	@Override
	public void updateProductByName(final String prodName, final Long cartId, final int quantity, final int productId)
			throws DBTransactionException {
		this.productRepository.updateProductByName(prodName, cartId, quantity, productId);
	}

	@Override
	public void deleteSpecificProductFromCart(final Long cartId, final String prodName) throws DBTransactionException {
		this.productRepository.deleteSpecificProductFromCart(cartId, prodName);
	}

	@Override
	public Product retrieveProductFromUserCart(final Long cartId, final String bookname) throws DBTransactionException {
		return this.productRepository.getProductByName(cartId, bookname);
	}

	@Override
	public List<Product> retrieveAllProducts(final Long cartId) throws DBTransactionException {
		return this.productRepository.findAllProducts(cartId);
	}

}
