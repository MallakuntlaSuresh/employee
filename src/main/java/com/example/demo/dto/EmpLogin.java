package com.example.demo.dto;

public class EmpLogin {
	private String email;
	private String password;
	private String loginId;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	@Override
	public String toString() {
		return "EmpLogin [email=" + email + ", password=" + password + ", loginId=" + loginId + "]";
	}

	public EmpLogin(String email, String password, String loginId) {
		super();
		this.email = email;
		this.password = password;
		this.loginId = loginId;
	}

}
