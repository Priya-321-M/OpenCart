package testBase;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import Utilities.ScreenRecorderUtil;

public class baseClass {

    public static ScreenRecorderUtil screenRecorder;
    public WebDriver driver;
    public Logger logger;
    public Properties p;

    @BeforeClass
    @Parameters({"browser","os"})
    public void setup(String br, String os) throws IOException {

        try (FileReader file = new FileReader("./src/test/resources/config.properties")) {
            p = new Properties();
            p.load(file);
        }

        logger = LogManager.getLogger(this.getClass());

        if (p.getProperty("execution-env").equalsIgnoreCase("remote")) {

            String gridUrl = "http://localhost:4444/wd/hub";
            DesiredCapabilities cap = new DesiredCapabilities();

            switch (br.toLowerCase()) {
                case "chrome":
                    cap.setBrowserName("chrome");
                    break;
                case "edge":
                    cap.setBrowserName("MicrosoftEdge");
                    break;
                case "firefox":
                    cap.setBrowserName("firefox");
                    break;
                default:
                    throw new RuntimeException("Invalid browser: " + br);
            }

            switch (os.toLowerCase()) {
                case "windows":
                    cap.setPlatform(Platform.WINDOWS);
                    break;
                case "linux":
                    cap.setPlatform(Platform.LINUX);
                    break;
                case "mac":
                    cap.setPlatform(Platform.MAC);
                    break;
                default:
                    throw new RuntimeException("Invalid OS: " + os);
            }

            driver = new RemoteWebDriver(new URL(gridUrl), cap);

            driver.get(p.getProperty("appURL"));
            driver.manage().window().maximize();

            logger.info("Application launched successfully");
        }

        if (p.getProperty("execution-env").equalsIgnoreCase("local")) {

            String validatedOS = validateLocalOS(os);
            logger.info("Running tests on OS: " + validatedOS);

            switch (br.toLowerCase()) {

                case "chrome":
                    driver = new ChromeDriver();
                    logger.info("Chrome Browser launched");
                    break;

                case "edge":
                    driver = new EdgeDriver();
                    logger.info("Edge Browser launched");
                    break;

                default:
                    throw new RuntimeException("Invalid browser: " + br);
            }

            driver.manage().deleteAllCookies();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            driver.get(p.getProperty("appURL"));
            driver.manage().window().maximize();

            logger.info("Application launched successfully");

            ScreenRecorderUtil.startRecording("TestVideo_" + System.currentTimeMillis());
        }
    }

    private String validateLocalOS(String os) {

        if (os == null || os.isEmpty()) {
            throw new RuntimeException("OS parameter is missing!");
        }

        switch (os.toLowerCase()) {
            case "windows":
            case "linux":
            case "mac":
            case "macos":
                return os.toLowerCase();
            default:
                throw new RuntimeException("Unsupported OS: " + os);
        }
    }

    @AfterClass
    public void tearDown() {

        if (driver != null) {
            driver.quit();
            logger.info("Browser closed");
        }

        ScreenRecorderUtil.stopRecording();
    }

    public String randomString() {
        return RandomStringUtils.randomAlphabetic(5);
    }

    public String randomNumeric() {
        return RandomStringUtils.randomNumeric(10);
    }

    public String randomAlphaNumeric() {
        return RandomStringUtils.randomAlphanumeric(8);
    }
}