package com.allstate.BookSpotApp.service;

import java.util.List;

import com.allstate.BookSpotApp.model.Credentials;
import com.allstate.BookSpotApp.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	private static Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserRepository userRepository;
	@Override
	public Credentials newUserRegistration(Credentials re) {
		
		return userRepository.save(re);
	}
	@Override
	public List<Credentials> login(String username, String password) {
			List<Credentials> list = null;
			try {
			list = userRepository.findByUsernameAndPassword(username, password);
			}
			catch(Exception e) {logger.error("Exception occurred during login, Please re-try after sometime");}
			return list;
	}
	@Override
	public Credentials getUserDetails(String username)  {
		
		return userRepository.findByUsername(username);
	}

	@Override
	public Credentials getUserDetailsById(int Id) {
		return userRepository.findByid(Id);
	}


}
