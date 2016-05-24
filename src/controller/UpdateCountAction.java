/* Name: Shan Gao
 * Date: 12/5/2015
 * Course number: 08672
 */

package controller;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.FavoriteBean;
import formbean.UserForm;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;

public class UpdateCountAction extends Action {

	private FavoriteDAO favoriteDAO;
	private UserDAO userDAO;
	private FormBeanFactory<UserForm> formBeanFactory = FormBeanFactory.getInstance(UserForm.class);

	public UpdateCountAction(Model model) {
		favoriteDAO = model.getFavoriteDAO();
		userDAO = model.getUserDAO();
	}

	public String getName() {
		return "updateCount.do";
	}

	public String perform(HttpServletRequest request) {
		String url = "";

		try {
			request.setAttribute("users", userDAO.getUsers());

			UserForm form = formBeanFactory.create(request);
			url = favoriteDAO.read(form.getIdAsInt()).getUrl();

			FavoriteBean favoriteBean = favoriteDAO.read(form.getIdAsInt());
			favoriteDAO.updateCount(favoriteBean);
		} catch (RollbackException e) {
			e.printStackTrace();
		} catch (FormBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!url.startsWith("http://"))
			url = "http://" + url;
		return url;
	}
}
