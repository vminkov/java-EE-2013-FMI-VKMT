package c2h5oh.jpa;

import javax.persistence.*;

import c2h5oh.beans.roles.Role;
import c2h5oh.jpa.User;
import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Entity
@Table(name = "T_EMPLOYEE")
@Inheritance(strategy = TABLE_PER_CLASS)
public class Employee {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@OneToOne(mappedBy = "employee", optional = false)
	private User user;
	@Basic
	private String firstName;
	@Basic
	private String lastName;
	@Basic
	private String address;
	@Basic
	private String salary;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User param) {
		this.user = param;
	}

	public void setFirstName(String param) {
		this.firstName = param;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String param) {
		this.lastName = param;
	}

	public String getLastName() {
		return lastName;
	}

	public void setAddress(String param) {
		this.address = param;
	}

	public String getAddress() {
		return address;
	}

	public void setSalary(String param) {
		this.salary = param;
	}

	public String getSalary() {
		return salary;
	}

	public Role getRole() {
		return null;
	}

}