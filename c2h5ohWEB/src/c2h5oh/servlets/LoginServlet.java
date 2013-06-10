package c2h5oh.servlets;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import c2h5oh.beans.UserInfoBean;
import c2h5oh.beans.roles.Role;
import c2h5oh.controller.IUserManager;
import c2h5oh.controller.UserManager;
import c2h5oh.controller.exceptions.NoSuchUserException;
import c2h5oh.controller.exceptions.UserCreationException;
import c2h5oh.controller.exceptions.UserExistsException;
import c2h5oh.controller.exceptions.WrongPasswordException;
import c2h5oh.jpa.User;
import c2h5oh.util.Constants;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/userLogin")
public class LoginServlet extends HttpServlet {
	@EJB
	private UserManager userManager;
	
	
	
	@Override
	public void init() throws ServletException {
		super.init();

		if (userManager.getAllUsers().size() == 0) {
			try {
				User admin = userManager.createNewUser("admin", "tigretigre", Role.DIRECTOR);
				admin.setEmail("boss@c2h5oh.bg");
				userManager.updateUserInfo(admin);

				System.out.println("Created ADMIN account with user/pass: "
						+ "admin / tigretigre");

			} catch (UserExistsException e) {
				System.out.println("This could never happen");
				e.printStackTrace();
			} catch (UserCreationException e) {
				System.out
						.println("that's bad - there is no admin account - create one from the database");
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String message = null;
		String username = request
				.getParameter(Constants.USERNAME_REQUEST_PARAM_NAME);
		String password = request
				.getParameter(Constants.PASSWORD_REQUEST_PARAM_NAME);
		String roleString = request
				.getParameter(Constants.ROLE_REQUEST_PARAM_NAME);
		System.out.println(username + " " + password + " " + roleString);
		try {
			if (username != null && password != null
					&& username.length() > 0 && password.length() > 0) {
				User loginBean = userManager.login(username, password);
				System.out.println(loginBean);
				if (loginBean != null) {
					// successful login
					UserInfoBean userInfo = new UserInfoBean();
					userInfo.setUsername(loginBean.getUsername());
					userInfo.setName(loginBean.getEmployee().getFirstName());
					userInfo.setEmail(loginBean.getEmail());
					userInfo.setRole(loginBean.getEmployee().getRole());
					userInfo.setPasswordHash(loginBean.getPasswordHash());

					HttpSession session = request.getSession();
					session.setAttribute(Constants.USER_INFO_SESSION_ATTR_NAME,
							userInfo);

					response.addCookie(new Cookie(Constants.COOKIE_NAME,
							loginBean.getPasswordHash()));
					response.sendRedirect(request.getContextPath() + Constants.MAIN_PAGE);
					return;
				}
			}
		} catch (WrongPasswordException e) {
			message = "Wrong username / password!";
		} catch (NoSuchUserException e) {
			message = "Wrong username / password!";
		} catch (LoginException e) {
			message = "Server error!";
		}
		// works for forwarding only
		request.setAttribute(Constants.MESSAGE_REQUEST_ATTR_NAME, message);

		request.getRequestDispatcher(Constants.LOGIN_PAGE).forward(request, response);
		// response.sendRedirect(this.getServletContext().getContextPath() +
		// Constants.LOGIN_PAGE);
	}

}
