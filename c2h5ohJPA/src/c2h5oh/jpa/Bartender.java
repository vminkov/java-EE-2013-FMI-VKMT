package c2h5oh.jpa;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import c2h5oh.beans.roles.Role;

@Entity
@Table(name = "T_BARTENDER")
public class Bartender extends Employee {

	@OneToMany(mappedBy = "bartender")
	private Collection<Order> completedOrders;

	public Collection<Order> getCompletedOrders() {
	    return completedOrders;
	}

	public void setCompletedOrders(Collection<Order> param) {
	    this.completedOrders = param;
	}
	
	@Override
	public Role getRole(){
		return Role.BARTENDER;
	}

}