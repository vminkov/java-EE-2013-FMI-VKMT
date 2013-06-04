package c2h5oh.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.resource.spi.SecurityException;
import javax.security.auth.login.LoginException;


import c2h5oh.beans.roles.Role;
import c2h5oh.controller.daos.UsersDao;
import c2h5oh.controller.exceptions.NoSuchUserException;
import c2h5oh.controller.exceptions.UserCreationException;
import c2h5oh.controller.exceptions.UserExistsException;
import c2h5oh.controller.exceptions.WrongPasswordException;
import c2h5oh.jpa.User;

@Singleton
public class UserManager {

	@EJB
	private UsersDao usersDao;

	public void removeUser(Long userId, String comment) {
		usersDao.delete(userId);
	}

	public User login(String username, String password, Role loginType)
			throws SecurityException, WrongPasswordException,
			NoSuchUserException, LoginException {
		if (username == null || password == null)
			throw new InvalidParameterException("username: " + username
					+ " password: " + password + " are not valid");

		final String userSearch = username.toLowerCase();

		List<User> users = this.usersDao.getByUsername(userSearch);
		if (users.size() != 1) {
			throw new NoSuchUserException();
		}

		User user = users.get(0);

		Role role = user.getEmployee().getRole();

		if (role == null || !role.equals(loginType)) {
			throw new SecurityException();
		}

		try {
			if (!user.getPasswordHash().equals(getMD5Hash(password))) {
				throw new WrongPasswordException();
			} else {
				return user;
			}
		//	Multi-catch parameters are not allowed for source level below 1.7
		} catch (UnsupportedEncodingException e) {
			throw new LoginException();
		} catch (NoSuchAlgorithmException e) {
			throw new LoginException();
		}
	}

	public User loginWithCookie(String username, String passwordHash,
			Role loginType) throws WrongPasswordException, SecurityException,
			NoSuchUserException {
		if (username == null || passwordHash == null)
			throw new InvalidParameterException("username: " + username
					+ " password: " + passwordHash + " are not valid");

		final String userSearch = username.toLowerCase();

		List<User> users = this.usersDao.getByUsername(userSearch);
		if (users.size() != 1) {
			throw new NoSuchUserException();
		}

		User user = users.get(0);

		Role role = user.getEmployee().getRole();
		if (role == null || !role.equals(loginType)) {
			throw new SecurityException();
		}

		if (!user.getPasswordHash().equals(passwordHash)) {
			throw new WrongPasswordException();
		} else {
			return user;
		}
	}

	public User createNewUser(String username, String password, Role role)
			throws UserExistsException, UserCreationException {
		if (username == null || password == null)
			throw new InvalidParameterException("username: " + username
					+ " password: " + password + " are not valid");

		final String userSearch = username.toLowerCase();

		List<User> users = this.usersDao.getByUsername(userSearch);
		
		try {
			if (users.size() > 0) {
					System.out.println("the user: " + username
							+ " already exist");
					throw new UserExistsException();
			} else {
				User newUser = new User();
				newUser.setUsername(username.toLowerCase());

				newUser.setPasswordHash(getMD5Hash(password));

				newUser.setEmployee(EmployeesFactory.get(role));
				

				System.out.println("creating user " + newUser.getUsername());
				this.usersDao.update(newUser);
				return newUser;
			}
		//	Multi-catch parameters are not allowed for source level below 1.7
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new UserCreationException();
		}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new UserCreationException();
		}
	}

	public boolean isDirector(String username) {
		if (username == null)
			throw new InvalidParameterException("the username is null");

		final String userSearch = username.toLowerCase();
		List<User> users = this.usersDao.getByUsername(userSearch);

		User user = users.get(0);

		return user.getEmployee().getRole().equals(Role.DIRECTOR);
	}

	public boolean isBartender(String username) {
		if (username == null)
			throw new InvalidParameterException("the username is null");

		final String userSearch = username.toLowerCase();
		List<User> users = this.usersDao.getByUsername(userSearch);

		User user = users.get(0);

		return user.getEmployee().getRole().equals(Role.BARTENDER);
	}

	public boolean isWaiter(String username) {
		if (username == null)
			throw new InvalidParameterException("the username is null");

		final String userSearch = username.toLowerCase();
		List<User> users = this.usersDao.getByUsername(userSearch);

		User user = users.get(0);

		return user.getEmployee().getRole().equals(Role.WAITER);
	}

	public User getUser(String username) {
		if (username == null)
			throw new InvalidParameterException("username is not valid");

		return this.usersDao.getByUsername(username.toLowerCase()).get(0);
	}

	public User updateUserInfo(User user) {
		throw new RuntimeException();
		//����� ����� ���� �� �� ����, ��� ����� ���� �� �� �� ������
	}

	private String getMD5Hash(String password) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		byte[] passwordAsBytes = password.getBytes("UTF-8");
		MessageDigest md = null;
		md = MessageDigest.getInstance("MD5");
		byte[] theDigest = md.digest(passwordAsBytes);

		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < theDigest.length; i++) {
			String hex = Integer.toHexString(0xff & theDigest[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}

		return hexString.toString();
	}

	public List<User> getAllUsers() {
		return this.usersDao.getAll();
	}
	
	public long getAllUsersCount() {
		return this.usersDao.countAll();
	}
}
