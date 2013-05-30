package c2h5oh.servlets;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import c2h5oh.beans.UserInfoBean;
import c2h5oh.beans.roles.Role;
import c2h5oh.controller.Constants;
import c2h5oh.controller.UserManager;
import c2h5oh.jpa.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @EJB
    private UserManager userManager;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }
    @Override
    public void init() throws ServletException {
    	super.init();
    	if(userManager.getAllUsersCount() < 1) {
    		
    	}
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request
				.getParameter(Constants.USERNAME_REQUEST_PARAM_NAME);
		String password = request
				.getParameter(Constants.PASSWORD_REQUEST_PARAM_NAME);
		String roleString = request
				.getParameter(Constants.ROLE_REQUEST_PARAM_NAME);
		
		
		try {
			Role role = Role.fromValue(roleString);
			
			if (role != null && username != null && password != null && username.length() > 0
					&& password.length() > 0) {
				User loginBean = userManager.login(username, password, role);

				if (loginBean != null) {
					// successful login
					UserInfoBean userInfo = new UserInfoBean();
					userInfo.setUsername(loginBean.getUsername());
					userInfo.setName(loginBean.getEmployee().getFirstName());
					userInfo.setEmail(loginBean.getEmail());
					
					HttpSession session = request.getSession();
					session.setAttribute(Constants.USER_INFO_SESSION_ATTR_NAME,
							userInfo);
					
					response.addCookie(new Cookie(Constants.COOKIE_NAME, loginBean.getPasswordHash()));
					response.sendRedirect(Constants.MAIN_PAGE);
					return;
				}
			}
			
			Locale requestLocale = request.getLocale();
			ResourceBundle labels = ResourceBundle.getBundle("ResourceBundle",
					requestLocale);

			String message = labels
					.getString("login.error.invalidCredentialsMsg");
			request.setAttribute(Constants.MESSAGE_REQUEST_ATTR_NAME, message);

			request.getRequestDispatcher(Constants.LOGIN_PAGE).forward(request,
					response);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Unexpected error", e);
		}
	}

}
