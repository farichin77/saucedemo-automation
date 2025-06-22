package tests;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.By;

public class InventoryPage extends BasePage {
    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "title")
    WebElement pageTitle;

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    WebElement addToCartButton;

    public String getTitle() {
        return pageTitle.getText();
    }

    public void addToCart() {
        // Wait for button to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        addToCartButton.click();
        
        // Wait for button to change to "Remove"
        wait.until(ExpectedConditions.textToBe(By.id("add-to-cart-sauce-labs-backpack"), "Remove"));
    }
    
    public boolean isItemInCart() {
        try {
            WebElement removeButton = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
            return removeButton.getText().equals("Remove");
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getCartBadgeCount() {
        try {
            WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
            return cartBadge.getText();
        } catch (Exception e) {
            return "0"; // No badge means 0 items
        }
    }
}
