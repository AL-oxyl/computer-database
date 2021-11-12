package com.oxyl.dto;

public class AuthentificationRequest {

		private String username;
		private String password;
		
		public AuthentificationRequest() {
		}
		
		public AuthentificationRequest(String username, String password) {
			this.username = username;
			this.password = password;
		}
		
		public String getUsername() {
			return username;
		}
		
		public String getPassword() {
			return password;
		}
		
		public void setUsername(String username) {
			this.username = username;
		}
		
		public void setPassword(String password) {
			this.password = password;
		}
}
