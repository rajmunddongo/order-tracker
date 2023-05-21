package onlab.aut.bme.hu.java;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;



public class SeleniumTest {


    @Test
    public void eightComponents() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Selenium\\chromedriver.exe");

        // Create an instance of ChromeDriver
        WebDriver driver = new ChromeDriver();

        // Navigate to the login page
        driver.get("http://localhost:4200/");

        // Find the username/email input field and enter the value
        // Get the page title
        String pageTitle = driver.getTitle();

        // Print the page title
        System.out.println("Page Title: " + pageTitle);
        // Verify the page title
        if (pageTitle.equals("Angular")) {
            System.out.println("Page title is correct. Test passed!");
        } else {
            System.out.println("Page title is incorrect. Test failed!");
        }


        // Wait for the page to load or perform assertions on the resulting page

        // Close the browser
        driver.quit();
    }

    @Test
    public void registerpage() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Selenium\\chromedriver.exe");

        // Create an instance of ChromeDriver
        WebDriver driver = new ChromeDriver();

        // Navigate to the login page
        driver.get("http://localhost:4200/");

        // Find the username/email input field and enter the value
        // Get the page title
        String pageTitle = driver.getTitle();

        // Print the page title
        System.out.println("Page Title: " + pageTitle);
        // Verify the page title
        if (pageTitle.equals("Angular")) {
            System.out.println("Page title is correct. Test passed!");
        } else {
            System.out.println("Page title is incorrect. Test failed!");
        }


        // Wait for the page to load or perform assertions on the resulting page

        // Close the browser
        driver.quit();
    }

    @Test
    public void testLogin() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Selenium\\chromedriver.exe");

        // Create an instance of ChromeDriver
        WebDriver driver = new ChromeDriver();

        // Navigate to the login page
        driver.get("http://localhost:4200/login");

        // Find the username/email input field
        WebElement usernameField = driver.findElement(By.name("username"));
        // Enter the username or email
        usernameField.sendKeys("your_username_or_email");

        // Find the password input field
        WebElement passwordField = driver.findElement(By.name("password"));
        // Enter the password
        passwordField.sendKeys("your_password");

        // Find the sign-in button
        WebElement signInButton = driver.findElement(By.id("signIn"));
        // Click on the sign-in button
        signInButton.click();

        // Wait for the login process to complete (you can use explicit waits here)

        // Verify that the login was successful (check for a specific element on the next page)

        // Close the browser
        driver.quit();
    }

    @Test
    public void testSeliniumWorking() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Selenium\\chromedriver.exe");

        // Create a new instance of the ChromeDriver
        WebDriver driver = new ChromeDriver();

        // Navigate to a webpage
        driver.get("https://www.example.com");

        // Get the page title
        String pageTitle = driver.getTitle();

        // Print the page title
        System.out.println("Page Title: " + pageTitle);

        // Verify the page title
        if (pageTitle.equals("Example Domain")) {
            System.out.println("Page title is correct. Test passed!");
        } else {
            System.out.println("Page title is incorrect. Test failed!");
        }

        // Close the browser
        driver.quit();
    }
}
