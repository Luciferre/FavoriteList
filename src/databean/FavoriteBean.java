/* Name: Shan Gao
 * Date: 12/5/2015
 * Course number: 08672
 */

package databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class FavoriteBean {
	private int id;
	private int userId;
	private String url;
	private String comment;
	private int clickCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getClickCount() {
		return clickCount;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

}
