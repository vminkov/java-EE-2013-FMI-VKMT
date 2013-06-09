package c2h5oh.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import c2h5oh.Statements;
import c2h5oh.jpa.Product;

@Stateless
@LocalBean
public class ProductBean {
	static final Logger LOGGER = Logger.getLogger(ProductBean.class.getName());
	public ProductBean() {
		// TODO Auto-generated constructor stub
	}

	@PersistenceContext
	EntityManager em;

	// Container transaction management is only supported by EJS-s, not by JSPs
	// and JSFs
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Product> listProducts() {
		
		LOGGER.log(Level.ALL, "PRODUCT LIST");

		List<Product> productList = new ArrayList<Product>();
		TypedQuery<Product> q = em.createQuery(Statements.GET_ACTIVE_PRODUCTS,
				Product.class);

		productList = q.getResultList();

		return productList;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Product newProduct(String name, String price) {
		
		LOGGER.log(Level.ALL, "NEW PRODUCT");
		
		Product product = new Product();
		product.setName(name);
		product.setPrice(new BigDecimal(price));
		product.setIsActive(true);
		em.persist(product);
		return product;

		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Product update(Product product) {
		
		LOGGER.log(Level.ALL, "Update PRODUCT");
		
		return em.merge(product);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remove(Long id) {
		
		LOGGER.log(Level.ALL, "Remove PRODUCT");
		Product product = getProduct(id);
		product.setIsActive(false);
		em.merge(product);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Product getProduct(Long id) {
		
		LOGGER.log(Level.ALL, "GET PRODUCT");
		
		return em.find(Product.class, id);
	}
}
