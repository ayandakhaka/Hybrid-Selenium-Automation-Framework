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
|           └── AutomationExerciseAPITests
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

# 🔄 CI/CD Integration

The next phase of the project is integrating the automation framework with **GitHub Actions** to enable automated test execution as part of the software development workflow.

### Planned CI/CD Workflow

```text
Developer Push
       │
       ▼
GitHub Repository
       │
       ▼
GitHub Actions
       │
       ▼
Build with Maven
       │
       ▼
Run UI & API Tests
       │
       ▼
Generate Allure Results
       │
       ▼
Generate Allure Report
       │
       ▼
Publish Test Results
```
### CI/CD Pipeline Capabilities

| Capability | Status |
|---|:---:|
| Automated Execution on Push | 🔄 In Progress |
| Pull Request Validation | ⏳ Planned |
| Maven Test Execution | 🔄 In Progress |
| UI Test Execution | 🔄 In Progress |
| API Test Execution | 🔄 In Progress |
| Allure Result Generation | 🔄 In Progress |
| Test Result Artifacts | ⏳ Planned |
| Allure History & Trends | ⏳ Planned |

**Current Status:** 🔄 In Progress

---
# 🎯 Project Goals

The goal of this project is to build a modern, scalable, and maintainable **UI & API Test Automation Framework** that demonstrates industry best practices in software quality engineering.

The framework is designed to showcase:

- ✅ UI Test Automation using Selenium WebDriver
- ✅ API Test Automation using REST Assured
- ✅ Page Object Model (POM)
- ✅ TestNG Test Execution
- ✅ Comprehensive Allure Reporting
- ✅ Failure Classification & Analysis
- 🔄 CI/CD Pipeline with GitHub Actions
- ⏳ Cloud-Based Test Execution using AWS

### End-to-End Automation Journey

```text
                 QA Automation Framework

       ┌─────────────────────────────────┐
       │       UI Automation             │
       │     Selenium WebDriver          │
       └───────────────┬─────────────────┘
                       │
                       ▼
       ┌─────────────────────────────────┐
       │       API Automation            │
       │        REST Assured             │
       └───────────────┬─────────────────┘
                       │
                       ▼
       ┌─────────────────────────────────┐
       │      Test Execution             │
       │           TestNG                │
       └───────────────┬─────────────────┘
                       │
                       ▼
       ┌─────────────────────────────────┐
       │      Allure Reporting           │
       │  Steps • Logs • Screenshots     │
       │  API Attachments • Categories   │
       └───────────────┬─────────────────┘
                       │
                       ▼
       ┌─────────────────────────────────┐
       │     Failure Analysis            │
       │ Categories • Trends • History   │
       └───────────────┬─────────────────┘
                       │
                       ▼
       ┌─────────────────────────────────┐
       │      GitHub Actions CI/CD       │
       │ Automated Build & Test          │
       └───────────────┬─────────────────┘
                       │
                       ▼
       ┌─────────────────────────────────┐
       │      AWS Cloud Execution        │
       │  Scalable Test Execution        │
       └─────────────────────────────────┘
```

This project is continuously evolving as new automation capabilities, reporting features, CI/CD integrations, and cloud execution support are implemented.

---

# 📚 Skills & Technologies Demonstrated

This project showcases practical experience with the following technologies and testing concepts.

| Category | Technologies |
|----------|--------------|
| 💻 Programming Language | Java |
| 🖥️ UI Automation | Selenium WebDriver |
| 🔌 API Automation | REST Assured |
| 🧪 Test Framework | TestNG |
| 📦 Build Tool | Maven |
| 🎨 Design Pattern | Page Object Model (POM) |
| 📊 Test Reporting | Allure Report |
| 📎 Attachments | Screenshots, Logs, API Requests & Responses |
| 🚨 Failure Analysis | Allure Categories & Trends |
| 📝 Logging | SLF4J / Log4j |
| 🔄 CI/CD | GitHub Actions |
| ☁️ Cloud | AWS *(Planned)* |
| 🐳 Containerization | Docker *(Planned)* |
| 🏗️ Architecture | Microservices Testing |
| 🔐 Security | API Security Testing |
| 🚀 Quality Engineering | Continuous Testing |

---

# 🚀 Project Roadmap

| Phase | Status |
|-------|:------:|
| Selenium UI Automation | ✅ Completed |
| REST Assured API Automation | ✅ Completed |
| Allure Reporting | ✅ Completed |
| Failure Categories | ✅ Completed |
| Allure Trends & History | 🔄 In Progress |
| GitHub Actions CI/CD | 🔄 In Progress |
| Automated Allure Reports | 🔄 In Progress |
| AWS Cloud Execution | ⏳ Planned |
| Docker Integration | ⏳ Planned |
| Cross-Browser Execution | ⏳ Planned |
| Parallel Test Execution | ⏳ Planned |
| Microservices Testing | ⏳ Planned |

---

# 👨‍💻 Author

## Ayanda Khaka

**QA Automation Engineer | SDET | Test Automation Engineer**

This repository represents my ongoing journey in building enterprise-level automation solutions using modern testing tools and best practices. My focus is on developing scalable automation frameworks that combine **UI automation, API testing, reporting, CI/CD, and cloud technologies** to support high-quality software delivery.

If you found this project interesting, feel free to ⭐ the repository, explore the code, or connect with me on LinkedIn to follow my QA automation journey.

