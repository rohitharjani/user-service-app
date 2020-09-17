package com.javaapp.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.CollectionUtils;

import com.javaapp.constants.TestConstants;
import com.javaapp.models.UserDetails;
import com.javaapp.repository.UserRepository;
import com.javaapp.sevice.IUserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
	
	@InjectMocks
	private UserController userController;

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private IUserService userService;

	@MockBean
	private UserRepository mockUserRepository;

	
	@Test
	public void testAddUser_Unauthorized() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/user")).andExpect(status().isUnauthorized());
	}

	@Test
	public void testAddUser() {
		UserDetails user = buildUser(TestConstants.USER_ID, TestConstants.FIRST_NAME, TestConstants.LAST_NAME);
		doNothing().when(userService).addUser(Mockito.any());
		ResponseEntity<String> response = userController.addUser(user);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	public void testGetUser() {
		UserDetails user = buildUser(TestConstants.USER_ID, TestConstants.FIRST_NAME, TestConstants.LAST_NAME);
		when(userService.getUser(Mockito.anyInt())).thenReturn(user);
		ResponseEntity<UserDetails> response = userController.getUser(TestConstants.USER_ID);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void testGetAllUsers() {
		List<UserDetails> usersList = new ArrayList<>();
		UserDetails user = buildUser(TestConstants.USER_ID, TestConstants.FIRST_NAME, TestConstants.LAST_NAME);
		usersList.add(user);
	    when(userService.getAllUsers()).thenReturn(usersList);
	    ResponseEntity<List<UserDetails>> response = userController.getAllUsers();
	    assertFalse(CollectionUtils.isEmpty(response.getBody()));
		assertEquals(HttpStatus.OK, response.getStatusCode()); 
	}
	
	@Test
	public void testDeleteUser() {
		doNothing().when(userService).deleteUser(Mockito.anyInt());
		ResponseEntity<String> response = userController.deleteUser(TestConstants.USER_ID);
		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
	
	private UserDetails buildUser(int id, String firstName, String LastName) {
		UserDetails user = new UserDetails();
		user.setId(id);
		user.setFirstName(firstName);
		user.setLastName(LastName);
		return user;
	}
}
