@echo off
echo ========================================
echo    SauceDemo TestNG + Allure Runner
echo ========================================
echo.

echo [1/4] Cleaning previous builds...
call mvn clean
if %errorlevel% neq 0 (
    echo ERROR: Clean failed!
    pause
    exit /b 1
)

echo.
echo [2/4] Compiling project...
call mvn compile test-compile
if %errorlevel% neq 0 (
    echo ERROR: Compilation failed!
    pause
    exit /b 1
)

echo.
echo [3/4] Running TestNG tests...
call mvn test
if %errorlevel% neq 0 (
    echo WARNING: Some tests failed, but continuing to generate report...
)

echo.
echo [4/4] Generating Allure report...
call mvn allure:report
if %errorlevel% neq 0 (
    echo ERROR: Report generation failed!
    pause
    exit /b 1
)

echo.
echo ========================================
echo    SUCCESS! Allure Report Generated
echo ========================================
echo.
echo Report location: target/allure-report/index.html
echo.
echo To view the report:
echo 1. Open target/allure-report/index.html in your browser
echo 2. Or run: mvn allure:serve
echo.
pause 