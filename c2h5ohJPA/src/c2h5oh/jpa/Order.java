package c2h5oh.jpa;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.ManyToOne;

import c2h5oh.jpa.Bartender;
import c2h5oh.jpa.Waiter;

import javax.persistence.Basic;

@Entity
@Table(name = "T_ORDER")
public class Order {

	@Id
	private long id;
	@OneToMany(mappedBy = "order")
	private Collection<OrderItem> orderItem;
	@Enumerated
	@Basic(optional = false)
	private State state;
	@Temporal(TemporalType.TIMESTAMP)
	@Basic(optional = false)
	private Date acceptedTime;
	@ManyToOne
	private Waiter waiter;
	@ManyToOne
	private Bartender bartender;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Collection<OrderItem> getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(Collection<OrderItem> param) {
		this.orderItem = param;
	}

	public void setState(State param) {
		this.state = param;
	}

	public State getState() {
		return state;
	}

	public void setAcceptedTime(Date param) {
		this.acceptedTime = param;
	}

	public Date getAcceptedTime() {
		return acceptedTime;
	}

	public Waiter getWaiter() {
		return waiter;
	}

	public void setWaiter(Waiter param) {
		this.waiter = param;
	}

	public Bartender getBartender() {
		return bartender;
	}

	public void setBartender(Bartender param) {
		this.bartender = param;
	}

	public static enum State {
		NEW, ACCEPTED, COMPLETED, OVERDUE
	}
}