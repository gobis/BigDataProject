package com.saitech;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@SuppressWarnings("deprecation")

@Entity
@Table
public class UserDetails {
	@Id 
	int userId;
	String username;
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	

}
