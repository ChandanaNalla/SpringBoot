package com.myshoppingapp.service;

import java.util.List;

import com.myshoppingapp.exceptions.DBTransactionException;
import com.myshoppingapp.model.User;

public interface UserService {

	public User createUser(User user) throws DBTransactionException;

	public User getUserByName(String userName) throws DBTransactionException;

	public List<User> getAllUser() throws DBTransactionException;

}
