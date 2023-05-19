package onlab.aut.bme.hu.java;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;



public class SeleniumTest {


    @Test
    public void eightComponents() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");

        // Create an instance of ChromeDriver
        WebDriver driver = new ChromeDriver();

        // Navigate to the login page
        driver.get("https://example.com/login");

        // Find the username/email input field and enter the value
        WebElement emailField = driver.findElement(By.name("email"));
        emailField.sendKeys("test");

        // Find the password input field and enter the value
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("test");

        // Find the submit button and click it
        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
        submitButton.click();

        // Wait for the page to load or perform assertions on the resulting page

        // Close the browser
        driver.quit();
    }
}
