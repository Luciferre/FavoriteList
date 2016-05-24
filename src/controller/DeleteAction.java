/* Name: Shan Gao
 * Date: 12/5/2015
 * Course number: 08672
 */

package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.FavoriteBean;
import databean.UserBean;
import formbean.FavoriteForm;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;

public class DeleteAction extends Action {
	private FormBeanFactory<FavoriteForm> formBeanFactory = FormBeanFactory.getInstance(FavoriteForm.class);

	private FavoriteDAO favoriteDAO;
	private UserDAO userDAO;

	public DeleteAction(Model model) {
		favoriteDAO = model.getFavoriteDAO();
		userDAO = model.getUserDAO();
	}

	public String getName() {
		return "delete.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			// Set up user list for nav bar
			FavoriteForm form = formBeanFactory.create(request);
			//request.setAttribute("form", form);
			request.setAttribute("users", userDAO.getUsers());

			UserBean user = (UserBean) request.getSession().getAttribute("user");

			int id = form.getIdAsInt();
			System.out.println(id);
			favoriteDAO.delete(id, user.getId());

			// Be sure to get the photoList after the delete
			FavoriteBean[] favoriteBeans = favoriteDAO.getFavoriteBeans(user.getId());
			request.setAttribute("favorites", favoriteBeans);

			return "managelist.jsp";
		} catch (RollbackException e) {
			System.out.println("r");
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (FormBeanException e) {
			System.out.println("f");
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}
