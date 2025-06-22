#!/bin/bash

echo "========================================"
echo "   SauceDemo TestNG + Allure Runner"
echo "========================================"
echo

echo "[1/4] Cleaning previous builds..."
mvn clean
if [ $? -ne 0 ]; then
    echo "ERROR: Clean failed!"
    exit 1
fi

echo
echo "[2/4] Compiling project..."
mvn compile test-compile
if [ $? -ne 0 ]; then
    echo "ERROR: Compilation failed!"
    exit 1
fi

echo
echo "[3/4] Running TestNG tests..."
mvn test
if [ $? -ne 0 ]; then
    echo "WARNING: Some tests failed, but continuing to generate report..."
fi

echo
echo "[4/4] Generating Allure report..."
mvn allure:report
if [ $? -ne 0 ]; then
    echo "ERROR: Report generation failed!"
    exit 1
fi

echo
echo "========================================"
echo "   SUCCESS! Allure Report Generated"
echo "========================================"
echo
echo "Report location: target/allure-report/index.html"
echo
echo "To view the report:"
echo "1. Open target/allure-report/index.html in your browser"
echo "2. Or run: mvn allure:serve" 