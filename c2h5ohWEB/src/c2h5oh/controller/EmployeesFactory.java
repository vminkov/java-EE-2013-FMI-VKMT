package c2h5oh.controller;

import c2h5oh.beans.roles.Role;
import c2h5oh.jpa.Bartender;
import c2h5oh.jpa.Director;
import c2h5oh.jpa.Employee;
import c2h5oh.jpa.Waiter;

//HR отделът си мечтаят само за такова нещо
public class EmployeesFactory {
	public static Employee get(Role role){
		
		if(Role.WAITER.equals(role)){
			return new Waiter();
		
		} else if(Role.BARTENDER.equals(role)){
			return new Bartender();
		
		} else if(Role.DIRECTOR.equals(role)) {
			return new Director();
		}
		return null;
	}
}
