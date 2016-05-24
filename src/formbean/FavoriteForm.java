/* Name: Shan Gao
 * Date: 12/5/2015
 * Course number: 08672
 */

package formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBean;

public class FavoriteForm extends FormBean {

	private String id;

	private String url;

	private String comment;

	public String getUrl() {
		return url;
	}

	public String getComment() {
		return comment;
	}

	public void setUrl(String url) {
		this.url = url.trim();
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (url == null || url.length() == 0)
			errors.add("URL is required");

		if (errors.size() > 0)
			return errors;

		if (url.matches(".*[<>\"].*"))
			errors.add("URL may not contain angle brackets or quotes");

		if (comment.matches(".*[<>\"].*"))
			errors.add("Comment may not contain angle brackets or quotes");

		return errors;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIdAsInt() {
		try {
			return Integer.parseInt(id);
		} catch (NumberFormatException e) {
			// call getValidationErrors() to detect this
			return -1;
		}
	}

}
