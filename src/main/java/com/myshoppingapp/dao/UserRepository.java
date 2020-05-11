package com.myshoppingapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myshoppingapp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT user FROM User user WHERE (user.userName) = (:userName)")
	User getByUserName(@Param("userName") String userName);

}
