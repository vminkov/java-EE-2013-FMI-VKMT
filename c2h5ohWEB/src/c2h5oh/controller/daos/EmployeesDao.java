package c2h5oh.controller.daos;

import javax.ejb.Stateless;

import c2h5oh.jpa.Employee;

@Stateless
public class EmployeesDao extends GenericDaoImpl<Employee> {


	public EmployeesDao() {
		super(Employee.class);
	}
	
}
