package com.javaapp.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.CollectionUtils;

import com.javaapp.constants.ServiceConstants;
import com.javaapp.constants.TestConstants;
import com.javaapp.exception.UserServiceException;
import com.javaapp.models.UserDetails;
import com.javaapp.repository.UserRepository;
import com.javaapp.sevice.UserService;
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	
	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRepository userRepository;
	
	@Test
	public void testAddUser() {
		UserDetails user = buildUser(TestConstants.USER_ID, TestConstants.FIRST_NAME, TestConstants.LAST_NAME);
		when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		when(userRepository.findByFirstNameAndLastName(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());
	    when(userRepository.save(Mockito.any())).thenReturn(user);
	    userService.addUser(user);
	    verify(userRepository).save(user);
	}
	
	@Test(expected = UserServiceException.class)
	public void testAddExisitingUser() {
		UserDetails user = buildUser(TestConstants.USER_ID, TestConstants.FIRST_NAME, TestConstants.LAST_NAME);
		when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
		when(userRepository.findByFirstNameAndLastName(Mockito.any(), Mockito.any())).thenReturn(Optional.of(user));
	    when(userRepository.save(Mockito.any())).thenReturn(user);
	    userService.addUser(user);
	}
	
	@Test
	public void testGetUser() {
		UserDetails user = buildUser(TestConstants.USER_ID, TestConstants.FIRST_NAME, TestConstants.LAST_NAME);
		when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
		UserDetails response = userService.getUser(TestConstants.USER_ID);
		assertNotNull(response);
		assertTrue(!response.getFirstName().isBlank());
		assertTrue(!response.getLastName().isBlank());
	}
	
	@Test(expected = UserServiceException.class)
	public void testGetUserException() {
		when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		userService.getUser(TestConstants.USER_ID);
	}
	
	@Test
	public void testGetAllUsers() {
		List<UserDetails> usersList = new ArrayList<>();
		UserDetails user = buildUser(TestConstants.USER_ID, TestConstants.FIRST_NAME, TestConstants.LAST_NAME);
		usersList.add(user);
		when(userRepository.findAll(Sort.by(Direction.ASC, ServiceConstants.LAST_NAME))).thenReturn(usersList);
		List<UserDetails> response = userService.getAllUsers();
		assertTrue(!CollectionUtils.isEmpty(response));
	}
	
	@Test
	public void testDeleteUser() {
		when(userRepository.existsById(Mockito.any())).thenReturn(Boolean.TRUE);
		userService.deleteUser(TestConstants.USER_ID);
		verify(userRepository).existsById(TestConstants.USER_ID);
	}
	
	@Test(expected = UserServiceException.class)
	public void testDeleteUserException() {
		when(userRepository.existsById(Mockito.any())).thenReturn(Boolean.FALSE);
		userService.deleteUser(TestConstants.USER_ID);
	}
	
	private UserDetails buildUser(int id, String firstName, String LastName) {
		UserDetails user = new UserDetails();
		user.setId(id);
		user.setFirstName(firstName);
		user.setLastName(LastName);
		return user;
	}
}
