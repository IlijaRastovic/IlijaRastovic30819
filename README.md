# ITS E-Learning Portal Test Automation

Automated tests for the ITS online learning portal, developed for a graduation thesis. The project uses Java, Selenium WebDriver, TestNG, the Page Object Model (POM), and Excel-based test data.

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

## What you need

- **JDK 26** (the version configured in `pom.xml`)
- **Apache Maven 3** (or the Maven bundled with IntelliJ IDEA)
- **Google Chrome**
- An internet connection and an active ITS portal test account
- The local `DDT.xlsx` workbook described below

You can use any operating system supported by these tools. The step-by-step terminal examples below use **Windows PowerShell**. On the first run, Maven downloads dependencies and WebDriverManager resolves ChromeDriver, so network access is needed.

## 1. Get the project

Clone the repository and enter its root directory (the folder containing `pom.xml`):

```powershell
git clone https://github.com/IlijaRastovic/IlijaRastovic30819.git
cd IlijaRastovic30819
```

If you already have the project, open a terminal in that directory. In IntelliJ IDEA, open the project folder, let Maven import `pom.xml`, and open the **Terminal** tab. Use JDK 26 as the project SDK and Maven runner JDK.

## 2. Make Java and Maven available in PowerShell

Install JDK 26 and [Apache Maven](https://maven.apache.org/install). Maven must be on `Path`; Java must point to JDK 26. Check both from the project terminal:

```powershell
java -version
mvn -v
```

`mvn -v` should show **Java 26**. If PowerShell says `mvn` is not recognized, Maven's `bin` directory is missing from `Path`. For a **temporary fix in the current terminal**, replace the two example paths with the paths on your computer:

```powershell
$env:JAVA_HOME = "C:\path\to\jdk-26"
$env:Path = "C:\path\to\apache-maven\bin;$env:Path"
mvn -v
```

If you use the Maven bundled with IntelliJ IDEA instead of a separate installation, use its `bin` directory. For example, on a default IntelliJ IDEA 2026.1 installation:

```powershell
$env:JAVA_HOME = "C:\Users\Win10\.jdks\openjdk-26"
$env:Path = "C:\Program Files\JetBrains\IntelliJ IDEA 2026.1\plugins\maven\lib\maven3\bin;$env:Path"
mvn -v
```

Those paths are **examples**, not part of the project; IntelliJ's installation path can differ or change after an update. For a permanent setup, open Windows **Edit environment variables for your account**, set `JAVA_HOME` to your JDK 26 folder, and add your Maven `bin` folder to the user `Path`. Then restart PowerShell (and IntelliJ IDEA if it was open) and check `mvn -v` again. See the [IntelliJ Maven guide](https://www.jetbrains.com/help/idea/maven-support.html) if you prefer running Maven from its tool window.

## 3. Prepare the private test data

Create this file in the cloned project:

```text
src/test/java/TestData/DDT.xlsx
```

The workbook must contain a sheet named **`Sheet1`**. Put text headers in row 1 and data in row 2:

| Column A | Column B | Column C | Column D |
| --- | --- | --- | --- |
| Valid username | Valid password | Invalid username | Invalid password |
| Your test account username | Your test account password | A username that cannot log in | A password that cannot log in |

The valid credentials must be in **A2 and B2**. Add more invalid username/password pairs in columns C and D on subsequent rows. Each data row should have a value in both C and D: the TestNG data provider reads every row through the last used row. Keep these cells formatted as text.

`DDT.xlsx` is ignored by Git because it contains credentials. Do not commit it, put real passwords in the test-case documentation workbook, or share it with the repository. The documented test cases are in [docs/test-cases/ITS Portal Test Cases.xlsx](docs/test-cases/ITS%20Portal%20Test%20Cases.xlsx); that workbook does **not** replace `DDT.xlsx`.

## 4. Run the tests

Run commands from the project root, after `mvn -v` reports Java 26.

**Login and dashboard tests (visible Chrome):**

```powershell
mvn clean test
```

**Login and dashboard tests (headless Chrome):**

```powershell
mvn "-Dheadless=true" clean test
```

The default suite is defined in `testng.xml`: it runs `Tests.LoginTest`, then `Tests.HomePageTests`. Headless mode only hides the Chrome window; it does not change which tests run.

**E2E tests (visible Chrome):**

```powershell
mvn "-Dtest=Tests.E2E" test
```

**E2E tests (headless Chrome):**

```powershell
mvn "-Dheadless=true" "-Dtest=Tests.E2E" test
```

To run **all** tests in headless mode, run the standard-suite command and then the E2E command. `clean` is optional on the second command because the first command already cleans the build output. The E2E test `shouldDeleteFirstMessage` **permanently deletes the first inbox message**; use a test account whose messages may be deleted.

Maven prints the result in the terminal. Detailed TestNG/Surefire reports are written under `target/surefire-reports/`.

## Screenshots on failure

When a test fails, `BaseTest` saves a screenshot before closing Chrome. The image is stored in the local `screenshots/` directory, and its full path is printed in the TestNG output. Screenshots are ignored by Git and remain available after `mvn clean`.

## Troubleshooting

- **`mvn` is not recognized:** Add Maven's `bin` directory to `Path`, or use the temporary PowerShell setup above. Restart the terminal after changing permanent environment variables.
- **`mvn -v` shows an older Java version:** Set `JAVA_HOME` to JDK 26 and reopen the terminal. IntelliJ's project SDK and its Maven runner JDK should also be 26.
- **`FileNotFoundException` for `DDT.xlsx`:** Create the private workbook at the exact path above. The public test-case workbook is a different file.
- **Login or portal test fails:** Check that the portal is reachable, the account is active, and A2/B2 contain valid credentials. The tests depend on the live portal, so its content or timing can affect results. Check the failure screenshot and `target/surefire-reports/` for details.

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

## Design

The project follows the Page Object Model:

- Tests describe the user flow and contain assertions.
- Page classes locate elements and expose actions that can be performed on each page.
- `BaseTest` creates and closes a fresh Chrome session for every test method.
- `ExcelReader` supplies credentials and invalid login combinations from the local workbook.
- Explicit waits synchronize actions with dynamic portal content and page refreshes.
