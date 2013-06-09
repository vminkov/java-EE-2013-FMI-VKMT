package c2h5oh.servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2h5oh.beans.OrderBean;
import c2h5oh.jpa.Order;
import c2h5oh.jpa.Product;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/order")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@EJB
	OrderBean bean;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String address;
		if ("create".equals(action)) {
			Order order = createOrder(request, response);
			request.setAttribute("order", order);
			address = "/order-success.jsp";
		} else {
			List<Product> products = bean.getProducts();
			request.setAttribute("products", products);			
			address = "/order-new.jsp";
		}
		
		RequestDispatcher disp = request.getRequestDispatcher(address);
		disp.forward(request, response);
	}

	private Order createOrder(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Creating order");
		String[] productsString = request.getParameterValues("products");
		String[] quantitiesString = request.getParameterValues("quantities");
		
		Long[] productIds = new Long[productsString.length];
		Integer[] quantities = new Integer[quantitiesString.length];
		
		for (int i = 0; i < productsString.length; ++i) {
			productIds[i] = Long.parseLong(productsString[i]);
		}
		
		for (int i = 0; i < quantitiesString.length; ++i) {
			quantities[i] = Integer.parseInt(quantitiesString[i]);
		}
		
		return bean.createOrder(productIds, quantities);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Order received: " + request);
		String[] productsString = request.getParameterValues("products");
		String[] quantitiesString = request.getParameterValues("quantities");
		
		Long[] productIds = new Long[productsString.length];
		Integer[] quantities = new Integer[quantitiesString.length];
		
		for (int i = 0; i < productsString.length; ++i) {
			productIds[i] = Long.parseLong(productsString[i]);
		}
		
		for (int i = 0; i < quantitiesString.length; ++i) {
			quantities[i] = Integer.parseInt(quantitiesString[i]);
		}
		
		bean.createOrder(productIds, quantities);
	}
}
