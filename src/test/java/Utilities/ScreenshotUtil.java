package Utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

    public static String captureScreenshot(WebDriver driver, String testName) throws Exception {

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        String path = System.getProperty("user.dir") + "/Screenshots/" + testName + "_" + timeStamp + ".png";

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(path);

        dest.getParentFile().mkdirs();
        FileUtils.copyFile(src, dest);

        return path;
    }
}