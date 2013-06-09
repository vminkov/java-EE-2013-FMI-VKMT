package c2h5oh.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2h5oh.beans.OrderBean;
import c2h5oh.beans.UserInfoBean;
import c2h5oh.controller.UserManager;
import c2h5oh.jpa.Bartender;
import c2h5oh.jpa.Order;
import c2h5oh.jpa.Product;
import c2h5oh.jpa.User;
import c2h5oh.jpa.Waiter;
import c2h5oh.util.Constants;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/order")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	OrderBean bean;

	@EJB
	private UserManager userManager;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrderServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String address;
		Object attribute = request.getSession().getAttribute(Constants.USER_INFO_SESSION_ATTR_NAME);
		
		if ("create-form".equals(action)) {
			// Create
			List<Product> products = bean.getProducts();
			request.setAttribute("products", products);
			address = "/order/order-create.jsp";
		} else if ("create".equals(action)) {
			Order order = createOrder(request, response);
			request.setAttribute("order", order);
			address = "/order/order-create-success.jsp";
		} else if ("accept-form".equals(action)) {
			// Accept
			User user = getSessionUser(request);
			Bartender bartender = (Bartender) user.getEmployee();
			request.setAttribute("bartender", bartender);
			List<Order> orders = bean.getNewOrders();
			request.setAttribute("orders", orders);
			address = "/order/order-accept.jsp";
		} else if ("accept".equals(action)) {
			Order order = acceptOrder(request, response);
			request.setAttribute("order", order);
			address = "/order/order-accept-sucess.jsp";
		} else if ("complete-form".equals(action)) {
			// Complete
			User user = getSessionUser(request);
			Waiter waiter = (Waiter) user.getEmployee();
			List<Order> orders = bean.getIncompleteOrders(waiter);
			request.setAttribute("orders", orders);
			address = "/order/order-complete.jsp";
		} else if ("complete".equals(action)) {
			Order order = completeOrder(request, response);
			request.setAttribute("order", order);
			address = "/order/order-complete-sucess.jsp";
		} else {
			// Default
			address = "index.jsp";
		}

		RequestDispatcher disp = request.getRequestDispatcher(address);
		disp.forward(request, response);
	}

	private User getSessionUser(HttpServletRequest request) {
		UserInfoBean userInfo =  (UserInfoBean) request.getSession().getAttribute(Constants.USER_INFO_SESSION_ATTR_NAME);
		return userManager.getUser(userInfo.getUsername());
	}

	/**
	 * Creates an order
	 * @param request
	 * @param response
	 * @return
	 */
	private Order createOrder(HttpServletRequest request,
			HttpServletResponse response) {
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
		
		User user = getSessionUser(request);		
		Waiter waiter = (Waiter) user.getEmployee();
		return bean.createOrder(waiter, productIds, quantities);
	}

	private Order acceptOrder(HttpServletRequest request,
			HttpServletResponse response) {
		Long orderId = Long.parseLong(request.getParameter("orderId"));
		Date acceptTime = new Date();
		Bartender bartender = (Bartender) getSessionUser(request).getEmployee();
		return bean.acceptOrder(orderId, acceptTime, bartender);
	}

	private Order completeOrder(HttpServletRequest request,
			HttpServletResponse response) {
		Long orderId = Long.parseLong(request.getParameter("orderId"));
		return bean.completeOrder(orderId);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}
}
