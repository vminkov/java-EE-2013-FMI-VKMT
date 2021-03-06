package c2h5oh.jpa;

import javax.persistence.*;

import c2h5oh.jpa.OrderItem;

import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Table(name = "T_PRODUCT")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToMany(mappedBy = "product")
	private Collection<OrderItem> orderItem;
	@Basic
	private String name;
	@Column(precision=10, scale=2)
	private BigDecimal price;
	@Basic
	private Boolean isActive;

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

	public void setName(String param) {
		this.name = param;
	}

	public String getName() {
		return name;
	}

	public void setPrice(BigDecimal param) {
		this.price = param;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setIsActive(Boolean param) {
		this.isActive = param;
	}

	public Boolean getIsActive() {
		return isActive;
	}
}