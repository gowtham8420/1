package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class User {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;

	 private String username;
	 private String password;

	 @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	 private List<SubScription> subscriptions = new ArrayList<>();

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Long id, String username, String password, List<SubScription> subscriptions) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.subscriptions = subscriptions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<SubScription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<SubScription> subscriptions) {
		this.subscriptions = subscriptions;
	}
	 
	 


}
