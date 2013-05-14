package c2h5oh;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import c2h5oh.jpa.Director;

@Startup
public class FUCKME {
	@PersistenceContext
	EntityManager manager;
	
	void shit() {
		Logger logger = Logger.getLogger(FUCKME.class.getName());
		logger.log(Level.ALL, "SHIT NIGGA");
		Director me = new Director();
		me.setAddress("na lunata");
		me.setFirstName("pesaho");
		logger.log(Level.ALL, "DAMN");
		
		manager.persist(me);
	}
}
