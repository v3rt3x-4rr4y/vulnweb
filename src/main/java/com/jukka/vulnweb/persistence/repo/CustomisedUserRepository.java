package com.jukka.vulnweb.persistence.repo;

import java.util.List;

import com.jukka.vulnweb.persistence.model.User;

public interface CustomisedUserRepository
{
	List<User> findUsersByRole(String userRole);
}
