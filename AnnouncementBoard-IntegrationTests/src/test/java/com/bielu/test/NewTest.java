package com.bielu.test;

import com.thoughtworks.selenium.SeleneseTestCase;

public class NewTest extends SeleneseTestCase {
	@Override
	public void setUp() throws Exception {
		setUp("http://localhost:8080/AnnouncementBoard/index.jsps");
	}

	public void testNew() throws Exception {
		selenium.open("http://localhost:8080/AnnouncementBoard/");
		
		selenium.type("doLogIn_username", "nonexisting");
		selenium.type("doLogIn_password", "password");
		selenium.click("doLogIn_input_logIn");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("User does not exist in our database"));
		selenium.click("link=Sign Up");
		selenium.waitForPageToLoad("30000");
		selenium.type("doSignUp_user_firstName", "Test");
		selenium.type("doSignUp_user_lastName", "Name");
		selenium.type("doSignUp_user_username", "test");
		selenium.type("doSignUp_password", "password");
		selenium.type("doSignUp_confirmPassword", "password");
		selenium.type("doSignUp_user_email", "test@gmail.com");
		selenium.type("doSignUp_confirmEmail", "test@gmail.com");
		selenium.click("doSignUp_input_signUp");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("User name:"));
		selenium.type("doLogIn_username", "test");
		selenium.type("doLogIn_password", "password");
		selenium.click("doLogIn_input_logIn");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("exact:Title*:"));
		verifyTrue(selenium.isTextPresent("exact:Description*:"));
		selenium.type("create_announcement_title", "Test announcement");
		selenium.select("create_announcement_category", "label=House");
		selenium.type("create_announcement_description", "some description");
		selenium.type("city", "Sopot");
		selenium.click("link=Update map position");
		selenium.click("create_input_create");
		selenium.waitForPageToLoad("30000");
	}
}