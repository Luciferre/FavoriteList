/* Name: Shan Gao
 * Date: 12/5/2015
 * Course number: 08672
 */

package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.FavoriteDAO;
import model.Model;
import model.UserDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.FavoriteBean;
import databean.UserBean;
import formbean.UserForm;

public class ListAction extends Action {
	private FormBeanFactory<UserForm> formBeanFactory = FormBeanFactory.getInstance(UserForm.class);

	private FavoriteDAO favoriteBean;
	private UserDAO userDAO;

	public ListAction(Model model) {
		favoriteBean = model.getFavoriteDAO();
		userDAO = model.getUserDAO();
	}

	public String getName() {
		return "list.do";
	}

	public String perform(HttpServletRequest request) {
		// Set up the request attributes (the errors list and the form bean so
		// we can just return to the jsp with the form if the request isn't
		// correct)
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			// Set up user list for nav bar
			request.setAttribute("users", userDAO.getUsers());

			UserForm form = formBeanFactory.create(request);

			String email = form.getEmail();
			if (email == null || email.length() == 0) {
				errors.add("User must be specified");
				return "error.jsp";
			}

			// Set up favorite list
			UserBean user = userDAO.getUserBean(email);
			if (user == null) {
				errors.add("Invalid User: " + email);
				return "error.jsp";
			}

			FavoriteBean[] favoriteBeans = favoriteBean.getFavoriteBeans(user.getId());
			request.setAttribute("favorites", favoriteBeans);
			return "list.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}
