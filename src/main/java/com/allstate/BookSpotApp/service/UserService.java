package com.allstate.BookSpotApp.service;

import com.allstate.BookSpotApp.model.Credentials;

import java.util.List;

public interface UserService {
	public List<Credentials> login(String username, String password);
	public Credentials newUserRegistration(Credentials credentials);
	public Credentials getUserDetails(String username);
	public Credentials getUserDetailsById(int id);

}