package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}


	@Test
	public void testForUnauthorizedUsers(){
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testForAuthorizedUsers(){
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.singUp("firstname", "lastname", "username", "password");
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("username", "password");
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());
		driver.findElement(By.id("logoutButton")).click();
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	@Test
	public void newNoteIsCreated(){
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.singUp("firstname", "lastname", "username", "password");
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("username", "password");
		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.addNewNote("test", "test");
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("test", homePage.getNoteTitle());

	}

	@Test
	public void noteIsEdited() {
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.singUp("firstname", "lastname", "username", "password");
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("username", "password");
		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.addNewNote("test", "test");
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("test", homePage.getNoteDescription());

		homePage.clickEditNoteBtn("1", homePage.getNoteTitle(), homePage.getNoteDescription());

		homePage.addTextToNewNote(homePage.getNoteTitle(), "Changed description");
		driver.get("http://localhost:" + this.port + "/home");

		Assertions.assertEquals("Changed description", homePage.getNoteDescription());
		System.out.println(homePage.getNoteDescription());





	}

	@Test
	public void noteIsDeleted() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.singUp("firstname", "lastname", "testDeleteNote", "password");
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("testDeleteNote", "password");
		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.addNewNote("test", "test");
		driver.get("http://localhost:" + this.port + "/home");


		//driver.findElement(By.linkText("deleteNoteButton")).click();
		driver.findElement(By.xpath("//a[@id='nav-notes-tab']")).click();
		Assertions.assertEquals("test", homePage.getNoteDescription());

		driver.get("http://localhost:" + this.port + "/home");

		driver.findElement(By.xpath("//a[@id='nav-notes-tab']")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("deleteNoteButton")).click();
		driver.get("http://localhost:" + this.port + "/home");

		WebElement oldNote = null;

		try {
			oldNote = driver.findElement(By.id("addedNoteDescription"));
		} catch (NoSuchElementException e) { }

		Assertions.assertTrue(oldNote == null);
		//System.out.println(homePage.getNoteDescription());

	}

	@Test
	public void credentialsAreVisibleAndEncrypted() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.singUp("firstname", "lastname", "username", "password");
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("username", "password");
		driver.get("http://localhost:" + this.port + "/home");

		HomePage homePage = new HomePage(driver);
		homePage.clickCredentialsTab();

		homePage.addNewCredential("www.test1.com", "user1", "password1");

		driver.get("http://localhost:" + this.port + "/home");
		homePage.clickCredentialsTab();

		homePage.addNewCredential("www.test2.com", "user2", "password2");

		driver.get("http://localhost:" + this.port + "/home");
		homePage.clickCredentialsTab();

		Thread.sleep(1000);

		Assertions.assertEquals("user1", driver.findElement(By.id("credusernameuser1")).getAttribute("innerHTML"));
		Assertions.assertEquals("user2", driver.findElement(By.id("credusernameuser2")).getAttribute("innerHTML"));
		Assertions.assertFalse( (driver.findElement(By.id("credpassworduser1")).getAttribute("innerHTML")).equals("password1"));

	}

	@Test
	public void credentialsAreEdited() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.singUp("firstname", "lastname", "username", "password");
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("username", "password");
		driver.get("http://localhost:" + this.port + "/home");

		HomePage homePage = new HomePage(driver);
		homePage.clickCredentialsTab();

		homePage.addNewCredential("www.test1.com", "user1", "password1");

		driver.get("http://localhost:" + this.port + "/home");
		homePage.clickCredentialsTab();

		homePage.addNewCredential("www.test2.com", "user2", "password2");

		driver.get("http://localhost:" + this.port + "/home");
		homePage.clickCredentialsTab();

		Thread.sleep(1000);

		homePage.clieckEditCredentialButton("1", "www.test1.com", "user1", "password1");
		Thread.sleep(1000);

		String oldPassword = driver.findElement(By.id("credpassworduser1")).getAttribute("innerHTML");
		Assertions.assertEquals("password1", homePage.getPasswordFromEditing());

		((JavascriptExecutor) driver).executeScript("arguments[0].value='test';", homePage.credentialURL);
		((JavascriptExecutor) driver).executeScript("arguments[0].value='user1';", homePage.credentialUsername);
		((JavascriptExecutor) driver).executeScript("arguments[0].value='changed';", homePage.credentialPassword);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", homePage.saveCredentialButton);

		driver.get("http://localhost:" + this.port + "/home");
		homePage.clickCredentialsTab();

		String newPassword = driver.findElement(By.id("credpassworduser1")).getAttribute("innerHTML");

		Assertions.assertNotEquals(newPassword, oldPassword);

	}

	@Test
	public void credentialsDeleteSuccessful() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.singUp("firstname", "lastname", "testDeleteCred", "password");
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("testDeleteCred", "password");
		driver.get("http://localhost:" + this.port + "/home");

		HomePage homePage = new HomePage(driver);
		homePage.clickCredentialsTab();

		homePage.addNewCredential("www.test1.com", "user1", "password1");

		driver.get("http://localhost:" + this.port + "/home");
		homePage.clickCredentialsTab();

		homePage.addNewCredential("www.test2.com", "user2", "password2");

		driver.get("http://localhost:" + this.port + "/home");
		Thread.sleep(1500);
		homePage.clickCredentialsTab();




		WebElement deleteButton = driver.findElement(By.id("deleteCreduser2"));

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(deleteButton));
		deleteButton.click();



		driver.get("http://localhost:" + this.port + "/home");
		homePage.clickCredentialsTab();
		WebElement oldNote = null;

		try {
			oldNote = driver.findElement(By.id("deleteCreduser2"));
		} catch (NoSuchElementException e) { }

		Assertions.assertTrue(oldNote == null);




	}


}
