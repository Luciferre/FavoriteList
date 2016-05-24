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

public class AddFavoriteAction extends Action {

	private FormBeanFactory<FavoriteForm> formBeanFactory = FormBeanFactory.getInstance(FavoriteForm.class);

	private FavoriteDAO favoriteDAO;
	private UserDAO userDAO;

	public AddFavoriteAction(Model model) {
		favoriteDAO = model.getFavoriteDAO();
		userDAO = model.getUserDAO();
	}

	public String getName() {
		return "addFavorite.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		try {
			// Set up user list for nav bar
			request.setAttribute("users", userDAO.getUsers());

			// If no params were passed, return with no errors so that the form
			// will be presented (we assume for the first time).
			FavoriteForm favoriteForm = formBeanFactory.create(request);
			request.setAttribute("form", favoriteForm);

			if (!favoriteForm.isPresent()) {
				return "managelist.jsp";
			}

			UserBean user = (UserBean) request.getSession().getAttribute("user");
			errors.addAll(favoriteForm.getValidationErrors());
			if (errors.size() != 0) {
				FavoriteBean[] favoriteBeans = favoriteDAO.getFavoriteBeans(user.getId());
				request.setAttribute("favorites", favoriteBeans);
				return "managelist.jsp";
			}

			FavoriteBean favoriteBean = new FavoriteBean();
			favoriteBean.setUserId(user.getId());
			favoriteBean.setUrl(favoriteForm.getUrl());
			favoriteBean.setComment(favoriteForm.getComment());
			favoriteBean.setClickCount(0);
			favoriteDAO.create(favoriteBean);

			return "managelist.do";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}
