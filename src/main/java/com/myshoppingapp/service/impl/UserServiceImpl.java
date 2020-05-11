package com.myshoppingapp.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshoppingapp.dao.UserRepository;
import com.myshoppingapp.exceptions.DBTransactionException;
import com.myshoppingapp.model.User;
import com.myshoppingapp.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User createUser(final User user) throws DBTransactionException {
		logger.info("UserServiceImpl::createUser::entering method");
		User u = new User();
		u = this.userRepository.save(user);
		logger.info("UserServiceImpl::createUser::exiting method");
		return u;
	}

	@Override
	public User getUserByName(final String userName) throws DBTransactionException {
		logger.info("UserServiceImpl::getUserByName::entering method");
		logger.info("UserServiceImpl::getUserByName::exiting method");
		return this.userRepository.getByUserName(userName);
	}

	@Override
	public List<User> getAllUser() throws DBTransactionException {
		logger.info("UserServiceImpl::getAllUser::entering method");
		logger.info("UserServiceImpl::getAllUser::exiting method");
		return this.userRepository.findAll();
	}

}
