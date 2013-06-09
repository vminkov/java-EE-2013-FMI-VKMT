package c2h5oh.controller;

import java.util.List;

import javax.resource.spi.SecurityException;
import javax.security.auth.login.LoginException;

import c2h5oh.beans.roles.Role;
import c2h5oh.controller.exceptions.NoSuchUserException;
import c2h5oh.controller.exceptions.UserCreationException;
import c2h5oh.controller.exceptions.UserExistsException;
import c2h5oh.controller.exceptions.WrongPasswordException;
import c2h5oh.jpa.User;

public interface IUserManager {

	public void removeUser(Long userId, String comment);

	public User login(String username, String password, Role loginType)
			throws SecurityException, WrongPasswordException,
			NoSuchUserException, LoginException;

	public User loginWithCookie(String username, String passwordHash,
			Role loginType) throws WrongPasswordException, SecurityException,
			NoSuchUserException;

	public User createNewUser(String username, String password, Role role)
			throws UserExistsException, UserCreationException;

	public boolean isDirector(String username);

	public boolean isBartender(String username);

	public boolean isWaiter(String username);

	public User getUser(String username);

	public User updateUserInfo(User user);

	public List<User> getAllUsers();

	public long getAllUsersCount();

}