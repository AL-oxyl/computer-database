package com.oxyl.model;

public class User {
	private Integer id;
	private String username;
	private String password;
	private String role;
	private Boolean enabled;
	
	public User(UserBuilder builder) {
		this.id = builder.id;
		this.username = builder.username;
		this.password = builder.password;
		this.role  = builder.role;
		this.enabled = builder.enabled;
	}
	
	//Getter
	public Integer getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}

	public Boolean isEnabled() {
		return enabled;
	}
	
	public static class UserBuilder {
		private Integer id;
		private String username;
		private String password;
		private String role;
		private Boolean enabled;
		
		public UserBuilder() {
		}
		
		public UserBuilder id(int id) {
			this.id = id;
			return this;
		}
		
		public UserBuilder username(String username) {
			this.username = username;
			return this;
		}
		
		public UserBuilder password(String password) {
			this.password = password;
			return this;
		}
		
		public UserBuilder role(String role) {
			this.role = role;
			return this;
		}
		
		public UserBuilder enabled(Boolean enabled) {
			this.enabled = enabled;
			return this;
		}
		
		
		public User build() {
			return new User(this);
		}
		
	}
}
