package com.javaapp.sevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.javaapp.constants.ServiceConstants;
import com.javaapp.exception.UserServiceException;
import com.javaapp.models.UserDetails;
import com.javaapp.repository.UserRepository;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void addUser(UserDetails user) {
		if (isUserExists(user)) {
			throw new UserServiceException(HttpStatus.BAD_REQUEST, ServiceConstants.USER_ALREADY_EXISTS);
		}
		userRepository.save(user);
	}

	@Override
	public UserDetails getUser(int id) {
		Optional<UserDetails> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new UserServiceException(HttpStatus.NOT_FOUND, ServiceConstants.USER_NOT_EXISTS);
		}
	}

	@Override
	public List<UserDetails> getAllUsers() {
		List<UserDetails> usersList = userRepository.findAll(Sort.by(Direction.ASC, ServiceConstants.LAST_NAME));
		if (!CollectionUtils.isEmpty(usersList)) {
			return usersList;
		}
		return new ArrayList<UserDetails>();
	}

	@Override
	public void deleteUser(int id) {
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
		} else {
			throw new UserServiceException(HttpStatus.NOT_FOUND, ServiceConstants.USER_NOT_EXISTS);
		}
	}

	private boolean isUserExists(UserDetails user) {
		return userRepository.findById(user.getId()).isPresent()
				|| userRepository.findByFirstNameAndLastName(user.getFirstName(), user.getLastName()).isPresent();
	}
}
