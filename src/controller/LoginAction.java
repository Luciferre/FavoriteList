/* Name: Shan Gao
 * Date: 12/5/2015
 * Course number: 08672
 */

package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.DuplicateKeyException;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.FavoriteBean;
import databean.UserBean;
import formbean.LoginForm;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;

public class LoginAction extends Action {

	private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);

	private UserDAO userDAO;

	public LoginAction(Model model) {
		userDAO = model.getUserDAO();
	}

	@Override
	public String getName() {
		return "login.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			LoginForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			request.setAttribute("users", userDAO.getUsers());

			// If no params were passed, return with no errors so that the form
			// will be
			// presented (we assume for the first time).
			if (!form.isPresent()) {
				return "login.jsp";
			}

			// Any validation errors?
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "login.jsp";
			}

			// Look up the user
			UserBean userBean = userDAO.getUserBean(form.getEmail());

			if (userBean == null) {
				errors.add("User Name not found");
				return "login.jsp";
			}

			// Check the password
			if (!userBean.getPassword().equals(form.getPassword())) {
				errors.add("Incorrect password");
				return "login.jsp";
			}

			// Attach (this copy of) the user bean to the session
			HttpSession session = request.getSession();
			session.setAttribute("user", userBean);

			// If redirectTo is null, redirect to the "todolist" action
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
