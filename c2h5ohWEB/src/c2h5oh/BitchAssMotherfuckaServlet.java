package c2h5oh;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.BananaBean;

import c2h5oh.jpa.Director;

/**
 * Servlet implementation class BitchAssMotherfuckaServlet
 */
@WebServlet("/BitchAssMotherfuckaServlet")
public class BitchAssMotherfuckaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//@PersistenceContext
	//EntityManager manager;
	
	@EJB
	BananaBean banana;
	
	//@Resource
	//UserTransaction ut;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	void shit() {
		String shit = banana.getShit();
		System.out.println(shit);
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
		shit();
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
