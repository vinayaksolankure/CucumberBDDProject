package StepDefinition;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import PageObject.AddNewCustomerPage;
import PageObject.LoginPage;
import PageObject.SearchCustomerPage;
import Utilities.ReadConfig;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginStepDef extends BaseClass {

	//@Before(order = 1)
	@Before("@Sanity") //Conditional Hooks
	public void setup1() throws IOException {
		readConfig = new ReadConfig();

		log = LogManager.getLogger("LoginStepDef");
		log = LogManager.getLogger("CloseStep");

		System.out.println("Setup1-Sanity method executed.....");
		String browser = readConfig.getBrowser();

		switch(browser.toLowerCase()) 
		{
		case "chrome":
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"}); 
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			break;

		case "msedge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;

		default:
			driver = null;
			break;
		}

		log.info("Setup1 executed........");
	}

	//@Before(order = 0)
	@Before("@regression") //Conditional Hooks
	public void setup2() {
		log = LogManager.getLogger("LoginStepDef");

		System.out.println("Setup2-Regression method executed.....");
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"}); 
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();

		log.info("Setup2 executed........");
	}

	@BeforeStep
	public void beforeStepMethodDemo() {
		System.out.println("Before step method.......");
	}

	@Given("User Launch Chrome browser")
	public void user_launch_chrome_browser() {
		loginPg = new LoginPage(driver);
		addNewCustomerPage  = new AddNewCustomerPage(driver);
		searchCustomerPage  = new SearchCustomerPage(driver);
		log.info("User lauched Chrome browser........");
	}

	@When("User opens URL {string}")
	public void user_opens_url(String url) {
		driver.get(url);
		log.info("URL Opened........");
	}

	@When("User enters Email as {string} and Password as {string}")
	public void user_enters_email_as_and_password_as(String email, String pwd) {
		loginPg.enterEmail(email);
		loginPg.enterPassword(pwd);
		log.info("Entered Email and Password........");
	}

	@When("Click on Login")
	public void click_on_login() {
		loginPg.clickOnLoginBtn();
		log.info("Clicked on login button........");
	}

	@Then("Page Title should be {string}")
	public void page_title_should_be(String expectedTitle) {
		String actualTitle = driver.getTitle();

		if(actualTitle.equals(expectedTitle))
		{
			Assert.assertTrue(true); //pass
			log.info("Test passed - Login Feature : Page Title matched......");
		}
		else
		{
			Assert.assertTrue(false); //fail
			log.warn("Test failed - Login Feature : Page Title not matched......");
		}
	}

	@When("User click on Log out link")
	public void user_click_on_log_out_link() {
		loginPg.clickOnLogOut();
		log.info("Clicked on Log out button.....");
	}




	//////////////////////////////// Add New Customer ///////////////////////////////////


	@Then("User can view Dashboad")
	public void user_can_view_dashboad() {
		String actualTitle = addNewCustomerPage.getPageTitle();
		String expectedTitle = "Dashboard / nopCommerce administration";
		if(actualTitle.equals(expectedTitle))
		{
			Assert.assertTrue(true);
			log.info("Test passed - User can view dashboard : Page Title matched......");
		}
		else
		{
			Assert.assertTrue(false);
			log.warn("Test failed - User can view dashboard : Page Title not matched......");
		}
	}

	@When("User click on customers Menu")
	public void user_click_on_customers_menu() throws InterruptedException {
		addNewCustomerPage.clickOnCustomersMenu();
		Thread.sleep(5000);
		log.info("Clicked on customer menu....");
	}

	@When("click on customers Menu Item")
	public void click_on_customers_menu_item() {
		addNewCustomerPage.clickOnCustomersMenuItem();
		log.info("Clicked on customer menu item....");
	}

	@When("click on Add new button")
	public void click_on_add_new_button() {
		addNewCustomerPage.clickOnAddnew();
		log.info("Clicked on add new button....");
	}

	@Then("User can view Add new customer page")
	public void user_can_view_add_new_customer_page() {
		String actualTitle = addNewCustomerPage.getPageTitle();
		String expectedTitle = "Add a new customer / nopCommerce administration";
		if(actualTitle.equals(expectedTitle))
		{
			Assert.assertTrue(true);
			log.info("Test passed - User can view and add new customer : Page Title matched......");
		}
		else
		{
			Assert.assertTrue(false);
			log.warn("Test failed - User can view and add new customer : Page Title not matched......");
		}
	}

	@When("User enter customer info")
	public void user_enter_customer_info() throws InterruptedException {
		addNewCustomerPage.enterEmail(generateEmailID() + "@gmail.com");
		addNewCustomerPage.enterPassword("test10");
		addNewCustomerPage.enterFirstName("Test10");
		addNewCustomerPage.enterLastName("Automation");
		addNewCustomerPage.enterGender("Male");
		addNewCustomerPage.enterDob("06/12/2005");
		addNewCustomerPage.enterCompanyName("ABC INDIA PVT LTD");
		addNewCustomerPage.enterAdminContent("AdminContent");
		addNewCustomerPage.enterManagerOfVendor("Vendor 1");
		Thread.sleep(5000);
		log.info("Entered Customer info....");
	}

	@When("click on Save button")
	public void click_on_save_button() {
		addNewCustomerPage.clickOnSave();
		log.info("Clicked on save button....");
	}

	@Then("User can view confirmation message {string}")
	public void user_can_view_confirmation_message(String expectedConfirmationMsg)
	{
		String bodyTag = driver.findElement(By.tagName("Body")).getText();
		if(bodyTag.contains(expectedConfirmationMsg))
		{
			Assert.assertTrue(true);
			log.info("Test passed - User can view confirmation message : Confimation msg matched......");
		}
		else
		{
			Assert.assertTrue(false);
			log.warn("Test failed - User can view confirmation message : Confimation msg not matched......");
		}
	}

	////////////////////////////////////// Search Customer By Email ///////////////////////////////////////

	@When("Enter customer EMail")
	public void enter_customer_e_mail() {
		searchCustomerPage.enterEmailAdd("victoria_victoria@nopCommerce.com");
		log.info("Entered Customer emailID ....");
	}

	@When("Click on search button")
	public void click_on_search_button() {
		searchCustomerPage.clickOnSearchButton();
		log.info("Clicked on search button....");
	}

	@Then("User should found Email in the Search table")
	public void user_should_found_email_in_the_search_table() throws InterruptedException {
		Thread.sleep(5000);
		String expectedEmail = "victoria_victoria@nopCommerce.com";

		if(searchCustomerPage.searchCustomerByEmail(expectedEmail) == true)
		{
			Assert.assertTrue(true);
			log.info("Test passed - User should found Email in the Search table : email verified......");
		}
		else
		{
			Assert.assertTrue(false);
			log.warn("Test failed - User should found Email in the Search table : email not verified......");
		}
	}

	/////////////////////////////////// Search Customer By Name ///////////////////////////////////////////////

	@When("Enter customer FirstName")
	public void enter_customer_first_name() {
		searchCustomerPage.enterFirstName("Victoria");
		log.info("Entered first name....");
	}

	@When("Enter customer LastName")
	public void enter_customer_last_name() {
		searchCustomerPage.enterLastName("Terces");
		log.info("Entered last name....");
	}

	@Then("User should found Name in the Search table")
	public void user_should_found_name_in_the_search_table() throws InterruptedException {
		Thread.sleep(5000);
		String expectedName = "Victoria Terces";

		if(searchCustomerPage.searchCustomerByName(expectedName) == true)
		{
			Assert.assertTrue(true);
			log.info("Test passed - User should found name in the Search table : name verified......");
		}
		else
		{
			Assert.assertTrue(false);
			log.warn("Test failed - User should found name in the Search table : name verified......");
		}
	}

	@After//(order = 0) //reverse ordering value
	public void teardown1(Scenario sc) throws IOException {
		System.out.println("tear down method executed.....");
		if (driver != null) {
			driver.quit();
		}
		//		if(sc.isFailed()==true)
		//		{
		//			captureScreenshot(driver,sc);
		//			log.error("Captured Screenshot.....");
		//		}
	}


	//	@After(order = 1 ) //reverse ordering value
	//	public void teardown2() {
	//		System.out.println("tear down method executed.....");
	//		driver.quit();
	//	}

	@AfterStep
	public void addScreenshot(Scenario scenario){

		if(scenario.isFailed())
		{
			final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			//attach image file report(data, media type, name of the attachment)
			scenario.attach(screenshot, "image/png", scenario.getName());
		}

	}

	public void captureScreenshot(WebDriver driver, Scenario sc) throws IOException 
	{
		// Step 1 : convert webdriver object to takescreenshot interface
		TakesScreenshot screenshot = ((TakesScreenshot) driver);  // Typecasted

		// Step 2 : call getScreenShotAs method to create img file
		File src = screenshot.getScreenshotAs(OutputType.FILE);

		File dest = new File(System.getProperty("user.dir") + "\\Screenshots\\" + sc + ".png" );

		// Step 3 : Copy img file to dest.
		FileUtils.copyFile(src, dest);
	}

}
