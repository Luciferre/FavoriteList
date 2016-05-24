/* Name: Shan Gao
 * Date: 12/5/2015
 * Course number: 08672
 */

package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.UserBean;
import formbean.RegisterForm;
import model.Model;
import model.UserDAO;

public class RegisterAction extends Action {

	private FormBeanFactory<RegisterForm> formBeanFactory = FormBeanFactory.getInstance(RegisterForm.class);

	private UserDAO userDAO;

	public RegisterAction(Model model) {
		userDAO = model.getUserDAO();
	}

	public String getName() {
		return "register.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			RegisterForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			request.setAttribute("users", userDAO.getUsers());

			// If no params were passed, return with no errors so that the form
			// will be presented (we assume for the first time).
			if (!form.isPresent()) {
				return "register.jsp";
			}

			if (userDAO.getUserBean(form.getEmail()) != null) {
				errors.add("User already existed");
			}

			// Any validation errors?
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "register.jsp";
			}

			// Create the user bean
			UserBean user = new UserBean();
			user.setEmail(form.getEmail());
			user.setFirstName(form.getFirstname());
			user.setLastName(form.getLastname());
			user.setPassword(form.getPassword());
			userDAO.create(user);

			// Attach (this copy of) the user bean to the session
			HttpSession session = request.getSession(false);
			session.setAttribute("user", user);

			return "managelist.do";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "register.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "register.jsp";
		}
	}

}
