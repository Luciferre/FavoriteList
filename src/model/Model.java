/* Name: Shan Gao
 * Date: 12/5/2015
 * Course number: 08672
 */

package model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;

public class Model {
	private FavoriteDAO favoriteDAO;
	private UserDAO userDAO;

	public Model(ServletConfig config) throws ServletException {
		try {
			String jdbcDriver = config.getInitParameter("jdbcDriver");
			String jdbcURL    = config.getInitParameter("jdbcURL");
			
			ConnectionPool pool = new ConnectionPool(jdbcDriver,jdbcURL);
			
			userDAO  = new UserDAO(pool, "shang_user");
			favoriteDAO = new FavoriteDAO(pool, "shang_favorite");
		} catch (DAOException e) {
			throw new ServletException(e);
		}
	}
	
	public FavoriteDAO getFavoriteDAO()  { return favoriteDAO; }
	public UserDAO getUserDAO()  { return userDAO; }
}
