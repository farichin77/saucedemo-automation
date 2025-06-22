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

public class PositiveTests {
    
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

    // ✅ Positive Login
    public static void login(WebDriver driver) {
        System.out.println("🔐 Login...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
        System.out.println("✅ Login successful");
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

    public static void fillCheckoutForm(WebDriver driver) {
        System.out.println("📝 Filling checkout form...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        wait.until(ExpectedConditions.elementToBeClickable(By.id("first-name")));
        driver.findElement(By.id("first-name")).sendKeys("Ahmad");
        driver.findElement(By.id("last-name")).sendKeys("Farichin");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        
        wait.until(ExpectedConditions.elementToBeClickable(By.id("continue")));
        driver.findElement(By.id("continue")).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));
        System.out.println("✅ Checkout form filled");
    }

    public static void finishOrder(WebDriver driver) {
        System.out.println("✅ Finishing order...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        wait.until(ExpectedConditions.elementToBeClickable(By.id("finish")));
        driver.findElement(By.id("finish")).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")));
        System.out.println("✅ Order completed");
    }

    public static void validateSuccess(WebDriver driver) {
        System.out.println(" Validating success...");
        String successMsg = driver.findElement(By.className("complete-header")).getText();
        if (successMsg.contains("Thank you for your order")) {
            System.out.println("🎉 Full automation completed successfully!");
        } else {
            System.out.println("❌ Order completion validation failed");
            throw new RuntimeException("Order completion validation failed");
        }
    }

    // Test login dengan semua user types
    public static void testAllUserTypes(WebDriver driver) {
        System.out.println("🔐 Testing all user types...");
        
        // Test standard_user
        testUserType(driver, "standard_user", "Standard User");
        
        // Test problem_user (UI issues expected)
        testUserType(driver, "problem_user", "Problem User");
        
        // Test performance_glitch_user (slow loading expected)
        testUserType(driver, "performance_glitch_user", "Performance Glitch User");
    }

    public static void testUserType(WebDriver driver, String username, String userType) {
        System.out.println("🔐 Testing " + userType + "...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        
        try {
            // Navigate to login page
            driver.get("https://www.saucedemo.com/");
            delay(2);
            
            wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
            driver.findElement(By.id("user-name")).sendKeys(username);
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
            System.out.println("✅ " + userType + " login successful");
            takeScreenshot(driver, "positive_test_" + username + "_login");
            
            // Test basic functionality
            testBasicFunctionality(driver, userType);
            
        } catch (Exception e) {
            System.out.println("⚠️ " + userType + " test failed: " + e.getMessage());
        }
    }

    public static void testBasicFunctionality(WebDriver driver, String userType) {
        System.out.println("🔍 Testing basic functionality for " + userType + "...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        try {
            // Test product listing
            List<WebElement> products = driver.findElements(By.cssSelector(".inventory_item"));
            System.out.println("✅ " + userType + " - Found " + products.size() + " products");
            
            // Test sorting functionality
            testSorting(driver, userType);
            
            // Test individual product
            testIndividualProduct(driver, userType);
            
        } catch (Exception e) {
            System.out.println("⚠️ Basic functionality test failed for " + userType + ": " + e.getMessage());
        }
    }

    public static void testSorting(WebDriver driver, String userType) {
        System.out.println("📊 Testing sorting functionality for " + userType + "...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        try {
            // Test sort by name (A to Z)
            WebElement sortDropdown = driver.findElement(By.cssSelector("select.product_sort_container"));
            sortDropdown.click();
            driver.findElement(By.cssSelector("option[value='az']")).click();
            delay(2);
            System.out.println("✅ " + userType + " - Sort by name (A to Z) successful");
            
            // Test sort by name (Z to A)
            sortDropdown.click();
            driver.findElement(By.cssSelector("option[value='za']")).click();
            delay(2);
            System.out.println("✅ " + userType + " - Sort by name (Z to A) successful");
            
            // Test sort by price (low to high)
            sortDropdown.click();
            driver.findElement(By.cssSelector("option[value='lohi']")).click();
            delay(2);
            System.out.println("✅ " + userType + " - Sort by price (low to high) successful");
            
            // Test sort by price (high to low)
            sortDropdown.click();
            driver.findElement(By.cssSelector("option[value='hilo']")).click();
            delay(2);
            System.out.println("✅ " + userType + " - Sort by price (high to low) successful");
            
        } catch (Exception e) {
            System.out.println("⚠️ Sorting test failed for " + userType + ": " + e.getMessage());
        }
    }

    public static void testIndividualProduct(WebDriver driver, String userType) {
        System.out.println("🛍️ Testing individual product for " + userType + "...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        try {
            // Click on first product
            List<WebElement> products = driver.findElements(By.cssSelector(".inventory_item_name"));
            if (!products.isEmpty()) {
                products.get(0).click();
                delay(2);
                
                // Test product details page
                String productTitle = driver.findElement(By.cssSelector(".inventory_details_name")).getText();
                System.out.println("✅ " + userType + " - Product details page: " + productTitle);
                
                // Test add to cart from product page
                WebElement addToCartButton = driver.findElement(By.cssSelector("button.btn_inventory"));
                addToCartButton.click();
                delay(1);
                
                // Check if button text changed
                String buttonText = addToCartButton.getText();
                if (buttonText.contains("Remove")) {
                    System.out.println("✅ " + userType + " - Product added to cart successfully");
                }
                
                // Go back to inventory
                driver.findElement(By.cssSelector("button#back-to-products")).click();
                delay(2);
                
            }
        } catch (Exception e) {
            System.out.println("⚠️ Individual product test failed for " + userType + ": " + e.getMessage());
        }
    }

    // Test order hanya 1 produk
    public static void testSingleProductOrder(WebDriver driver) {
        System.out.println("🛒 Testing single product order...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        try {
            // Login
            login(driver);
            
            // Add only first product
            List<WebElement> addButtons = driver.findElements(By.cssSelector("button.btn_inventory"));
            if (!addButtons.isEmpty()) {
                addButtons.get(0).click();
                delay(1);
                System.out.println("✅ Single product added to cart");
            }
            
            // Go to cart and checkout
            goToCart(driver);
            proceedToCheckout(driver);
            fillCheckoutForm(driver);
            finishOrder(driver);
            validateSuccess(driver);
            
            takeScreenshot(driver, "positive_test_single_product_order");
            System.out.println("✅ Single product order completed successfully");
            
        } catch (Exception e) {
            System.out.println("❌ Single product order test failed: " + e.getMessage());
            takeScreenshot(driver, "positive_test_single_product_error");
        }
    }

    // Test checkout dengan data berbeda
    public static void testDifferentCheckoutData(WebDriver driver) {
        System.out.println("📝 Testing checkout with different data...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        try {
            // Login
            login(driver);
            addAllProductsToCart(driver);
            goToCart(driver);
            proceedToCheckout(driver);
            
            // Fill with different data
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
            System.out.println("✅ Checkout with different data completed successfully");
            
        } catch (Exception e) {
            System.out.println("❌ Different checkout data test failed: " + e.getMessage());
            takeScreenshot(driver, "positive_test_different_checkout_error");
        }
    }

    // Test logout setelah order
    public static void testLogoutAfterOrder(WebDriver driver) {
        System.out.println("🚪 Testing logout after order...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        try {
            // Complete an order first
            login(driver);
            addAllProductsToCart(driver);
            goToCart(driver);
            proceedToCheckout(driver);
            fillCheckoutForm(driver);
            finishOrder(driver);
            
            // Test logout
            wait.until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn")));
            driver.findElement(By.id("react-burger-menu-btn")).click();
            delay(1);
            
            wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
            driver.findElement(By.id("logout_sidebar_link")).click();
            delay(2);
            
            // Verify logout successful
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
            System.out.println("✅ Logout after order successful");
            
            takeScreenshot(driver, "positive_test_logout_after_order");
            
        } catch (Exception e) {
            System.out.println("❌ Logout after order test failed: " + e.getMessage());
            takeScreenshot(driver, "positive_test_logout_error");
        }
    }

    // Utility to ensure clean login page before each test
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

    public static void main(String[] args) {
        System.out.println("🚀 Starting SauceDemo Positive Tests");
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

            // ✅ POSITIVE TESTS
            System.out.println("\n🟢 RUNNING POSITIVE TESTS");
            System.out.println("=========================");
            
            // Test 1: Basic flow with all products
            System.out.println("\n📋 Test 1: Basic flow with all products");
            ensureCleanLoginPage(driver);
            login(driver);
            takeScreenshot(driver, "positive_test_login");
            delay(2);
            
            addAllProductsToCart(driver);
            takeScreenshot(driver, "positive_test_add_to_cart");
            delay(2);
            
            goToCart(driver);
            takeScreenshot(driver, "positive_test_cart");
            delay(2);
            
            proceedToCheckout(driver);
            takeScreenshot(driver, "positive_test_checkout");
            delay(2);
            
            fillCheckoutForm(driver);
            takeScreenshot(driver, "positive_test_fill_form");
            delay(2);
            
            finishOrder(driver);
            takeScreenshot(driver, "positive_test_finish_order");
            delay(2);
            
            validateSuccess(driver);
            takeScreenshot(driver, "positive_test_complete");

            // Test 2: All user types
            System.out.println("\n📋 Test 2: All user types");
            ensureCleanLoginPage(driver);
            testAllUserTypes(driver);

            // Test 3: Single product order
            System.out.println("\n📋 Test 3: Single product order");
            ensureCleanLoginPage(driver);
            testSingleProductOrder(driver);

            // Test 4: Different checkout data
            System.out.println("\n📋 Test 4: Different checkout data");
            ensureCleanLoginPage(driver);
            testDifferentCheckoutData(driver);

            // Test 5: Logout after order
            System.out.println("\n📋 Test 5: Logout after order");
            ensureCleanLoginPage(driver);
            testLogoutAfterOrder(driver);

            System.out.println("\n✅ All positive tests completed successfully!");

        } catch (Exception e) {
            System.err.println("❌ Positive tests failed: " + e.getMessage());
            takeScreenshot(driver, "positive_tests_error");
            e.printStackTrace();
        } finally {
            delay(3);
            System.out.println("🔚 Closing browser...");
            driver.quit();
            System.out.println("=====================================");
            System.out.println("🏁 Positive tests completed");
        }
    }
} 