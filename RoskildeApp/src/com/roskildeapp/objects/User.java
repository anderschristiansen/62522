package com.roskildeapp.objects;

public class User {
	String objectId;
	String name;
	String password;
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	public String getObjectId() {
		return objectId;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
