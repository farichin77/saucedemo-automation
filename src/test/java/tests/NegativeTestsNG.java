package tests;

import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.*;

import java.io.File;
import java.time.Duration;

@Epic("SauceDemo E-commerce")
@Feature("Negative Test Scenarios")
public class NegativeTestsNG {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    @BeforeMethod
    public void ensureCleanLoginPage() {
        System.out.println("🔄 Ensuring clean login page...");
        try {
            // Try to close any error popup first
            try {
                WebElement errorButton = driver.findElement(By.cssSelector("button.error-button"));
                errorButton.click();
                delay(1);
            } catch (Exception e) {
                // No error popup found, continue
            }
            // Navigate to login page and refresh
            driver.get("https://www.saucedemo.com/");
            delay(2);
            // Wait for login form to be ready
            wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
            System.out.println("✅ Clean login page ready");
        } catch (Exception e) {
            System.out.println("⚠️ Error ensuring clean login page: " + e.getMessage());
            // Force refresh as fallback
            driver.navigate().refresh();
            delay(2);
        }
    }
    
    public static void delay(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void takeScreenshot(WebDriver driver, String filename) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        try {
            File target = new File("screenshots/" + filename + ".png");
            target.getParentFile().mkdirs();
            src.renameTo(target);
            System.out.println("📸 Screenshot saved: " + target.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("❌ Failed to save screenshot: " + e.getMessage());
        }
    }

    @Test(description = "Invalid username test")
    @Story("Login Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test login with invalid username")
    public void testInvalidUsername() {
        Allure.step("Enter invalid username");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys("invalid_user");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        
        Allure.step("Verify error message");
        String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        if (errorMsg.contains("Username and password do not match")) {
            System.out.println("✅ Invalid username test passed");
        } else {
            System.out.println("❌ Invalid username test failed");
            throw new RuntimeException("Invalid username test failed");
        }
        takeScreenshot(driver, "negative_test_invalid_username");
    }

    @Test(description = "Invalid password test")
    @Story("Login Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test login with invalid password")
    public void testInvalidPassword() {
        Allure.step("Enter invalid password");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("wrong_password");
        driver.findElement(By.id("login-button")).click();
        
        Allure.step("Verify error message");
        String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        if (errorMsg.contains("Username and password do not match")) {
            System.out.println("✅ Invalid password test passed");
        } else {
            System.out.println("❌ Invalid password test failed");
            throw new RuntimeException("Invalid password test failed");
        }
        takeScreenshot(driver, "negative_test_invalid_password");
    }

    @Test(description = "Locked out user test")
    @Story("User Account Status")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test login with locked out user account")
    public void testLockedOutUser() {
        Allure.step("Login with locked out user");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        
        Allure.step("Verify locked out message");
        String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        if (errorMsg.contains("Sorry, this user has been locked out")) {
            System.out.println("✅ Locked out user test passed");
        } else {
            System.out.println("❌ Locked out user test failed");
            throw new RuntimeException("Locked out user test failed");
        }
        takeScreenshot(driver, "negative_test_locked_user");
    }

    @Test(description = "Empty fields test")
    @Story("Form Validation")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test login with empty username and password")
    public void testEmptyFields() {
        Allure.step("Submit empty form");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("login-button")).click();
        
        Allure.step("Verify error message");
        String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        if (errorMsg.contains("Username is required")) {
            System.out.println("✅ Empty fields test passed");
        } else {
            System.out.println("❌ Empty fields test failed");
            throw new RuntimeException("Empty fields test failed");
        }
        takeScreenshot(driver, "negative_test_empty_fields");
    }

    @Test(description = "Problem user test")
    @Story("User Account Issues")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test login with problem user (expected UI issues)")
    public void testProblemUser() {
        Allure.step("Login with problem user");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys("problem_user");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        
        Allure.step("Verify login successful but with UI issues");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
        System.out.println("✅ Problem user login successful (this user has UI issues)");
        takeScreenshot(driver, "negative_test_problem_user");
    }

    @Test(description = "Performance glitch user test")
    @Story("Performance Issues")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test login with performance glitch user (slow loading)")
    public void testPerformanceGlitchUser() {
        Allure.step("Login with performance glitch user");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys("performance_glitch_user");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        
        Allure.step("Wait for slow loading and verify login");
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        longWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
        System.out.println("✅ Performance glitch user login successful (slow loading handled)");
        takeScreenshot(driver, "negative_test_performance_glitch");
    }

    @Test(description = "Empty username with valid password")
    @Story("Form Validation")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test login with empty username but valid password")
    public void testEmptyUsername() {
        Allure.step("Enter empty username with valid password");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        
        Allure.step("Verify error message");
        String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        if (errorMsg.contains("Username is required") || errorMsg.contains("Epic sadface")) {
            System.out.println("✅ Empty username test passed");
        } else {
            System.out.println("❌ Empty username test failed");
            throw new RuntimeException("Empty username test failed");
        }
        takeScreenshot(driver, "negative_test_empty_username");
    }

    @Test(description = "Valid username with empty password")
    @Story("Form Validation")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test login with valid username but empty password")
    public void testEmptyPassword() {
        Allure.step("Enter valid username with empty password");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("login-button")).click();
        
        Allure.step("Verify error message");
        String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        if (errorMsg.contains("Password is required") || errorMsg.contains("Epic sadface")) {
            System.out.println("✅ Empty password test passed");
        } else {
            System.out.println("❌ Empty password test failed");
            throw new RuntimeException("Empty password test failed");
        }
        takeScreenshot(driver, "negative_test_empty_password");
    }

    @Test(description = "Special characters in credentials")
    @Story("Input Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test login with special characters in username and password")
    public void testSpecialCharacters() {
        Allure.step("Enter credentials with special characters");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys("user@#$%");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("pass@#$%");
        driver.findElement(By.id("login-button")).click();
        
        Allure.step("Verify error message");
        String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        if (errorMsg.contains("Username and password do not match")) {
            System.out.println("✅ Special characters test passed");
        } else {
            System.out.println("❌ Special characters test failed");
            throw new RuntimeException("Special characters test failed");
        }
        takeScreenshot(driver, "negative_test_special_characters");
    }

    @Test(description = "Whitespace only credentials")
    @Story("Input Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test login with whitespace only in username and password")
    public void testWhitespaceOnly() {
        Allure.step("Enter whitespace only credentials");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys("   ");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("   ");
        driver.findElement(By.id("login-button")).click();
        
        Allure.step("Verify error message");
        String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        if (errorMsg.contains("Username and password do not match") || errorMsg.contains("Epic sadface")) {
            System.out.println("✅ Whitespace only test passed");
        } else {
            System.out.println("❌ Whitespace only test failed");
            throw new RuntimeException("Whitespace only test failed");
        }
        takeScreenshot(driver, "negative_test_whitespace_only");
    }
} 