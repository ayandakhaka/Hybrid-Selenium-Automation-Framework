# 🚀 UI & API Test Automation Framework

A hybrid test automation framework built with **Java, Selenium WebDriver, REST Assured, TestNG, Maven, and Allure Report**.

This project is being developed as a practical demonstration of modern QA automation practices, covering both **UI and API test automation**, detailed reporting, failure analysis, and CI/CD integration.

The framework is continuously evolving, with additional automation coverage and infrastructure improvements being added as development progresses.

---

## 📌 Project Overview

The goal of this project is to build a maintainable and scalable test automation framework that supports:

- UI test automation
- API test automation
- Reusable test components
- Page Object Model (POM)
- API service layer
- Dynamic test data
- Detailed test reporting
- Failure classification
- API request and response visibility
- CI/CD test execution
- Cloud-based test execution

---

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java | Programming language |
| Selenium WebDriver | UI test automation |
| REST Assured | API test automation |
| TestNG | Test execution and assertions |
| Maven | Build and dependency management |
| Page Object Model | UI automation design pattern |
| Allure Report | Test reporting and execution analysis |
| SLF4J / Log4j | Framework logging |
| Git | Version control |
| GitHub | Source code repository |
| GitHub Actions | CI/CD automation |
| Postman | API exploration and validation |
| JSON | API data handling |
| Microsoft Edge / Google Chrome | UI test execution |

---

# 🏗️ Framework Architecture

The framework follows a modular structure designed to separate test logic, page interactions, API services, test data, and reusable utilities.

```text
src
├── main
│   └── java
│       ├── api
|       |   ├── helpers
|       |   |   └── UserDatahelper
│       │   ├── model
|       |   |   └── UserModel
|       |   ├── payload
|       |   |   └── UserPayload
│       │   └── services
│       │       └── UserApiService
|       |   ├── testdata
|       |   |   └── TestDataManager
│       ├── pages
|       |   ├── HomePage
|       |   ├── LoginPage
|       |   ├── LogoutPage
|       |   ├── ProductsPage
|       |   └── ViewCartPage
│       │
│       └── utility
│           ├── ActionHelper
│           ├── AllureApiAttachmednt
│           ├── AllureApiHelper
│           ├── AllureAttachment
│           ├── BasePage
│           ├── ConfigReader
│           ├── DriverFactory
|           ├── FakeDataGenerator
|           └── FrameworkLogger
├── test
│   └── java
│       ├── api
|       |   ├──setup
|       |   |  └── ApiTestSetup
|       |   |  ├── TestDataSetup
|       |   ├──tests
|       |   |  └── AutomationExerciseAPITests
│       ├── ui
|           ├── CartTest
|           ├── LoginTest
|           └── SearchProductTest
|       ├── utility
|           └──BaseTest
│
└── resources
    ├── environmentvariables
    |   ├── config.properties
    └── testdata
        ├── userData.json
    ├── categories.json
    ├── log4j2.xml
```
## 🖥️ UI Automation

The UI automation layer is built using **Selenium WebDriver** and follows the **Page Object Model (POM)** design pattern.

The framework is designed to provide reusable page components, maintainable test cases, and reliable UI test execution.

### UI Automation Coverage

| Test Area | Status |
|---|:---:|
| User Login | ✅ Automated |
| Invalid Login Scenarios | ✅ Automated |
| Product Interactions | ✅ Automated |
| Shopping Cart | ✅ Automated |
| Add Product to Cart | ✅ Automated |
| Remove Product from Cart | ✅ Automated |
| Cart Validation | ✅ Automated |
| UI Test Cleanup | ✅ Automated |
| Checkout | 🔄 In Progress |
| Order Management | ⏳ Planned |
| Additional Negative Scenarios | ⏳ Planned |

---

## 🔌 API Automation

API automation is implemented using **REST Assured** and **TestNG**.

The API automation layer uses reusable **service classes**, **API models**, and **test data utilities** to improve test maintainability and reduce duplication.

### API Test Coverage

#### 👤 User & Account APIs

| API Scenario | Status |
|---|:---:|
| Register User | ✅ Automated |
| Login with Valid Credentials | ✅ Automated |
| Login with Invalid Credentials | ✅ Automated |
| Login Without Required Parameters | ✅ Automated |
| Retrieve User Details by Email | ✅ Automated |
| Update User Account | ✅ Automated |
| Delete User Account | ✅ Automated |
| Delete Login | ✅ Automated |

#### 🛍️ Product APIs

| API Scenario | Status |
|---|:---:|
| Search Products | ✅ Automated |
| Validate Product API Responses | ✅ Automated |
| Additional Negative API Scenarios | 🔄 In Progress |

---

# 📊 Allure Reporting

The framework uses **Allure Report** to provide detailed and interactive test execution reports.

The reporting implementation is designed to provide clear visibility into test execution, failures, environment details, and API communication.

### Allure Reporting Features

