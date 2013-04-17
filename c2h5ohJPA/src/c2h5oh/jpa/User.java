package c2h5oh.jpa;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import org.eclipse.persistence.annotations.Mutable;

import c2h5oh.jpa.Employee;

@Entity
@Table(name = "T_USER")
public class User {

	@Id
	private long id;
	@Basic(optional = false)
	@Mutable(false)
	private String username;
	@Basic(optional = false)
	private String password;
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

	public void setPassword(String param) {
		this.password = param;
	}

	public String getPassword() {
		return password;
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