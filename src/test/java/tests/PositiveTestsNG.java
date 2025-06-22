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
import java.util.List;

@Epic("SauceDemo E-commerce")
@Feature("Positive Test Scenarios")
public class PositiveTestsNG {
    
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

    @Test(description = "Basic flow with all products")
    @Story("Complete Purchase Flow")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test complete purchase flow: login, add all products, checkout, and complete order")
    public void testBasicFlow() {
        Allure.step("Login with standard user");
        login(driver);
        takeScreenshot(driver, "positive_test_login");
        delay(2);
        
        Allure.step("Add all products to cart");
        addAllProductsToCart(driver);
        takeScreenshot(driver, "positive_test_add_to_cart");
        delay(2);
        
        Allure.step("Go to cart");
        goToCart(driver);
        takeScreenshot(driver, "positive_test_cart");
        delay(2);
        
        Allure.step("Proceed to checkout");
        proceedToCheckout(driver);
        takeScreenshot(driver, "positive_test_checkout");
        delay(2);
        
        Allure.step("Fill checkout form");
        fillCheckoutForm(driver);
        takeScreenshot(driver, "positive_test_fill_form");
        delay(2);
        
        Allure.step("Finish order");
        finishOrder(driver);
        takeScreenshot(driver, "positive_test_finish_order");
        delay(2);
        
        Allure.step("Validate success");
        validateSuccess(driver);
        takeScreenshot(driver, "positive_test_complete");
    }

    @Test(description = "Test all user types")
    @Story("User Type Testing")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test login and basic functionality with different user types")
    public void testAllUserTypes() {
        Allure.step("Test standard user");
        testUserType("standard_user", "Standard User");
        
        Allure.step("Test problem user");
        testUserType("problem_user", "Problem User");
        
        Allure.step("Test performance glitch user");
        testUserType("performance_glitch_user", "Performance Glitch User");
    }

    @Test(description = "Single product order")
    @Story("Single Product Purchase")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test ordering a single product")
    public void testSingleProductOrder() {
        Allure.step("Login and add single product");
        login(driver);
        
        List<WebElement> addButtons = driver.findElements(By.cssSelector("button.btn_inventory"));
        if (!addButtons.isEmpty()) {
            addButtons.get(0).click();
            delay(1);
            System.out.println("✅ Single product added to cart");
        }
        
        Allure.step("Complete checkout process");
        goToCart(driver);
        proceedToCheckout(driver);
        fillCheckoutForm(driver);
        finishOrder(driver);
        validateSuccess(driver);
        
        takeScreenshot(driver, "positive_test_single_product_order");
    }

    @Test(description = "Different checkout data")
    @Story("Checkout Data Variation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test checkout with different customer data")
    public void testDifferentCheckoutData() {
        Allure.step("Login and add products");
        login(driver);
        addAllProductsToCart(driver);
        goToCart(driver);
        proceedToCheckout(driver);
        
        Allure.step("Fill with different data");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("first-name")));
        driver.findElement(By.id("first-name")).sendKeys("Budi");
        driver.findElement(By.id("last-name")).sendKeys("Santoso");
        driver.findElement(By.id("postal-code")).sendKeys("54321");
        
        wait.until(ExpectedConditions.elementToBeClickable(By.id("continue")));
        driver.findElement(By.id("continue")).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));
        driver.findElement(By.id("finish")).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")));
        validateSuccess(driver);
        
        takeScreenshot(driver, "positive_test_different_checkout_data");
    }

    @Test(description = "Logout after order")
    @Story("Post-Order Actions")
    @Severity(SeverityLevel.MINOR)
    @Description("Test logout functionality after completing an order")
    public void testLogoutAfterOrder() {
        Allure.step("Complete an order first");
        login(driver);
        addAllProductsToCart(driver);
        goToCart(driver);
        proceedToCheckout(driver);
        fillCheckoutForm(driver);
        finishOrder(driver);
        
        Allure.step("Test logout");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn")));
        driver.findElement(By.id("react-burger-menu-btn")).click();
        delay(1);
        
        wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
        driver.findElement(By.id("logout_sidebar_link")).click();
        delay(2);
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        System.out.println("✅ Logout after order successful");
        
        takeScreenshot(driver, "positive_test_logout_after_order");
    }

    // Helper methods
    private void login(WebDriver driver) {
        System.out.println("🔐 Login...");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
        System.out.println("✅ Login successful");
    }

    private void addAllProductsToCart(WebDriver driver) {
        System.out.println("🛒 Adding all products to cart...");
        List<WebElement> addButtons = driver.findElements(By.cssSelector("button.btn_inventory"));
        int count = addButtons.size();
        
        for (WebElement button : addButtons) {
            wait.until(ExpectedConditions.elementToBeClickable(button));
            button.click();
            delay(1);
        }
        System.out.println("✅ Added " + count + " products to cart");
    }

    private void goToCart(WebDriver driver) {
        System.out.println("🧺 Going to cart...");
        wait.until(ExpectedConditions.elementToBeClickable(By.className("shopping_cart_link")));
        driver.findElement(By.className("shopping_cart_link")).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout")));
        System.out.println("✅ Cart page loaded");
    }

    private void proceedToCheckout(WebDriver driver) {
        System.out.println("📦 Proceeding to checkout...");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout")));
        driver.findElement(By.id("checkout")).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
        System.out.println("✅ Checkout page loaded");
    }

    private void fillCheckoutForm(WebDriver driver) {
        System.out.println("📝 Filling checkout form...");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("first-name")));
        driver.findElement(By.id("first-name")).sendKeys("Ahmad");
        driver.findElement(By.id("last-name")).sendKeys("Farichin");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        
        wait.until(ExpectedConditions.elementToBeClickable(By.id("continue")));
        driver.findElement(By.id("continue")).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));
        System.out.println("✅ Checkout form filled");
    }

    private void finishOrder(WebDriver driver) {
        System.out.println("✅ Finishing order...");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("finish")));
        driver.findElement(By.id("finish")).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")));
        System.out.println("✅ Order completed");
    }

    private void validateSuccess(WebDriver driver) {
        System.out.println(" Validating success...");
        String successMsg = driver.findElement(By.className("complete-header")).getText();
        if (successMsg.contains("Thank you for your order")) {
            System.out.println("🎉 Full automation completed successfully!");
        } else {
            System.out.println("❌ Order completion validation failed");
            throw new RuntimeException("Order completion validation failed");
        }
    }

    private void testUserType(String username, String userType) {
        System.out.println("🔐 Testing " + userType + "...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        
        try {
            driver.get("https://www.saucedemo.com/");
            delay(2);
            
            wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
            driver.findElement(By.id("user-name")).sendKeys(username);
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
            System.out.println("✅ " + userType + " login successful");
            takeScreenshot(driver, "positive_test_" + username + "_login");
            
        } catch (Exception e) {
            System.out.println("⚠️ " + userType + " test failed: " + e.getMessage());
        }
    }
} 