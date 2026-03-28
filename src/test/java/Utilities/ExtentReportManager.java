package Utilities;

import java.awt.Desktop;
import java.io.File;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.baseClass;

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    String reportPath;

    public void onStart(ITestContext context) {

        reportPath = System.getProperty("user.dir") + "/Reports/myReport.html";

        sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setReportName("Automation Test Report");
        sparkReporter.config().setDocumentTitle("TestNG Extent Report");
        sparkReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Tester", "Priya");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
    }

    public void onTestStart(ITestResult result) {
        test.set(extent.createTest(result.getName()));
    }

    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test PASSED: " + result.getName());
    }

    public void onTestFailure(ITestResult result) {

        test.get().fail("Test FAILED: " + result.getName());
        test.get().fail(result.getThrowable());

        try {
            WebDriver driver = ((baseClass) result.getInstance()).driver;

            String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName());
            test.get().addScreenCaptureFromPath(screenshotPath);

            String videoPath = System.getProperty("user.dir") + "/videos/";
            test.get().info("Video saved at: " + videoPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onTestSkipped(ITestResult result) {
        test.get().skip("Test SKIPPED: " + result.getName());
    }

    public void onFinish(ITestContext context) {

        extent.flush();

        try {
            Desktop.getDesktop().browse(new File(reportPath).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}