package com.allstate.BookSpotApp.repo;

import java.util.List;

import com.allstate.BookSpotApp.model.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Credentials, Long> {
	public List<Credentials> findByUsernameAndPassword(String username, String password);
	public Credentials findByUsername(String username);
	public Credentials findByid(int id);
}
