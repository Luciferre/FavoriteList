/* Name: Shan Gao
 * Date: 12/5/2015
 * Course number: 08672
 */

package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class UserForm extends FormBean {
	private String email = "";
	private String id;

	public String getEmail() {
		return email;
	}

	public void setEmail(String s) {
		email = trimAndConvert(s, "<>>\"]");
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (email == null || email.length() == 0) {
			errors.add("Email is required");
		}

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
