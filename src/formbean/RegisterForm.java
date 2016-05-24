/* Name: Shan Gao
 * Date: 12/5/2015
 * Course number: 08672
 */

package formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBean;

public class RegisterForm extends FormBean {
	private String email;
	private String firstname;
	private String lastname;
	private String password;
	private String confirm;

	public void setEmail(String email) {
		this.email = trimAndConvert(email, "<>\"");
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String s) {
		confirm = s.trim();
	}

	public void setFirstname(String firstname) {
		this.firstname = trimAndConvert(firstname, "<>\"");
	}

	public void setLastname(String lastname) {
		this.lastname = trimAndConvert(lastname, "<>\"");
	}

	public void setPassword(String password) {
		this.password = password.trim();
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (firstname == null || firstname.length() == 0) {
			errors.add("First Name is required");
		}

		if (lastname == null || lastname.length() == 0) {
			errors.add("Last Name is required");
		}

		if (email == null || email.length() == 0) {
			errors.add("Email is required");
		}

		if (password == null || password.length() == 0) {
			errors.add("Password is required");
		}

		if (confirm == null || confirm.length() == 0) {
			errors.add("Confirm Password is required");
		}

		if (errors.size() > 0) {
			return errors;
		}

		if (!password.equals(confirm)) {
			errors.add("Passwords are not the same");
		}

		return errors;

	}
}
