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

import databean.FavoriteBean;

public class FavoriteDAO extends GenericDAO<FavoriteBean> {

	public FavoriteDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(FavoriteBean.class, tableName, cp);
	}

	public FavoriteBean[] getFavoriteBeans(int userid) throws RollbackException {
		try {
			Transaction.begin();
			FavoriteBean[] favoriteBeans = match(MatchArg.equals("userid", userid));
			Transaction.commit();
			return favoriteBeans;
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

	public void create(FavoriteBean favoriteBean) throws RollbackException {
		try {
			Transaction.begin();
			createAutoIncrement(favoriteBean);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}

	}

	public void updateCount(FavoriteBean favoriteBean) throws RollbackException {
		try {
			Transaction.begin();
			int count = favoriteBean.getClickCount();
			favoriteBean.setClickCount(count + 1);
			update(favoriteBean);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

	public void delete(int id, int userId) throws RollbackException {
		try {
			FavoriteBean favoriteBean = read(id);

			Transaction.begin();

			if (favoriteBean == null) {
				throw new RollbackException("Favorite does not exist: id=" + id);
			}

			if (userId != favoriteBean.getUserId()) {
				throw new RollbackException("UserID not owned by " + userId);
			}

			delete(id);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

}