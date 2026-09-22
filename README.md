# ITS E-Learning Portal Test Automation

This repository contains a Selenium test automation project for the ITS online learning portal. It was developed as part of a graduation thesis and demonstrates Page Object Model (POM), data-driven testing (DDT), explicit waits, and end-to-end user flows.

## Test documentation

The documented test cases are available in [ITS Portal Test Cases](docs/test-cases/ITS%20Portal%20Test%20Cases.xlsx).

## Technology stack

- Java 26
- Maven
- Selenium WebDriver 4.43.0
- TestNG 7.12.0
- WebDriverManager 6.3.4
- Apache POI 5.2.5
- Google Chrome

## Project structure

```text
src/test/java
├── Base
│   ├── BaseTest.java
│   └── ExcelReader.java
├── Pages
│   ├── LoginPage.java
│   └── HomePage.java
├── TestData
│   └── CredentialBuilderHelper.java
└── Tests
    ├── LoginTest.java
    ├── HomePageTests.java
    └── E2E.java
```

- `Base` contains WebDriver lifecycle management and Excel data reading.
- `Pages` contains page elements, user actions, and explicit waits.
- `TestData` contains utilities used to prepare test credentials.
- `Tests` contains login, dashboard, and end-to-end test scenarios.

## Test coverage

### Login tests

- Login with valid credentials
- Login with invalid credentials supplied by a TestNG data provider
- Empty username and password validation
- Password case sensitivity
- Username case behavior
- Password field masking
- Keyboard navigation with the Tab key

### Dashboard tests

- Enable dark mode
- Hide the Live Class panel
- Restore the Live Class panel

### End-to-end tests

- Log in and log out
- Open and delete the first inbox message
- Send a question to the AI mentor and wait for a response

## Test data setup

Create the following workbook locally before running the tests:

```text
src/test/java/TestData/DDT.xlsx
```

The workbook must contain a sheet named `Sheet1` with this column layout:

| Column | Value |
| --- | --- |
| A | Valid username |
| B | Valid password |
| C | Invalid username |
| D | Invalid password |

The first Excel row contains headers. Test data starts on the second row. Additional rows in columns C and D are used by the `invalidCredentials` data provider.

`DDT.xlsx` is excluded through `.gitignore` because it contains account credentials. Do not commit the workbook or real credentials to the repository.

## Running the tests

Clone the repository and open it as a Maven project in IntelliJ IDEA, or run the tests from a terminal in the project directory.

Run the standard TestNG suite:

```bash
mvn clean test
```

Run the standard suite in headless Chrome:

```bash
mvn -Dheadless=true clean test
```

The standard suite is defined in `testng.xml` and runs `LoginTest` followed by `HomePageTests`.

Run the E2E class separately:

```bash
mvn -Dtest=Tests.E2E test
```

The `shouldDeleteFirstMessage` E2E scenario permanently deletes the first message from the test account. Run this class only with data that may be changed by automated tests.

## Design

The project follows the Page Object Model:

- Tests describe the user flow and contain assertions.
- Page classes locate elements and expose actions that can be performed on each page.
- `BaseTest` creates and closes a fresh Chrome session for every test method.
- `ExcelReader` supplies credentials and invalid login combinations from the local workbook.
- Explicit waits synchronize actions with dynamic portal content and page refreshes.
