package c2h5oh.beans;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
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
	@PersistenceContext(unitName = "c2h5oh")
	EntityManager manager;
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Product> getProducts() {
		manager.clear();
		String queryString = "SELECT p FROM Product p WHERE p.isActive = true";
		Query query = manager.createQuery(queryString);
		return query.getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createOrder(Long[] productIds, Integer[] quantities) {
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
	}
}