| Feature | Status |
|---|:---:|
| Test Execution Results | ✅ Implemented |
| Meaningful Test Descriptions | ✅ Implemented |
| Test Steps using `@Step` | ✅ Implemented |
| Test Severity Classification | ✅ Implemented |
| Dynamic Environment Information | ✅ Implemented |
| Browser Name & Version | ✅ Implemented |
| Operating System Information | ✅ Implemented |
| Java Version | ✅ Implemented |
| Automatic Screenshots on UI Failures | ✅ Implemented |
| Framework Logs | ✅ Implemented |
| API Request Attachments | ✅ Implemented |
| API Response Attachments | ✅ Implemented |
| Failure Categories | ✅ Implemented |
| Test Execution Trends | 🔄 In Progress |
| Historical Test Results | 🔄 In Progress |

---

## 🏷️ Failure Classification

Allure failure categories are used to classify test failures and make failure analysis easier.

### Current Failure Categories

| Category | Purpose |
|---|---|
| 🔌 API Failures | Identifies failures related to API requests, responses, or HTTP status codes |
| ⚠️ Assertion Failures | Identifies failures where expected and actual results do not match |
| 🖥️ UI Element Failures | Identifies Selenium element interaction and locator failures |
| ⏱️ Timeout Failures | Identifies failures caused by elements or conditions not becoming available within the expected time |
| 🌐 WebDriver Failures | Identifies browser, driver, and WebDriver session-related failures |

### Failure Classification Flow

```text
Test Failure
     │
     ▼
Allure Report
     │
     ├── 🔌 API Failure
     │
     ├── ⚠️ Assertion Failure
     │
     ├── 🖥️ UI Element Failure
     │
     ├── ⏱️ Timeout Failure
     │
     └── 🌐 WebDriver Failure
```
Failure categorization helps distinguish between application defects, automation issues, and environment-related failures, making test results easier to analyze and troubleshoot.

# 📈 Allure Trends & Test History

The framework is being enhanced to track test execution history and identify trends across multiple test runs.

### Planned Trend Metrics

| Metric | Status |
|---|:---:|
| Passed Tests | ✅ |
| Failed Tests | ✅ |
| Broken Tests | ✅ |
| Skipped Tests | ✅ |
| Test Execution Duration | ✅ |
| Test Stability Over Time | 🔄 In Progress |

The goal is to provide historical visibility into the health, stability, and overall quality of the automated test suite.

**Current Status:** 🔄 In Progress

---


```
📈 Allure Reports

After executing tests, generate the report using:


bash
allure serve target/allure-results
Alternatively:


bash
allure generate target/allure-results --clean
The generated report can be opened from:


text
allure-report/index.html
The report provides visibility into:

Passed tests.
Failed tests.
Broken tests.
Skipped tests.
Execution duration.
Test steps.
Screenshots.
Logs.
API request and response evidence.
Failure categories.
---
# 🔄 CI/CD Integration
The repository contains a GitHub Actions workflow directory.

The intended CI/CD execution flow is:

### End-to-End Automation Journey

```text
Developer Push
      |
      v
GitHub Repository
      |
      v
GitHub Actions
      |
      v
Maven Build
      |
      v
TestNG Execution
      |
      v
UI and API Automation
      |
      v
Allure Results
      |
      v
Test Execution Artifacts

```

This project is continuously evolving as new automation capabilities, reporting features, CI/CD integrations, and cloud execution support are implemented.

---

🗺️ Project Roadmap

|Feature       |	Status|
|--------------|-----------------------------------|
|Selenium | UI automation	Implemented|
|REST Assured | API automation	Implemented|
|Page Object Model |	Implemented|
|Centralized PageManager |	Implemented in current development|
|Reusable utilities |	Implemented|
|TestNG execution |	Implemented|
|Maven integration |	Implemented|
|Allure reporting |	Implemented|
|Failure classification |	Implemented|
|Parallel test configuration |	Configured|
|Historical reporting trends |	Further development|
|GitHub Actions automation |	Verify workflow|
|Cross-browser execution |	Further development|
|Docker integration |	Planned|
|Cloud-based execution |	Planned|
|Expanded negative testing |	Planned|

---

🧠 Skills Demonstrated
This project demonstrates practical knowledge of:


|Area          |           Skills|
|--------------|-----------------------------------|
|Programming	| Java, OOP|
|UI automation	| Selenium WebDriver|
|Design patterns | Page Object Model, PageManager|
API automation	 | REST Assured|
Test execution	 | TestNG|
Build management | Maven|
Browser management | WebDriver, WebDriverManager|
Test data | JSON, Jackson, Java Faker, Apache POI|
Reporting | Allure|
Logging	| Log4j2, SLF4J|
Version control | Git, GitHub|
CI/CD | GitHub Actions|
Framework design | Modular architecture, reusable components|
Test engineering | Failure analysis, parallel execution|


---

👨‍💻 Author
Ayanda Khaka
QA Automation Engineer | SDET | Test Automation Engineer

This project represents my practical journey in designing and developing a hybrid automation framework using Java, Selenium WebDriver and REST Assured.

My focus is on building maintainable, reusable and scalable automation solutions that combine UI testing, API testing, framework architecture, reporting and continuous testing practices.

GitHub: ayandakhaka

Project: Hybrid Selenium Automation Framework

---
⭐ If you find this project useful, feel free to explore the repository and follow its development.



If you found this project interesting, feel free to ⭐ the repository, explore the code, or connect with me on LinkedIn to follow my QA automation journey.

