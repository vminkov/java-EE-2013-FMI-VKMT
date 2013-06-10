package c2h5oh.jpa;

import javax.persistence.*;

import c2h5oh.jpa.Employee;

@Entity
@Table(name = "T_USER")
public class User {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Basic(optional = false)
	private String username;
	@Basic(optional = false)
	private String passwordHash;
	@OneToOne
	private Employee employee;
	@Basic
	private String email;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUsername(String param) {
		this.username = param;
	}

	public String getUsername() {
		return username;
	}

	public void setPasswordHash(String param) {
		this.passwordHash = param;
	}

	public String getPasswordHash() {
		return passwordHash;
	}
 
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee param) {
		this.employee = param;
	}

	public void setEmail(String param) {
		this.email = param;
	}

	public String getEmail() {
		return email;
	}

}