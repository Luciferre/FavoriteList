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

import model.Model;
import model.UserDAO;

public class LogoutAction extends Action {
	private UserDAO userDAO;

	public LogoutAction(Model model) {
		userDAO = model.getUserDAO();

	}

	public String getName() {
		return "logout.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			HttpSession session = request.getSession(false);
			session.setAttribute("user", null);

			request.setAttribute("message", "You are now logged out");
			request.setAttribute("users", userDAO.getUsers());
			return "success.jsp";

		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";

		}

	}
}
