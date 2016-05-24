/* Name: Shan Gao
 * Date: 12/5/2015
 * Course number: 08672
 */

package formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBean;

public class LoginForm extends FormBean {
	private String email;
	private String password;

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (email == null || email.length() == 0)
			errors.add("Email is required");
		if (password == null || password.length() == 0)
			errors.add("Password is required");

		return errors;
	}

	public void setEmail(String email) {
		this.email = trimAndConvert(email, "<>\"");
	}

	public void setPassword(String password) {
		this.password = password.trim();
	}

}
