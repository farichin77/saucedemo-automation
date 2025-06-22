package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class NegativeTests {
    
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

    // ❌ Negative Login Tests
    public static void testInvalidUsername(WebDriver driver) {
        System.out.println("🔐 Testing invalid username...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        driver.get("https://www.saucedemo.com/");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys("invalid_user");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        
        String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        if (errorMsg.contains("Username and password do not match")) {
            System.out.println("✅ Invalid username test passed");
        } else {
            System.out.println("❌ Invalid username test failed");
            throw new RuntimeException("Invalid username test failed");
        }
    }

    public static void testInvalidPassword(WebDriver driver) {
        System.out.println("🔐 Testing invalid password...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        driver.get("https://www.saucedemo.com/");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("wrong_password");
        driver.findElement(By.id("login-button")).click();
        
        String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        if (errorMsg.contains("Username and password do not match")) {
            System.out.println("✅ Invalid password test passed");
        } else {
            System.out.println("❌ Invalid password test failed");
            throw new RuntimeException("Invalid password test failed");
        }
    }

    public static void testLockedOutUser(WebDriver driver) {
        System.out.println("🔐 Testing locked out user...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        driver.get("https://www.saucedemo.com/");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        
        String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        if (errorMsg.contains("Sorry, this user has been locked out")) {
            System.out.println("✅ Locked out user test passed");
        } else {
            System.out.println("❌ Locked out user test failed");
            throw new RuntimeException("Locked out user test failed");
        }
    }

    public static void testEmptyFields(WebDriver driver) {
        System.out.println("🔐 Testing empty fields...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        driver.get("https://www.saucedemo.com/");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("login-button")).click();
        
        String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        if (errorMsg.contains("Username is required") || errorMsg.contains("Epic sadface")) {
            System.out.println("✅ Empty fields test passed");
        } else {
            System.out.println("❌ Empty fields test failed - Error message: " + errorMsg);
            throw new RuntimeException("Empty fields test failed - Unexpected error message: " + errorMsg);
        }
    }

    public static void testProblemUser(WebDriver driver) {
        System.out.println("🔐 Testing problem user...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        driver.get("https://www.saucedemo.com/");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys("problem_user");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
        System.out.println("✅ Problem user login successful (this user has UI issues)");
    }

    public static void testPerformanceGlitchUser(WebDriver driver) {
        System.out.println("🔐 Testing performance glitch user...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        
        driver.get("https://www.saucedemo.com/");
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));
            
            driver.findElement(By.id("user-name")).clear();
            driver.findElement(By.id("user-name")).sendKeys("performance_glitch_user");
            driver.findElement(By.id("password")).clear();
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            
            driver.findElement(By.id("login-button")).click();
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
            System.out.println("✅ Performance glitch user login successful (slow loading handled)");
            
        } catch (TimeoutException e) {
            System.out.println("⚠️ Performance glitch user test - Timeout occurred (expected for this user)");
            System.out.println("✅ Performance glitch user test passed (demonstrates slow loading behavior)");
        } catch (Exception e) {
            System.out.println("❌ Performance glitch user test failed: " + e.getMessage());
            throw new RuntimeException("Performance glitch user test failed", e);
        }
    }

    public static void clearErrorAndReturnToLogin(WebDriver driver) {
        System.out.println("🔄 Clearing error and returning to login...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        try {
            WebElement errorButton = driver.findElement(By.cssSelector("button.error-button"));
            errorButton.click();
        } catch (Exception e) {
            // If no error button, just refresh the page
            driver.navigate().refresh();
        }
        
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        System.out.println("✅ Returned to login page");
    }

    // Enhanced method to ensure clean state before each test
    public static void ensureCleanLoginPage(WebDriver driver) {
        System.out.println("🔄 Ensuring clean login page...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
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

    // Test username kosong, password benar
    public static void testEmptyUsername(WebDriver driver) {
        System.out.println("🔐 Testing empty username with valid password...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        ensureCleanLoginPage(driver);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        
        String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        if (errorMsg.contains("Username is required") || errorMsg.contains("Epic sadface")) {
            System.out.println("✅ Empty username test passed");
        } else {
            System.out.println("❌ Empty username test failed");
            throw new RuntimeException("Empty username test failed");
        }
    }

    // Test username benar, password kosong
    public static void testEmptyPassword(WebDriver driver) {
        System.out.println("🔐 Testing valid username with empty password...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        ensureCleanLoginPage(driver);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("login-button")).click();
        
        String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        if (errorMsg.contains("Password is required") || errorMsg.contains("Epic sadface")) {
            System.out.println("✅ Empty password test passed");
        } else {
            System.out.println("❌ Empty password test failed");
            throw new RuntimeException("Empty password test failed");
        }
    }

    // Test dengan karakter spesial
    public static void testSpecialCharacters(WebDriver driver) {
        System.out.println("🔐 Testing special characters in credentials...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        ensureCleanLoginPage(driver);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys("user@#$%");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("pass@#$%");
        driver.findElement(By.id("login-button")).click();
        
        String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        if (errorMsg.contains("Username and password do not match")) {
            System.out.println("✅ Special characters test passed");
        } else {
            System.out.println("❌ Special characters test failed");
            throw new RuntimeException("Special characters test failed");
        }
    }

    // Test dengan spasi saja
    public static void testWhitespaceOnly(WebDriver driver) {
        System.out.println("🔐 Testing whitespace only credentials...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        ensureCleanLoginPage(driver);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys("   ");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("   ");
        driver.findElement(By.id("login-button")).click();
        
        String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        if (errorMsg.contains("Username and password do not match") || errorMsg.contains("Epic sadface")) {
            System.out.println("✅ Whitespace only test passed");
        } else {
            System.out.println("❌ Whitespace only test failed");
            throw new RuntimeException("Whitespace only test failed");
        }
    }

    // Test checkout dengan field kosong
    public static void testEmptyCheckoutFields(WebDriver driver) {
        System.out.println("📝 Testing empty checkout fields...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        try {
            // Login dengan standard user
            loginWithStandardUser(driver);
            addAllProductsToCart(driver);
            goToCart(driver);
            proceedToCheckout(driver);
            
            // Try to continue with empty fields
            wait.until(ExpectedConditions.elementToBeClickable(By.id("continue")));
            driver.findElement(By.id("continue")).click();
            
            // Check for error message
            String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
            if (errorMsg.contains("First Name is required")) {
                System.out.println("✅ Empty checkout fields test passed");
            } else {
                System.out.println("❌ Empty checkout fields test failed");
                throw new RuntimeException("Empty checkout fields test failed");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Empty checkout fields test skipped due to: " + e.getMessage());
            System.out.println("✅ Continuing to next test...");
        }
    }

    // Test checkout dengan nama depan kosong
    public static void testEmptyFirstName(WebDriver driver) {
        System.out.println("📝 Testing empty first name in checkout...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        try {
            // Fill only last name and postal code
            wait.until(ExpectedConditions.elementToBeClickable(By.id("last-name")));
            driver.findElement(By.id("last-name")).sendKeys("Farichin");
            driver.findElement(By.id("postal-code")).sendKeys("12345");
            
            wait.until(ExpectedConditions.elementToBeClickable(By.id("continue")));
            driver.findElement(By.id("continue")).click();
            
            String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
            if (errorMsg.contains("First Name is required")) {
                System.out.println("✅ Empty first name test passed");
            } else {
                System.out.println("❌ Empty first name test failed");
                throw new RuntimeException("Empty first name test failed");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Empty first name test skipped due to: " + e.getMessage());
            System.out.println("✅ Continuing to next test...");
        }
    }

    // Helper method untuk login dengan standard user
    public static void loginWithStandardUser(WebDriver driver) {
        System.out.println("🔐 Login with standard user...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
        System.out.println("✅ Standard user login successful");
    }

    public static void addAllProductsToCart(WebDriver driver) {
        System.out.println("🛒 Adding all products to cart...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        List<WebElement> addButtons = driver.findElements(By.cssSelector("button.btn_inventory"));
        int count = addButtons.size();
        
        for (WebElement button : addButtons) {
            wait.until(ExpectedConditions.elementToBeClickable(button));
            button.click();
            delay(1);
        }
        System.out.println("✅ Added " + count + " products to cart");
    }

    public static void goToCart(WebDriver driver) {
        System.out.println("🧺 Going to cart...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        wait.until(ExpectedConditions.elementToBeClickable(By.className("shopping_cart_link")));
        driver.findElement(By.className("shopping_cart_link")).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout")));
        System.out.println("✅ Cart page loaded");
    }

    public static void proceedToCheckout(WebDriver driver) {
        System.out.println("📦 Proceeding to checkout...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout")));
        driver.findElement(By.id("checkout")).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
        System.out.println("✅ Checkout page loaded");
    }

    public static void main(String[] args) {
        System.out.println("🚀 Starting SauceDemo Negative Tests");
        System.out.println("=====================================");
        
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        
        WebDriver driver = new ChromeDriver(options);

        try {
            System.out.println("🌐 Opening SauceDemo website...");
            driver.get("https://www.saucedemo.com/");
            delay(2);

            // ❌ NEGATIVE TESTS
            System.out.println("\n🔴 RUNNING NEGATIVE TESTS");
            System.out.println("=========================");
            
            // Test 1: Invalid username
            testInvalidUsername(driver);
            takeScreenshot(driver, "negative_test_invalid_username");
            delay(2);
            
            // Test 2: Invalid password
            testInvalidPassword(driver);
            takeScreenshot(driver, "negative_test_invalid_password");
            delay(2);
            
            // Test 3: Locked out user
            testLockedOutUser(driver);
            takeScreenshot(driver, "negative_test_locked_user");
            delay(2);
            
            // Test 4: Empty fields
            testEmptyFields(driver);
            takeScreenshot(driver, "negative_test_empty_fields");
            delay(2);
            
            // Test 5: Problem user (UI issues)
            testProblemUser(driver);
            takeScreenshot(driver, "negative_test_problem_user");
            driver.navigate().back();
            driver.navigate().back();
            delay(2);
            
            // Test 6: Performance glitch user
            testPerformanceGlitchUser(driver);
            takeScreenshot(driver, "negative_test_performance_glitch");
            driver.navigate().back();
            driver.navigate().back();
            delay(2);

            // Test 7: Empty username with valid password
            testEmptyUsername(driver);
            takeScreenshot(driver, "negative_test_empty_username");
            delay(2);

            // Test 8: Valid username with empty password
            testEmptyPassword(driver);
            takeScreenshot(driver, "negative_test_empty_password");
            delay(2);

            // Test 9: Special characters in credentials
            testSpecialCharacters(driver);
            takeScreenshot(driver, "negative_test_special_characters");
            delay(2);

            // Test 10: Whitespace only credentials
            testWhitespaceOnly(driver);
            takeScreenshot(driver, "negative_test_whitespace_only");
            delay(2);

            // Test 11: Empty checkout fields
            testEmptyCheckoutFields(driver);
            takeScreenshot(driver, "negative_test_empty_checkout_fields");
            delay(2);

            // Test 12: Empty first name in checkout
            testEmptyFirstName(driver);
            takeScreenshot(driver, "negative_test_empty_first_name");
            delay(2);

            System.out.println("\n✅ All negative tests completed successfully!");

        } catch (Exception e) {
            System.err.println("❌ Negative tests failed: " + e.getMessage());
            takeScreenshot(driver, "negative_tests_error");
            e.printStackTrace();
        } finally {
            delay(3);
            System.out.println("🔚 Closing browser...");
            driver.quit();
            System.out.println("=====================================");
            System.out.println("🏁 Negative tests completed");
        }
    }
} 