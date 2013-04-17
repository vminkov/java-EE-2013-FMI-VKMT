package c2h5oh.jpa;

import javax.persistence.*;

import c2h5oh.jpa.Order;

import java.util.Collection;

@Entity
@Table(name = "T_WAITER")
public class Waiter extends Employee {

	@OneToMany(mappedBy = "waiter")
	private Collection<Order> acceptedOrders;

	public Collection<Order> getAcceptedOrders() {
	    return acceptedOrders;
	}

	public void setAcceptedOrders(Collection<Order> param) {
	    this.acceptedOrders = param;
	}

}