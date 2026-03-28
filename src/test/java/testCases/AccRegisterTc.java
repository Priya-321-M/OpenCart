package testCases;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.remarks.video.annotations.Video;

import Utilities.ExtentReportManager;
import pageObject.HomePage;
import pageObject.RegisterPage;
import testBase.baseClass;

public class AccRegisterTc extends baseClass {

    @Test
    @Video
    public void createNewAccount() {

        ExtentReportManager.test.get().info("Starting test case");

        HomePage home = new HomePage(driver);
        home.clickMyaccount();
        home.clickRegister();

        ExtentReportManager.test.get().info("Navigated to registration page");

        RegisterPage reg = new RegisterPage(driver);

        reg.enterFirstname(randomString().toUpperCase());
        reg.enterLastname(randomString().toUpperCase());
        reg.enterEmail(randomString().toLowerCase() + "@gmail.com");
        reg.enterTelephnone(randomNumeric());

        String password = randomAlphaNumeric();
        reg.enterPassword(password);
        reg.enterConfpwd(password);

        ExtentReportManager.test.get().info("Entered customer details");

        reg.clickAgreechk();
        reg.clickContinue();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String confirmMsg = wait.until(
                ExpectedConditions.visibilityOf(reg.successMsg)
        ).getText();

        Assert.assertEquals(confirmMsg, "Your Account Has Been Created!");

        ExtentReportManager.test.get().pass("Account created successfully");

        home.clickHomeicon();

        ExtentReportManager.test.get().info("Returned to homepage");
    }
}