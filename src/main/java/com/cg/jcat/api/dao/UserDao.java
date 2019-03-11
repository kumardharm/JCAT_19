package com.cg.jcat.api.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionSystemException;

import com.cg.jcat.api.JcatApiApplication;

import com.cg.jcat.api.controller.UserController;

import com.cg.jcat.api.entity.User;
import com.cg.jcat.api.exception.DeleteUserException;
import com.cg.jcat.api.exception.JcatExceptions;
import com.cg.jcat.api.exception.SaveUserException;
import com.cg.jcat.api.exception.SystemExceptions;
import com.cg.jcat.api.exception.UserAlreadyExistsException;
import com.cg.jcat.api.repository.IUserRepository;

@Component
public class UserDao {

	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

	@Autowired
	private IUserRepository userRepository;
	
	Date date = new Date();

	public List<UserModel> getUsers() {
		List<User> userList = userRepository.findAll();
		List<UserModel> userDaoList = new ArrayList<UserModel>();
		return togetUsers(userList, userDaoList);
	}

	private List<UserModel> togetUsers(List<User> userList, List<UserModel> userDaoList) {

		for (User user : userList) {
			userDaoList.add(toUserDao(user));
		}
		return userDaoList;
	}

	private UserModel toUserDao(User user) {
		UserModel userDao = null;
		if (user != null) {
			userDao = new UserModel();
			userDao.setUserId(user.getUserId());
			userDao.setFirstName(user.getFirstName());
			userDao.setLastName(user.getLastName());
			userDao.setUsername(user.getUsername());
			userDao.setPassword(user.getPassword());
			userDao.setUserEmail(user.getUserEmail());
			userDao.setCompany(user.getCompany());
			userDao.setAdmin(user.isAdmin());
		}
		return userDao;
	}

	public boolean createUser(UserModel userModel, String createdBy) {
		userModel.setCreatedBy(createdBy);
		userModel.setPassword("Cg@123");
		return saveUser(userModel);
	}

	public boolean saveUser(UserModel userModel) {
		boolean value = false;
		User savedUser = userRepository.save(toUsers(userModel));
		if (savedUser != null) {
			value = true;
		}

		return value;

	}

	public boolean updateUsers(UserModel user, String modifiedBy) {
		return setUpdatedUser(user, modifiedBy);
	}

	public boolean setUpdatedUser(UserModel user, String modifiedBy) {

		user.setModifiedBy(modifiedBy);
		return saveUser(user);
	}

	public boolean deleteById(int userId) throws JcatExceptions {
		User user = findByUserId(userId);
		if (user == null) {

			throw new DeleteUserException(Integer.toString(userId));
		}
		user.setDeleted(true);
		user = userRepository.save(user);
		if (user.isDeleted()) {
			return true;
		} else {
			return false;
		}
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User findByUserId(int userId) {
		return userRepository.findByUserId(userId);

	}

	private User toUsers(UserModel userModel) {
		User users = null;
		if (userModel != null) {
			users = new User();
			users.setUsername(userModel.getUsername());
			users.setUserId(userModel.getUserId());
			users.setCompany(userModel.getCompany());
			users.setCreatedBy(userModel.getCreatedBy());
			users.setFirstName(userModel.getFirstName());
			users.setLastName(userModel.getLastName());
			users.setModifiedBy(userModel.getModifiedBy());
			users.setPassword(userModel.getPassword());
			users.setUserEmail(userModel.getUserEmail());
			users.setCteatedTime(date);
		}
		return users;
	}

	public User create(String userName) {
		
		User users = new User();
		users.setUsername(userName);
		users.setAdmin(false);
		users.setDeleted(false);
		users.setCreatedBy("Admin");
		users.setCteatedTime(date);
		 return userRepository.save(users);
	}

}
