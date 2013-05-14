package c2h5oh;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2h5oh.jpa.Director;

/**
 * Servlet implementation class BitchAssMotherfuckaServlet
 */
@WebServlet("/BitchAssMotherfuckaServlet")
public class BitchAssMotherfuckaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BitchAssMotherfuckaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
	public void init() {
		shit();
	}
}
