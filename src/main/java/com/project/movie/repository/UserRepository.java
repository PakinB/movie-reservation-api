package com.project.movie.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.movie.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);
	
	List<User> findByUsername(String name);
	
	List<User> findByRole(String role);
	
	boolean existsByEmail(String email);
	
}
