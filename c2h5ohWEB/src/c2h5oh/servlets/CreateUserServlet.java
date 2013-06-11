package c2h5oh.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2h5oh.beans.UserInfoBean;
import c2h5oh.beans.roles.Role;
import c2h5oh.controller.UserManager;
import c2h5oh.controller.daos.EmployeesDao;
import c2h5oh.controller.exceptions.UserCreationException;
import c2h5oh.controller.exceptions.UserExistsException;
import c2h5oh.jpa.User;
import c2h5oh.util.Constants;
import c2h5oh.util.JspUtils;

/**
 * Servlet implementation class CreateUserServlet
 */
@WebServlet("/CreateUserServlet")
public class CreateUserServlet extends HttpServlet {
	@EJB
	private UserManager userManager;
	
	@EJB
	private EmployeesDao emplDao;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUserServlet() {
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
		request.setCharacterEncoding("UTF-8");
		UserInfoBean requester = (UserInfoBean) request.getSession().getAttribute(Constants.USER_INFO_SESSION_ATTR_NAME);
		if(requester == null || !requester.getRole().equals(Role.DIRECTOR)){
			response.setStatus(405);
			return;
		}
		
		String username = request.getParameter(Constants.USERNAME_REQUEST_PARAM_NAME);
		String password = request.getParameter(Constants.PASSWORD_REQUEST_PARAM_NAME);
		String role = request.getParameter(Constants.ROLE_REQUEST_PARAM_NAME);
		String email = request.getParameter(Constants.EMAIL_REQUEST_PARAM_NAME);
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String address = request.getParameter("address");
		String salary = request.getParameter("salary");
		//validate
		username = JspUtils.escapeForHTML(username);
		password = JspUtils.escapeForHTML(password);
		role = JspUtils.escapeForHTML(role);
		email = JspUtils.escapeForHTML(email);
		firstName = JspUtils.escapeForHTML(firstName);
		lastName = JspUtils.escapeForHTML(lastName);
		address = JspUtils.escapeForHTML(address);
		salary = JspUtils.escapeForHTML(salary);
		try {
			this.userManager.createNewUser(username, password, Role.valueOf(role));
			User created = this.userManager.getUser(username);
			created.getEmployee().setAddress(address);
			created.getEmployee().setSalary(salary);
			
			this.emplDao.update(created.getEmployee());
			
			created.setEmail(email);
			this.userManager.updateUserInfo(created);
		} catch (UserExistsException e) {
			e.printStackTrace();
		} catch (UserCreationException e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher(Constants.MAIN_PAGE).forward(request, response);
	}

}
