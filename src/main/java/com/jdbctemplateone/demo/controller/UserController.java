package com.jdbctemplateone.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jdbctemplateone.demo.domain.User;
import com.jdbctemplateone.demo.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping()
	public List<User> users(){
		return this.userRepository.findAll();
	}
	
	@GetMapping("/firstnames")
	public List<String> firstNames(){
		return this.userRepository.findFirstNames();
	}
	
	@GetMapping("/user/{userId}")
	public User getUser(@PathVariable String userId){
		return this.userRepository.findUserById(Integer.parseInt(userId));
	}
	
	@RequestMapping(name="/user/{userId}/{userNafirstNameme}",method=RequestMethod.POST)
	public User getUserByIdAndFirstNam(@PathVariable String userId, @PathVariable String firstName) {
		return this.userRepository.findUserByIdAndFirstName(Integer.parseInt(userId), firstName);
	}
	

}
