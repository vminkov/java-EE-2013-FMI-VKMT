package c2h5oh.jpa;

import javax.persistence.*;

import c2h5oh.jpa.Order;

import java.util.Collection;

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

}