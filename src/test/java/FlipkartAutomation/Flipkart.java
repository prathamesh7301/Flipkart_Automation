package FlipkartAutomation;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;

public class Flipkart {
	 WebDriver driver;
	 boolean isLoggedIn = false;

	    // Locator for the Login button
	    By LoginBtn = By.xpath("//a[@title='Login']/span[text()='Login']");
	    
	    //Locator to add Mobile Number
	    By addMobileNumber = By.xpath("//input[@type='text' and @class='r4vIwl BV+Dqf' and @autocomplete='off' and @value='']");
	    
	    //Locator to clk on Request OTP Button
	    By RequestOTP = By.xpath("//button[contains(text(), 'Request OTP')]");
	    
	    //Locator to click on submit button
	    By VerifyBtn = By.xpath("//button[@type='submit' and text()='Verify']");

	    //Locator of Search Bar  
	    By SearchBar = By.xpath("//input[@title='Search for Products, Brands and More']");
	    
	    
	    @BeforeTest
	    public void beforeTest() {
	    	
	    	//Step 1: Create a New Firefox Profile (Manual Step)
	       // Step 2: Close all Firefox windows.
	       // Step 3: Open terminal / run prompt:   -->  firefox -P
	      //  Step 4: Click “Create Profile” → Name it something like FlipkartProfile.
	      //Step 5: Launch Firefox with this new profile.
	    //Step 6: Navigate to https://www.flipkart.com and log in manually.
	    	//Step 7: Close Firefox. Session and login will be saved in that profile.
	    	
	    	
	    	 // Path to the Firefox profile you created
	        String profilePath = "C:\\Users\\Prathamesh\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\4u8oe98g.FlipkartAutomation";

	        FirefoxProfile profile = new FirefoxProfile(new File(profilePath));
	        FirefoxOptions options = new FirefoxOptions();
	        options.setProfile(profile);
	    	
	        driver = new FirefoxDriver(options);  
	    	
	    	//driver = new FirefoxDriver();
	       // driver.manage().window().maximize();
	       // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	    }

	    @Test(priority = 0)
	    public void openFlipkart() {
	        driver.get("https://www.flipkart.com/");
	        
	        // Wait for the main body tag to ensure page is fully loaded
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
	    }
	    
	    @Test(priority = 1, dependsOnMethods = "openFlipkart")
	    public void checkIfLoggedIn() {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        try {
	            wait.until(ExpectedConditions.visibilityOfElementLocated(LoginBtn));
	            System.out.println("User is not logged in. Proceeding with login.");
	            isLoggedIn = false;
	        } catch (TimeoutException e) {
	            System.out.println("User already logged in. Skipping login steps.");
	            isLoggedIn = true;
	            throw new SkipException("Already logged in. Skipping login tests.");
	        }
	    }

	    @Test(priority = 2, dependsOnMethods = "checkIfLoggedIn")
	    public void clickLoginButton() {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        wait.until(ExpectedConditions.elementToBeClickable(LoginBtn)).click();
	    }

	    @Test(priority = 3, dependsOnMethods = "clickLoginButton")
	    public void mobileNumber() {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        
	        // Please add your mobile number in sendkeys
	        wait.until(ExpectedConditions.visibilityOfElementLocated(addMobileNumber)).sendKeys("add your Mobile Number");
	    }

	    @Test(priority = 4, dependsOnMethods = "mobileNumber")
	    public void requestOTPbtn() {
	        driver.findElement(RequestOTP).click();
	    }

	    @Test(priority = 5, dependsOnMethods = "requestOTPbtn")
	    public void waitForManualOTPAndClickVerify() {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	        wait.until(driver -> {
	            for (int i = 1; i <= 6; i++) {
	                String xpath = "(//input[@type='text' and @maxlength='1'])[" + i + "]";
	                String value = driver.findElement(By.xpath(xpath)).getDomProperty("value");
	                if (value == null || value.trim().isEmpty()) {
	                    return false;
	                }
	            }
	            return true;
	        });

	        wait.until(ExpectedConditions.elementToBeClickable(VerifyBtn)).click();
	    }
	    
	    @Test(priority = 6)
	    public void SearchProduct() throws InterruptedException {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	        try {
	            WebElement closePopup = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'✕')]")));
	            closePopup.click();
	            System.out.println("Login popup closed.");
	        } catch (TimeoutException e) {
	            System.out.println("Login popup not found or already closed.");
	        }

	        try {
	            WebElement searchElement = wait.until(ExpectedConditions.elementToBeClickable(SearchBar));
	            searchElement.clear();
	            searchElement.sendKeys("Mobiles");
	            searchElement.submit();
	            System.out.println("Search submitted successfully.");
	        } catch (TimeoutException e) {
	            System.out.println("Search bar not found on the page within timeout.");
	            throw e;
	        }

	        try {
	            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, '_1AtVbE')]")));
	            System.out.println("Search results loaded.");
	        } catch (TimeoutException e) {
	            System.out.println("Search results did not load in time.");
	        }
	    }

	    @Test(priority = 7, dependsOnMethods = "SearchProduct")
	    public void fetchProductDetails() {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, '_1AtVbE')]")));

	        List<WebElement> products = driver.findElements(By.xpath("//div[contains(@class, '_1AtVbE') and .//div[contains(@class, '_4rR01T')]]"));

	        System.out.println("---- Flipkart Mobile Products ----");

	        int count = 0;
	        for (WebElement product : products) {
	            try {
	                WebElement nameElement = product.findElement(By.xpath(".//div[contains(@class, '_4rR01T')]"));
	                String name = nameElement.getText();

	                WebElement priceElement = product.findElement(By.xpath(".//div[contains(@class, '_30jeq3')]"));
	                String price = priceElement.getText();

	                String rating = "N/A";
	                try {
	                    WebElement ratingElement = product.findElement(By.xpath(".//div[contains(@class, '_3LWZlK')]"));
	                    rating = ratingElement.getText();
	                } catch (Exception e) {
	                    // Rating not available
	                }

	                count++;
	                System.out.println(count + ". " + name + " | Price: " + price + " | Rating: " + rating);

	            } catch (Exception e) {
	                System.out.println("Error fetching product details");
	            }
	        }

	        if (count == 0) {
	            System.out.println("No product details found. Flipkart may have changed the page layout.");
	        }
	    }
	    
	    
  @AfterTest
  public void afterTest() {
  }

}
