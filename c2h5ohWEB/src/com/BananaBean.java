package com;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import c2h5oh.FUCKME;
import c2h5oh.jpa.Product;

/**
 * Session Bean implementation class BananaBean
 */
@Stateless
@LocalBean
public class BananaBean {

	/**
	 * Default constructor.
	 */
	public BananaBean() {
		// TODO Auto-generated constructor stub
	}

	@PersistenceContext
	EntityManager manager;

	// Container transaction management is only supported by EJS-s, not by JSPs
	// and JSFs
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String getShit() {
		Logger logger = Logger.getLogger(FUCKME.class.getName());
		logger.log(Level.ALL, "SHIT NIGGA");
		try {
			// ut.begin();
			Product prod = new Product();
			prod.setName("sadasd");
			prod.setPrice("12.assd");
			manager.persist(prod);
			// ut.commit();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.log(Level.ALL, "DAMN");
		return "shit";
	}
}
