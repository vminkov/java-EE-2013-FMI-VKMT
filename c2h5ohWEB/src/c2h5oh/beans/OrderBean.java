package c2h5oh.beans;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import c2h5oh.jpa.Bartender;
import c2h5oh.jpa.Order;
import c2h5oh.jpa.Order.State;
import c2h5oh.jpa.OrderItem;
import c2h5oh.jpa.Product;
import c2h5oh.jpa.User;
import c2h5oh.jpa.Waiter;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderBean {
	static final Logger LOGGER = Logger.getLogger(OrderBean.class.getName());
	
	// The number of minutes after which an order is overdue
	private static final int OVERDUE_MINUTES = 5;
	// The number of minutes after which an order is late
	private static final int LATE_MINUTES = 3;
	
	
	@PersistenceContext(unitName = "c2h5oh")
	EntityManager manager;
	
	@SuppressWarnings("unchecked")
	public List<Product> getProducts() {
		manager.clear();
		String queryString = "SELECT p FROM Product p WHERE p.isActive = true";
		Query query = manager.createQuery(queryString);
		return query.getResultList();
	}

	public Order createOrder(Long[] productIds, Integer[] quantities) {
		Order order = new Order();
		order.setState(State.NEW);
		order.setAcceptedTime(new Date());
		
		int size = productIds.length;
		for (int i = 0; i < size; ++i) {
			Long productId = productIds[i];
			Integer quantity = quantities[i];
			OrderItem item = new OrderItem();
			Product product = manager.find(Product.class, productId);

			item.setOrder(order);
			item.setProduct(product);
			item.setQuantity(quantity);
			manager.persist(item);
		}
		
		manager.persist(order);
		manager.flush();
		manager.refresh(order);
		return order;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Bartender getBartender(User user) {
		User theUser = manager.find(User.class, user.getId());
		manager.refresh(theUser);
		String queryString = "SELECT b FROM Bartender b WHERE b.id = :id";
		
		Query query = manager.createQuery(queryString);
		query.setParameter("id", theUser.getId());

		List<Bartender> bartenders = query.getResultList();
		Bartender bartender = null;
		if (bartenders != null && bartenders.size() > 0) {
			bartender = bartenders.get(0);
		}
		return bartender;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<User> getUsers() {
		String queryString = "SELECT u FROM User u";
		Query query = manager.createQuery(queryString);
		List<User> users = query.getResultList();
		return users;
	}

	@SuppressWarnings("unchecked")
	//@Schedule(second="*/5", minute="*", hour="*", persistent=false)
	public void markOverdueOrders() {
		System.out.println("entering markOverdueOrders");
		// update the state of all the orders which
		Date now = new Date();
		// update late orders
		String lateQueryString = "SELECT o FROM Order o WHERE o.state = c2h5oh.jpa.Order$State.NEW OR o.state = c2h5oh.jpa.Order$State.ACCEPTED";
		Query lateQuery = manager.createQuery(lateQueryString);
		List<Order> lateOrders = lateQuery.getResultList();
		for (Order order : lateOrders) {
			Date orderTime = order.getAcceptedTime();
			updateLate(now, order, orderTime);
		}
		
		// update overdue orders
		String overdueQueryString = "SELECT o FROM Order o WHERE o.state = c2h5oh.jpa.Order$State.LATE";
		Query overdueQuery = manager.createQuery(overdueQueryString);
		List<Order> overdueOrders = overdueQuery.getResultList();
		for (Order order : overdueOrders) {
			Date orderTime = order.getAcceptedTime();
			updateOverdue(now, order, orderTime);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Order> getNewOrders() {
		// TODO: Make unaccepted
		String unacceptedQueryString = "SELECT o FROM Order o WHERE o.state = c2h5oh.jpa.Order$State.NEW";
		Query query = manager.createQuery(unacceptedQueryString);
		List<Order> orders = query.getResultList();
		return orders;
	}
	
	/**
	 * Returns a list of incomplete orders for a waiter
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Order> getIncompleteOrders(Waiter waiter) {
		// TODO: Make unaccepted
		String unacceptedQueryString = "SELECT o FROM Order o WHERE o.state = c2h5oh.jpa.Order$State.ACCEPTED AND o.waiter = :waiter";
		Query query = manager.createQuery(unacceptedQueryString);
		query.setParameter("waiter", waiter);
		List<Order> orders = query.getResultList();
		return orders;
	}
	

	/**
	 * Accepts an order
	 * @param orderId
	 * @param acceptedTime
	 * @param bartender
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Order acceptOrder(Long orderId, Date acceptedTime, Bartender bartender) {
		Order order = manager.find(Order.class, orderId);
		order.setAcceptedTime(acceptedTime);
		order.setBartender(bartender);
		manager.persist(order);
		return order;
	}
	
	/**
	 * Completes an order
	 * @param orderId
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Order completeOrder(Long orderId) {
		Order order = manager.find(Order.class, orderId);
		order.setState(State.COMPLETED);
		manager.persist(order);
		return order;
	}

	private void updateLate(Date now, Order order, Date orderTime) {
		Calendar overdueOrderCalendar = Calendar.getInstance();
		overdueOrderCalendar.setTime(orderTime);
		overdueOrderCalendar.add(Calendar.MINUTE, LATE_MINUTES);
		if (now.after(overdueOrderCalendar.getTime())) {
			System.out.println("Marking order with id " + order.getId() + " as late.");
			order.setState(State.LATE);
			manager.persist(order);
		}
	}

	private void updateOverdue(Date now, Order order, Date orderTime) {
		Calendar overdueOrderCalendar = Calendar.getInstance();
		overdueOrderCalendar.setTime(orderTime);
		overdueOrderCalendar.add(Calendar.MINUTE, OVERDUE_MINUTES);
		if (now.after(overdueOrderCalendar.getTime())) {
			System.out.println("Marking order with id " + order.getId() + " as overdue.");
			order.setState(State.OVERDUE);
			manager.persist(order);
		}
	}
}
