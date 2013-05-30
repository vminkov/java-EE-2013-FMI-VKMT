package c2h5oh.controller.daos;


import java.util.List;

import javax.ejb.Stateless;

import c2h5oh.jpa.User;

@Stateless
public class UsersDao extends GenericDaoImpl<User> {


	public UsersDao() {
		super(User.class);
	}
	
	//никой не е казал, че е ID username
	public List<User> getByUsername(String username) {
		return this.filter("username", username);
	}
}
