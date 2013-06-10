package c2h5oh.beans;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import c2h5oh.beans.roles.Role;

@LocalBean
@Stateless
public class UserInfoBean {
	
	private Long id;
	protected String username;
	protected String name;
	protected String email;
	protected String passwordHash;
	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	protected Role role;
	
	public UserInfoBean() {
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


}
