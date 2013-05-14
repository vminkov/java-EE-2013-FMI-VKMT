package c2h5oh.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Basic;
import javax.persistence.ManyToOne;

import c2h5oh.jpa.Product;
import c2h5oh.jpa.Order;

@Entity
@Table(name = "T_ORDERITEM")
public class OrderItem {

	@Id
	private long id;
	@Basic(optional = false)
	private Integer quantity;
	@ManyToOne(optional = false)
	private Product product;
	@ManyToOne(optional = false)
	private Order order;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setQuantity(Integer param) {
		this.quantity = param;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Product getProduct() {
	    return product;
	}

	public void setProduct(Product param) {
	    this.product = param;
	}

	public Order getOrder() {
	    return order;
	}

	public void setOrder(Order param) {
	    this.order = param;
	}

}