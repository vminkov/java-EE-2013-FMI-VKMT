package c2h5oh.beans;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import c2h5oh.jpa.Order;
import c2h5oh.jpa.Order.State;
import c2h5oh.jpa.OrderItem;
import c2h5oh.jpa.Product;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderBean {
	
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
	
	@SuppressWarnings("unchecked")
//	@Schedule(second="*/5", minute="*", hour="*")
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
