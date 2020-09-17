package com.javaapp.sevice;

import java.util.List;

import com.javaapp.models.UserDetails;

public interface IUserService {
	
	//Add User
	public void addUser(UserDetails user);
	
	//Fetch Single User
	public UserDetails getUser(int id);
	
	//Fetch All Users
	public List<UserDetails> getAllUsers();
	
	//Delete User
	public void deleteUser(int id);
}
