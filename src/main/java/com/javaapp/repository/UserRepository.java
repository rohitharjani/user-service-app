package com.javaapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javaapp.models.UserDetails;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Integer> {
	
	Optional<UserDetails> findByFirstNameAndLastName(String firstName, String lastName);

}
