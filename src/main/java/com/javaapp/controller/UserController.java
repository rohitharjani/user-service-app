package com.javaapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaapp.constants.ServiceConstants;
import com.javaapp.models.UserDetails;
import com.javaapp.sevice.IUserService;

@RestController
@RequestMapping("/v1")
@Validated
public class UserController {
	
	
	@Autowired
	private IUserService userService;
	
	@PostMapping("/user")
	public ResponseEntity<String> addUser(@Valid @RequestBody UserDetails user) {
		userService.addUser(user);
		return new ResponseEntity<String>(ServiceConstants.USER_CREATED_SUCCESSFULLY, HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<UserDetails> getUser(@PathVariable("id") int id){
		UserDetails user = userService.getUser(id);
		return new ResponseEntity<UserDetails>(user, HttpStatus.OK);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<UserDetails>> getAllUsers(){
		List<UserDetails> usersList = userService.getAllUsers();
		return new ResponseEntity<List<UserDetails>>(usersList, HttpStatus.OK);
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") int id){
		userService.deleteUser(id);
		return new ResponseEntity<String>(ServiceConstants.USER_DELETED_SUCCESSFULLY, HttpStatus.NO_CONTENT);
	}
}
