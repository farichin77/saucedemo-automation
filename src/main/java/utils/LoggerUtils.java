package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtils {
    
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
    
    public static void logInfo(String message) {
        System.out.println("ℹ️ " + message);
    }
    
    public static void logSuccess(String message) {
        System.out.println("✅ " + message);
    }
    
    public static void logError(String message) {
        System.err.println("❌ " + message);
    }
    
    public static void logWarning(String message) {
        System.out.println("⚠️ " + message);
    }
} 