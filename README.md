# SauceDemo Automation Testing Project

##  Project Overview
A comprehensive web automation testing project for the SauceDemo e-commerce website using Java, Selenium WebDriver, TestNG, and Allure reporting. This project demonstrates industry best practices in test automation including Page Object Model, configuration management, robust error handling, and professional test reporting.

## ✨ Key Features
- **Page Object Model (POM)** - Maintainable and reusable test structure
- **Configuration Management** - Externalized configuration for different environments
- **Robust Error Handling** - Comprehensive exception handling with screenshots
- **Explicit Waits** - Reliable element interactions
- **Logging Framework** - Detailed test execution logs
- **Screenshot Capture** - Automatic screenshot capture on failures
- **Maven Integration** - Professional build and dependency management
- **TestNG Framework** - Professional test execution and organization
- **Allure Reporting** - Beautiful and detailed test reports
- **Parallel Execution** - Faster test execution with parallel test suites

## ️ Architecture

## 🎯 Overview
Project automation testing untuk website SauceDemo menggunakan Java, Selenium WebDriver, TestNG, dan Allure reporting.

##  Cara Menjalankan

### Method 1: TestNG + Allure (Recommended)
```bash
# Windows
run-testng-allure.bat

# Linux/Mac
chmod +x run-testng-allure.sh
./run-testng-allure.sh
```

### Method 2: Manual TestNG Execution
```bash
# Run all tests and generate report
mvn clean test allure:report

# Serve Allure report locally
mvn allure:serve
```

### Method 3: Legacy Method (Original Tests)
```bash
# Windows
run-automation.bat

# Linux/Mac
./run-automation.sh
```

### Method 4: Using Maven
```bash
mvn exec:java -Dexec.mainClass="tests.FullAutomationTest"
```

### Method 5: Using Java Directly
```bash
mvn compile
java -cp target/classes;target/dependency/* tests.FullAutomationTest
```

## 🚀 Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher
- Chrome browser

### Installation
1. Clone the repository
2. Navigate to project directory
3. Run: `mvn clean compile`

### Running Tests

#### TestNG + Allure (Recommended)
```bash
# Run all tests with Allure reporting
mvn clean test allure:report

# View report in browser
mvn allure:serve
```

#### Legacy Tests
```bash
# Run full automation
mvn exec:java

# Or with specific main class
mvn exec:java -Dexec.mainClass="tests.FullAutomationTest"
```

## 📋 Test Scenarios

### TestNG Test Suites
1. **Positive Tests** (`PositiveTestsNG`)
   - Basic flow with all products
   - All user types testing
   - Single product order
   - Different checkout data
   - Logout after order

2. **Negative Tests** (`NegativeTestsNG`)
   - Invalid username/password
   - Locked out user
   - Empty fields validation
   - Problem user scenarios
   - Performance glitch user
   - Special characters input
   - Whitespace validation

### Legacy Test Scenarios
1. **Positive Flow**
   - User login with valid credentials
   - Add all products to shopping cart
   - Complete checkout process
   - Validate order completion

2. **Negative Flow**
   - Login with invalid credentials
   - Verify error message display

## ️ Technical Stack
- **Java 11** - Programming language
- **Selenium WebDriver 4.19.1** - Web automation framework
- **TestNG 7.8.0** - Test execution framework
- **Allure 2.24.0** - Test reporting framework
- **WebDriverManager 5.7.0** - Driver management
- **Maven 3.11.0** - Build tool and dependency management
- **SLF4J + Logback** - Logging framework

## 📊 Test Results
- **Test Coverage**: Full e-commerce workflow
- **Execution Time**: ~2-3 minutes (TestNG), ~1-2 minutes (parallel)
- **Success Rate**: 95%+ (with proper network conditions)
- **Screenshots**: Automatic capture on failures
- **Reports**: Professional Allure HTML reports

## 🔧 Best Practices Implemented
- ✅ Page Object Model design pattern
- ✅ Configuration externalization
- ✅ Explicit waits for element interactions
- ✅ Comprehensive error handling
- ✅ Screenshot capture on failures
- ✅ Professional logging
- ✅ Clean code structure
- ✅ Maven project structure
- ✅ TestNG annotations and organization
- ✅ Allure reporting integration
- ✅ Parallel test execution
- ✅ Test severity classification

## 📸 Screenshots & Reports
- **Screenshots**: Automated screenshots captured in `screenshots/` directory
- **Allure Reports**: Professional HTML reports in `target/allure-report/`
- **Test Results**: Detailed test execution results with trends and analytics

## 📈 Allure Report Features
- **Dashboard**: Overview of test execution
- **Test Cases**: Detailed test case information
- **Categories**: Test failure categorization
- **Timeline**: Test execution timeline
- **Trends**: Historical test execution trends
- **Attachments**: Screenshots and logs
- **Severity**: Test priority classification

##  Learning Outcomes
This project demonstrates:
- Web automation with Selenium
- Page Object Model implementation
- Configuration management
- Error handling strategies
- Professional project structure
- Maven build automation
- TestNG test organization
- Allure reporting integration
- Parallel test execution

## 📝 Future Enhancements
- Data-driven testing with Excel/CSV
- Cross-browser testing
- Mobile testing
- API testing integration
- CI/CD pipeline integration
- Performance testing
- Visual regression testing