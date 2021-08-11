package com.jukka.vulnweb.web;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jukka.vulnweb.persistence.model.User;
import com.jukka.vulnweb.persistence.repo.UserRepository;
import com.jukka.vulnweb.web.exception.UserIdMismatchException;
import com.jukka.vulnweb.web.exception.UserNotFoundException;

@RestController
@RequestMapping("/api/users")
public class UserController
{
	@Autowired
    private UserRepository userRepository;

    @GetMapping
    public Iterable<User> findAll()
    {
        return userRepository.findAll();
    }

    @GetMapping("/role/{userRole}")
    public List<User> findByRole(@PathVariable String userRole)
    {
        return userRepository.findUsersByRole(userRole);
    }
    
    @GetMapping("/username/{username}")
    public List<User> findByName(@PathVariable String username)
    {
        return userRepository.findByUsername(username);
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable long id)
    {
        return userRepository.findById(id)
          .orElseThrow(UserNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user)
    {
    	System.out.println(user.toString());
    	User user1 = userRepository.save(user);
        return user1;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id)
    {
    	userRepository.findById(id)
          .orElseThrow(UserNotFoundException::new);
    	userRepository.deleteById(id);
    }
    
    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable long id)
    {
        if (user.getId() != id)
        {
            throw new UserIdMismatchException();
        }
        userRepository.findById(id)
          .orElseThrow(UserNotFoundException::new);
        return userRepository.save(user);
    }
}
