/* Name: Shan Gao
 * Date: 12/5/2015
 * Course number: 08672
 */

package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.UserBean;

public class UserDAO extends GenericDAO<UserBean> {

	public UserDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(UserBean.class, tableName, cp);
	}

	public UserBean getUserBean(String email) throws RollbackException {
		try {
			Transaction.begin();
			UserBean[] userBeans = match(MatchArg.equals("email", email));
			Transaction.commit();
			if (userBeans.length != 0)
				return userBeans[0];
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
		return null;
	}

	public void create(UserBean userBean) throws RollbackException {
		try {
			Transaction.begin();
			createAutoIncrement(userBean);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}

	}

	public UserBean[] getUsers() throws RollbackException {
		UserBean[] userBeans = match();
		return userBeans;
	}

	public void setPassword(String email, String password) throws RollbackException {
		try {
			UserBean dbUser = getUserBean(email);

			Transaction.begin();
			if (dbUser == null) {
				throw new RollbackException("User " + email + " no longer exists");
			}

			dbUser.setPassword(password);

			update(dbUser);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

}