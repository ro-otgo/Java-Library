package application;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class GenerarPWD {

	public static void main(String[] args) {

		String password = "12345678";
		String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
		System.out.println(bcryptHashString);
	}

}
