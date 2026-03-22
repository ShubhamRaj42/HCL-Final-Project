# ✈️ Cleartrip Flight Search Automation Framework

## 📌 Overview

This project automates the **flight search functionality on Cleartrip** using **Selenium WebDriver** and **TestNG**.

It follows the **Page Object Model (POM)** design pattern to ensure:

- Maintainability
- Scalability
- Reusability
- Modularity

## The automation validates flight search results without completing the booking process.

=======
The automation validates flight search results without completing the booking process.

---

## 📁 Project Structure

```
Hcl-final-project/
├── pom.xml                          # Maven dependencies & plugins
├── testng.xml                       # Full test suite configuration
│
├── src/main/java/com/hcl/project/
│   ├── pages/
│   │   ├── HomePage.java            # Browsing the HomePage and their functionalities
│   │   └── FlightListingPage.java   # Listing the flight name with their price
│   ├── base/
│   │   ├── BasePage.java            # Provides core browser actions (click, sendKeys, waits)
│   │
│   ├── config/
│   │   ├── ConfigReader.java          # Reads values (like URL, browser) from the properties file.
│   │
│   └── utils/
│       ├── ExtentReportListener.java       # Generates and manages Extent Reports for test execution.
│       ├── WaitUtils.java           # Explicit wait helpers
│       ├── DriverFactory.java    # Initializes and manages WebDriver instances.
│       └── LogUtils.java           # Handles logging of test execution details.
│
├── src/main/resources
│   ├── config.properties         # Provides info about browser and url
|
├── src/test/java/com/hcl/project/
│   ├── base/
│   │   ├── BaseTest.java            # Basic setup test to be extended by other test classes
│   ├── tests/
│   │   ├── HomeTest.java            # Testing the home page
│   │   ├── FlightListingTest.java    # Testing on listing flight names and values

```

## 🧠 Key Components

### 🔹 Base Layer

- **BasePage.java** → Contains reusable methods like click, sendKeys, waits

### 🔹 Configuration

- **ConfigReader.java** → Reads properties like URL, browser from config file

### 🔹 Utilities

- **DriverFactory.java** → Manages WebDriver initialization
- **WaitUtils.java** → Handles explicit waits
- **LogUtils.java** → Logging utility
- **ExtentReportListener.java** → Generates test reports

### 🔹 Test Layer

- **BaseTest.java** → Setup & teardown using TestNG annotations
- **HomeTest.java** → Contains test cases

---

## 🚀 Test Scenario

### ✅ Cleartrip Flight Search & Validation

### Steps:

1. Open https://www.cleartrip.com
2. Handle login popup (if displayed)
3. Select:
   - Flights
   - One-way journey
4. Enter:
   - From city
   - To city
5. Select a future departure date
6. Click **Search Flights**

---

## 🔍 Validations

- Flight results page is displayed
- Flights are listed
- Each flight shows:
  - Airline Name ✈️
  - Ticket Price 💰

---

## ⚠️ Challenges Handled

- Dynamic elements (AJAX loading)
- Auto-suggestion dropdowns
- Calendar date picker
- Popups / login interruptions
- Synchronization using explicit waits

---

## 🧰 Tech Stack

| Category        | Technology         |
| --------------- | ------------------ |
| Language        | Java               |
| Automation      | Selenium WebDriver |
| Framework       | TestNG             |
| Build Tool      | Maven              |
| IDE             | Eclipse            |
| Version Control | Git                |
| Reporting       | ExtentReports      |

---

## ▶️ How to Run the Project

### 1️⃣ Clone Repository

git clone https://github.com/ravi-gautam17/HCL-Final-Project.git

### 2️⃣ Import in Eclipse

- File → Import → Existing Maven Project

### 3️⃣ Install Dependencies

mvn clean install

### 4️⃣ Run Tests

- Right-click → Run as TestNG  
  **OR**

mvn test

---

## 🎯 Learning Outcomes

- Page Object Model (POM)
- Handling dynamic web elements
- Explicit waits in Selenium
- Framework design best practices
- Writing maintainable test scripts

---

## 🔮 Future Enhancements

- Round-trip testing
- Filters validation (price, airlines)
- Data-driven testing
- CI/CD integration (GitHub Actions)

---

## 👥 Team Mavericks 🚀

**Team Members:**

- Shubham Raj
- Ravi Gautam
- Sarthak Kumar
- Anjali Kumari

> _"Driven by innovation, powered by teamwork."_

---

## 💡 Quote

> “Confidence grows with every stable automation you build.”

---

## 📜 License

