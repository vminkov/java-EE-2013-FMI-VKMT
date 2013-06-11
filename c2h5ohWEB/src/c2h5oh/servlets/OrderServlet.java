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

import com.sun.xml.rpc.processor.modeler.j2ee.xml.roleNameType;

import c2h5oh.beans.OrderBean;
import c2h5oh.beans.UserInfoBean;
import c2h5oh.beans.roles.Role;
import c2h5oh.controller.UserManager;
import c2h5oh.jpa.Bartender;
import c2h5oh.jpa.Order;
import c2h5oh.jpa.Product;
import c2h5oh.jpa.User;
import c2h5oh.jpa.Waiter;
import c2h5oh.util.Constants;
import c2h5oh.util.JspUtils;

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
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		action = JspUtils.escapeForHTML(action);
		String address;
		UserInfoBean user = (UserInfoBean) request.getSession().getAttribute(Constants.USER_INFO_SESSION_ATTR_NAME);
		if(user == null) return;
		
		if ("create-form".equals(action) && Role.WAITER.equals(user.getRole())) {
			// Create
			List<Product> products = bean.getProducts();
			request.setAttribute("products", products);
			address = "/order/order-create.jsp";
		} else if ("create".equals(action) && Role.WAITER.equals(user.getRole())) {
			Order order = createOrder(request, response);
			request.setAttribute("order", order);
			address = "/order/order-create-success.jsp";
		} else if ("accept-form".equals(action) && Role.BARTENDER.equals(user.getRole())) {
			// Accept
			User userEntity = getSessionUser(request);
			Bartender bartender = (Bartender) userEntity.getEmployee();
			request.setAttribute("bartender", bartender);
			List<Order> orders = bean.getNewOrders();
			request.setAttribute("orders", orders);
			address = "/order/order-accept.jsp";
		} else if ("accept".equals(action)  && Role.BARTENDER.equals(user.getRole())) {
			Order order = acceptOrder(request, response);
			request.setAttribute("order", order);
			address = "/order/order-accept-sucess.jsp";
		} else if ("pending-form".equals(action)  && Role.BARTENDER.equals(user.getRole())) {
			// Pending
			User userEntity = getSessionUser(request);
			Bartender bartender = (Bartender) userEntity.getEmployee();
			request.setAttribute("bartender", bartender);
			System.out.println("bartender id: " + bartender.getId());
			List<Order> orders = bean.getAcceptedOrders(bartender);
			request.setAttribute("orders", orders);
			address = "/order/order-pending.jsp";
		} else if ("pending".equals(action)  && Role.BARTENDER.equals(user.getRole())) {
			Order order = pendOrder(request, response);
			request.setAttribute("order", order);
			address = "/order/order-pending-success.jsp";
		} else if ("complete-form".equals(action)  && Role.WAITER.equals(user.getRole())) {
			// Complete
			User userEntity = getSessionUser(request);
			Waiter waiter = (Waiter) userEntity.getEmployee();
			List<Order> orders = bean.getPendingOrders(waiter);
			request.setAttribute("orders", orders);
			address = "/order/order-complete.jsp";
		} else if ("complete".equals(action)  && Role.WAITER.equals(user.getRole())) {
			Order order = completeOrder(request, response);
			request.setAttribute("order", order);
			address = "/order/order-complete-success.jsp";
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

	private Order pendOrder(HttpServletRequest request,
			HttpServletResponse response) {
		String orderId = request.getParameter("orderId");
		System.out.println("Pending order: " + orderId);
		return bean.pendOrder(Long.parseLong(orderId));
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
