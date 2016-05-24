/* Name: Shan Gao
 * Date: 12/5/2015
 * Course number: 08672
 */

package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import databean.FavoriteBean;
import databean.UserBean;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;

public class ManageListAction extends Action {

	private FavoriteDAO favoriteDAO;
	private UserDAO userDAO;

	public ManageListAction(Model model) {
		favoriteDAO = model.getFavoriteDAO();
		userDAO = model.getUserDAO();
	}

	public String getName() {
		return "managelist.do";
	}

	public String perform(HttpServletRequest request) {
		// Set up the errors list
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			// Set up user list for nav bar
			request.setAttribute("users", userDAO.getUsers());

			UserBean user = (UserBean) request.getSession(false).getAttribute("user");
			FavoriteBean[] favoriteBeans = favoriteDAO.getFavoriteBeans(user.getId());
			request.setAttribute("favorites", favoriteBeans);

			return "managelist.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}
