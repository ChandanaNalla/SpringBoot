package com.myshoppingapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myshoppingapp.exceptions.CartException;
import com.myshoppingapp.exceptions.DBTransactionException;
import com.myshoppingapp.exceptions.UserAccountException;
import com.myshoppingapp.model.Apparal;
import com.myshoppingapp.model.Book;
import com.myshoppingapp.model.Cart;
import com.myshoppingapp.model.Product;
import com.myshoppingapp.model.User;
import com.myshoppingapp.service.impl.CartServiceImpl;
import com.myshoppingapp.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/shopping-app")
public class ShoppingCartController {

	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	CartServiceImpl cartServiceImpl;

	private static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

	@PostMapping("/createUser")
	public String createUser(@RequestBody final User user) {
		logger.info("ShoppingCartController::createUser::entring method");
		String response = null;
		try {
			if (StringUtils.isEmpty(user)) {
				response = "Empty User request";
				throw new UserAccountException("Empty User request");
			} else {
				this.userServiceImpl.createUser(user);
				logger.info("ShoppingCartController::createUser::exiting method");
				response = "User saved Successfully";
			}
		} catch (final Exception e) {
			logger.error("Error Occured while saving. Pls try again::" + e.toString());
			response = "Error Occured while saving. Pls try again";
		}
		logger.info("ShoppingCartController::createUser::exiting method");
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/getAllUser")
	public List<User> getAllUser() throws UserAccountException {
		logger.info("ShoppingCartController::getAllUser::entering method");
		List<User> userList = new ArrayList();
		try {
			userList = this.userServiceImpl.getAllUser();
		} catch (final DBTransactionException dbe) {
			logger.error("Error occured while getting user from DB" + dbe);
			new ResponseEntity("Error occured while getting user from DB", HttpStatus.EXPECTATION_FAILED);
		}
		if (userList.size() > 0) {
			logger.info("ShoppingCartController::getAllUser::exiting method");
			return userList;
		} else {
			logger.info("ShoppingCartController::getAllUser::exiting method");
			throw new UserAccountException("User List empty");
			// return null;
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/addBook")
	public ResponseEntity<String> addBook(@RequestBody final Book book, @RequestParam("userName") final String userName)
			throws CartException {
		logger.info("ShoppingCartController::addBook::entering method");
		try {
			final User user = this.userServiceImpl.getUserByName(userName);
			if (user != null && book != null) {
				final Cart cart = new Cart();
				cart.setCartId(user.getCart().getCartId());
				final Product product = this.cartServiceImpl.retrieveProductFromUserCart(user.getCart().getCartId(),
						book.getProdName());
				if (product != null) {
					final int quantity = product.getQuantity() + book.getQuantity();
					this.cartServiceImpl.updateProductByName(book.getProdName(), user.getCart().getCartId(), quantity,
							product.getProductId());
				} else {
					this.checkBookQuantity(book, cart);
				}
			} else {
				logger.error("ShoppingCartController::addBook::empty request body");
				throw new CartException("Empty request body");
			}
		} catch (final DBTransactionException dbe) {
			logger.error("Error occured while adding to cart" + dbe);
			new ResponseEntity("Error occured while adding to cart", HttpStatus.EXPECTATION_FAILED);
		}
		logger.info("ShoppingCartController::addBook::exiting method");
		return new ResponseEntity("Book added to cart", HttpStatus.CREATED);
	}

	private void checkBookQuantity(final Book book, final Cart cart) throws DBTransactionException {
		final List<Product> prodList = this.getProductList(book);
		if (prodList.size() > 0) {
			prodList.get(0).setCart(cart);
			cart.setProductList(prodList);
			this.cartServiceImpl.addProductToCart(cart);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/addApparal")
	public ResponseEntity<String> addApparal(@RequestBody final Apparal apparal,
			@RequestParam("userName") final String userName) throws CartException {
		logger.info("ShoppingCartController::addApparal::entering method");
		try {
			final User user = this.userServiceImpl.getUserByName(userName);
			if (user != null && apparal != null) {
				final Cart cart = new Cart();
				cart.setCartId(user.getCart().getCartId());
				final Product product = this.cartServiceImpl.retrieveProductFromUserCart(user.getCart().getCartId(),
						apparal.getProdName());
				if (product != null) {
					final int quantity = product.getQuantity() + apparal.getQuantity();
					this.cartServiceImpl.updateProductByName(apparal.getProdName(), user.getCart().getCartId(),
							quantity, product.getProductId());
				} else {
					this.checkApparalQuantity(apparal, cart);
				}
			} else {
				logger.error("ShoppingCartController::addApparal::empty request body");
				throw new CartException("Empty request body");
			}
		} catch (final DBTransactionException dbe) {
			logger.error("Error occured while adding to cart" + dbe);
			new ResponseEntity("Error occured while adding to cart", HttpStatus.EXPECTATION_FAILED);
		}
		logger.info("ShoppingCartController::addApparal::exiting method");
		return new ResponseEntity("Apparal added to cart", HttpStatus.CREATED);
	}

	private void checkApparalQuantity(final Apparal apparal, final Cart cart) throws DBTransactionException {
		final List<Product> prodList = this.getProductList(apparal);
		if (prodList.size() > 0) {
			prodList.get(0).setCart(cart);
			cart.setProductList(prodList);
			this.cartServiceImpl.addProductToCart(cart);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/showMyCart")
	public ResponseEntity<String> showMyCart(@RequestParam("userName") final String userName)
			throws UserAccountException {
		logger.info("ShoppingCartController::showMyCart::entering method");
		@SuppressWarnings("rawtypes")
		ResponseEntity re = null;
		try {
			final User user = this.userServiceImpl.getUserByName(userName);
			if (user == null) {
				re = new ResponseEntity("User not found", HttpStatus.CONTINUE);
				logger.error("ShoppingCartController::showMyCart::User not found");
				throw new UserAccountException("User not found");
			} else {
				final List<Product> productList = this.cartServiceImpl.retrieveAllProducts(user.getCart().getCartId());
				if (productList.size() > 0) {
					final Cart cart = new Cart();
					cart.setCartId(user.getCart().getCartId());
					cart.setProductList(productList);
					double tPrice = 0.0;
					for (final Product pd : productList) {
						tPrice = tPrice + (pd.getPrice() * pd.getQuantity());
					}
					cart.setTotalPrice(tPrice);
					re = new ResponseEntity(cart, HttpStatus.OK);
				} else {
					re = new ResponseEntity("Cart empty", HttpStatus.CONTINUE);
				}
			}
		} catch (final DBTransactionException dbe) {
			logger.error("Error occured while fetching from DB" + dbe);
			new ResponseEntity("Error occured while fetching from DB", HttpStatus.EXPECTATION_FAILED);
		}
		logger.info("ShoppingCartController::showMyCart::exiting method");
		return re;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@DeleteMapping("/deleteFromCart")
	public ResponseEntity<String> deleteProductfromUserCart(@RequestParam("prodName") final String prodname,
			@RequestParam("username") final String userName) throws CartException, UserAccountException {
		logger.info("ShoppingCartController::deleteProductfromUserCart::entering method");
		try {
			final User user = this.userServiceImpl.getUserByName(userName);
			if (user == null) {
				throw new UserAccountException("User not found");
			} else {
				final Product usrProduct = this.cartServiceImpl.retrieveProductFromUserCart(user.getCart().getCartId(),
						prodname);
				if (usrProduct == null) {
					logger.error("Product not found::" + prodname);
					throw new CartException("Product not found");
				} else {
					this.cartServiceImpl.deleteSpecificProductFromCart(user.getCart().getCartId(), prodname);
					logger.info("Product deleted from cart::" + prodname);
				}
			}
		} catch (final DBTransactionException dbe) {
			logger.error("Error occured while deleting from Cart" + dbe);
			return new ResponseEntity("Error occured while deleting from Cart", HttpStatus.EXPECTATION_FAILED);
		}
		logger.info("ShoppingCartController::deleteProductfromUserCart::exiting method");
		return new ResponseEntity("Product deleted from Cart", HttpStatus.CREATED);

	}

	private List<Product> getProductList(final Product product) {
		logger.info("ShoppingCartController::getProductList::entering method");
		final List<Product> productList = new ArrayList<Product>();
		if (product instanceof Book) {
			final Book bk = new Book();
			bk.setProdName(product.getProdName());
			bk.setAuthor(((Book) product).getAuthor());
			bk.setPublication(((Book) product).getPublication());
			bk.setPrice(product.getPrice());
			bk.setQuantity(product.getQuantity());
			bk.setGenre(((Book) product).getGenre());
			productList.add(bk);

		} else {
			final Apparal ap = new Apparal();
			ap.setProdName(product.getProdName());
			ap.setDesign(((Apparal) product).getDesign());
			ap.setDesign(((Apparal) product).getBrand());
			ap.setPrice(product.getPrice());
			ap.setQuantity(product.getQuantity());
			ap.setType(((Apparal) product).getType());
			productList.add(ap);
		}
		logger.info("ShoppingCartController::getProductList::exiting method");
		return productList;

	}

	@GetMapping("/check")
	public String getMethod() {
		return "I am alive";
	}
}
