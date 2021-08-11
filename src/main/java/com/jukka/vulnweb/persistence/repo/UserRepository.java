package com.jukka.vulnweb.persistence.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jukka.vulnweb.persistence.model.User;

public interface UserRepository extends CrudRepository<User, Long>, CustomisedUserRepository
{
	List<User> findByUsername(String username);

	List<User> findUsersByRole(String userRole);
}
