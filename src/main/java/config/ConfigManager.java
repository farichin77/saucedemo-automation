package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static Properties properties;
    
    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/config.properties"));
        } catch (IOException e) {
            // Set default values
            properties.setProperty("base.url", "https://www.saucedemo.com/");
            properties.setProperty("username", "standard_user");
            properties.setProperty("password", "secret_sauce");
            properties.setProperty("browser", "chrome");
            properties.setProperty("implicit.wait", "10");
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public static String getUrl() {
        return getProperty("base.url");
    }
    
    public static String getUsername() {
        return getProperty("username");
    }
    
    public static String getPassword() {
        return getProperty("password");
    }
} 