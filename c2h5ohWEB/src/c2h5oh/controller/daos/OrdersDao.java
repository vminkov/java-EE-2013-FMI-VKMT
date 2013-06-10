package c2h5oh.controller.daos;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import c2h5oh.jpa.Order;

@Stateless
public class OrdersDao extends GenericDaoImpl<Order> {

	public OrdersDao() {
		super(Order.class);
	}
	
    public List<Order> filterByDate(Date from, Date to) {
		Query q = em.createQuery("SELECT o FROM " + Order.class.getSimpleName() + " o WHERE o.acceptedTime BETWEEN :from " + 
				" AND :to");
		q.setParameter("from", from);
		q.setParameter("to", to);
		List<Order> resultList = (List<Order>) q.getResultList();

		return resultList;
    }	
}
