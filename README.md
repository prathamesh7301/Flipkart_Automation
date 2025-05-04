# Flipkart Automation Testing Project

This project automates the login and product search functionalities on the [Flipkart](https://www.flipkart.com/) e-commerce platform using **Selenium WebDriver**, **TestNG**, and **Firefox browser profiles**.

## 🚀 Project Overview

The purpose of this project is to:
- Reuse an existing Flipkart login session through a Firefox profile. (Will add new functionality in future)
- Detect whether the user is logged in or not.
- Automate login using mobile number and OTP (manual input).
- Search for mobile products.
- Extract and display product name, price, and ratings from the results.

This project simulates a e-commerce user journey and can serve as a foundational structure for more advanced UI test automation suites.

---

## 🛠️ Technologies Used

- **Java** – Programming language
- **Selenium WebDriver** – For browser automation
- **TestNG** – Testing framework
- **FirefoxDriver (GeckoDriver)** – For running tests on Mozilla Firefox
- **Firefox Profile** – To preserve login sessions across runs

---

## 🔧 Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/FlipkartAutomation.git
   cd FlipkartAutomation

2. Install Prerequisites

    Java JDK (8+)

    Maven or any Java build tool

    GeckoDriver (for Firefox)

    Firefox browser

3. Create Firefox Profile for Flipkart Login

    Close all Firefox windows.

    Run firefox.exe -P to open the Profile Manager.

    Create a profile named FlipkartAutomation.

    Launch Firefox using that profile and manually log in to Flipkart.

    Close Firefox to save the session.

4. Update the Firefox profile path

    Replace the profilePath in your Java code with your actual profile directory:

    String profilePath = "C:\\Users\\<YourUsername>\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\<your-profile>.FlipkartAutomation";

5. Run Tests

    Use your IDE (like IntelliJ or Eclipse)
