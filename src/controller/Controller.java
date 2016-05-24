/* Name: Shan Gao
 * Date: 12/5/2015
 * Course number: 08672
 */

package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import databean.FavoriteBean;
import databean.UserBean;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	private FavoriteDAO favoriteDAO;

	public void init() throws ServletException {
		Model model = new Model(getServletConfig());
		Action.add(new ChangePwdAction(model));
		Action.add(new UpdateCountAction(model));
		Action.add(new RegisterAction(model));
		Action.add(new ManageListAction(model));
		Action.add(new LoginAction(model));
		Action.add(new LogoutAction(model));
		Action.add(new AddFavoriteAction(model));
		Action.add(new DeleteAction(model));
		Action.add(new ListAction(model));
		initializeTables(model);

	}

	public void initializeTables(Model model) {
		userDAO = model.getUserDAO();
		favoriteDAO = model.getFavoriteDAO();
		String url1 = "www.cmu.edu";
		String url2 = "www.google.com";
		String url3 = "www.youtube.com";
		String url4 = "www.twitter.com";
		try {
			if (userDAO.getCount() == 0) {
				int userId1 = createUser("1");
				int userId2 = createUser("2");
				int userId3 = createUser("3");

				createFavorite(url1, userId1);
				createFavorite(url2, userId1);
				createFavorite(url3, userId1);
				createFavorite(url4, userId1);

				createFavorite(url1, userId2);
				createFavorite(url2, userId2);
				createFavorite(url3, userId2);
				createFavorite(url4, userId2);

				createFavorite(url1, userId3);
				createFavorite(url2, userId3);
				createFavorite(url3, userId3);
				createFavorite(url4, userId3);

			}
		} catch (RollbackException e) {
			e.printStackTrace();
		}
	}

	public int createUser(String str) {
		UserBean userBean = new UserBean();
		userBean.setEmail(str);
		userBean.setFirstName(str);
		userBean.setLastName(str);
		userBean.setPassword(str);
		int userId = 0;
		try {
			userDAO.create(userBean);
			userId = userDAO.getUserBean(str).getId();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userId;
	}

	public void createFavorite(String url, int userId) {
		FavoriteBean favoriteBean = new FavoriteBean();
		favoriteBean.setUrl(url);
		favoriteBean.setUserId(userId);
		favoriteBean.setClickCount(0);
		try {
			favoriteDAO.create(favoriteBean);
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = performTheAction(request);
		sendToNextPage(nextPage, request, response);
	}

	/*
	 * Extracts the requested action and (depending on whether the user is
	 * logged in) perform it (or make the user login).
	 * 
	 * @param request
	 * 
	 * @return the next page (the view)
	 */
	private String performTheAction(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String servletPath = request.getServletPath();
		UserBean user = (UserBean) session.getAttribute("user");
		String action = getActionName(servletPath);

		if (action.equals("register.do") || action.equals("login.do")) {
			// Allow these actions without logging in
			return Action.perform(action, request);
		}

		if (user == null) {
			// visitor can see and clicke others' list
			if (action.contains("list.do")) {
				return Action.perform(action, request);
			}
			if (action.contains("updateCount.do")) {
				return Action.perform(action, request);
			}

			// If the user hasn't logged in, so login is the only option
			return Action.perform("login.do", request);
		}

		// Let the logged in user run his chosen action
		return Action.perform(action, request);
	}

	/*
	 * If nextPage is null, send back 404 If nextPage ends with ".do", redirect
	 * to this page. If nextPage ends with ".jsp", dispatch (forward) to the
	 * page (the view) This is the common case
	 */
	private void sendToNextPage(String nextPage, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if (nextPage == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, request.getServletPath());
			return;
		}

		if (nextPage.endsWith(".do")) {
			response.sendRedirect(nextPage);
			return;
		}

		if (nextPage.endsWith(".jsp")) {
			RequestDispatcher d = request.getRequestDispatcher("WEB-INF/" + nextPage);
			d.forward(request, response);
			return;
		}

		if (nextPage.startsWith("http")) {
			response.sendRedirect(nextPage);
			return;
		}

		throw new ServletException(
				Controller.class.getName() + ".sendToNextPage(\"" + nextPage + "\"): invalid extension.");
	}

	/*
	 * Returns the path component after the last slash removing any "extension"
	 * if present.
	 */
	private String getActionName(String path) {
		// We're guaranteed that the path will start with a slash
		int slash = path.lastIndexOf('/');
		String name = path.substring(slash + 1);
		System.out.println(name);
		return name;
	}
}
