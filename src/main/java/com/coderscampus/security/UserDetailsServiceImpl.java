package com.coderscampus.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.coderscampus.repository.UserRepository;


public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDetails userDetails = userRepo.findUserByEmail(username);
		
		if (userDetails == null) {
			throw new UsernameNotFoundException("no such user exists");
		}
		return null;
	}

}
