package com.oxyl.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Transient;

@Entity
@Table(name = "user")
public class UserPersistDto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private String password;
	private String role;
	private Boolean enabled;
	@Transient
	private Boolean accountExpired = false;
	@Transient
	private Boolean locked = false;
	@Transient
	private Boolean credentialExpired = false;
	
	public UserPersistDto(UserPersistDtoBuilder builder) {
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
	
	public static class UserPersistDtoBuilder {
		private Integer id;
		private String username;
		private String password;
		private String role;
		private Boolean enabled;
		
		public UserPersistDtoBuilder() {
		}
		
		public UserPersistDtoBuilder id(int id) {
			this.id = id;
			return this;
		}
		
		public UserPersistDtoBuilder username(String username) {
			this.username = username;
			return this;
		}
		
		public UserPersistDtoBuilder password(String password) {
			this.password = password;
			return this;
		}
		
		public UserPersistDtoBuilder role(String role) {
			this.role = role;
			return this;
		}
		
		public UserPersistDtoBuilder enabled(Boolean enabled) {
			this.enabled = enabled;
			return this;
		}
		
		
		public UserPersistDto build() {
			return new UserPersistDto(this);
		}
		
	}
}