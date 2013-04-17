package c2h5oh.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Basic;
import javax.persistence.ManyToOne;

import c2h5oh.jpa.Article;
import c2h5oh.jpa.Order;

@Entity
@Table(name = "T_ORDERITEM")
public class OrderItem {

	@Id
	private long id;
	@Basic(optional = false)
	private Integer quantity;
	@ManyToOne(optional = false)
	private Article article;
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

	public Article getArticle() {
	    return article;
	}

	public void setArticle(Article param) {
	    this.article = param;
	}

	public Order getOrder() {
	    return order;
	}

	public void setOrder(Order param) {
	    this.order = param;
	}

}