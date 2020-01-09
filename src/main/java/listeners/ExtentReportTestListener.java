package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import logger.ExtentManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExtentReportTestListener implements ITestListener {
    private static ExtentReports extent = ExtentManager.createInstance("reports/extent.html");
    private static Map extentTestMap = new HashMap();

    public static synchronized ExtentTest getTest() {
        return (ExtentTest)extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = extent.createTest(testName, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }

    @Override
    public synchronized void onStart(ITestContext context) {
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        extent.flush();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        startTest(result.getMethod().getMethodName()+" for "+result.getParameters()[1],result.getParameters()[2].toString());
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        getTest().pass("Test Passed.");
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        getTest().fail("Test Failed.");
        getTest().fail(result.getThrowable());
        //MediaEntityModelProvider mediaModel;
        /*
        try {
            String path = ScreenshotRecorder.getBrowserScreenshot();
            //mediaModel = MediaEntityBuilder.createScreenCaptureFromPath()).build();
            getTest().fail(ScreenshotRecorder.getLink("Failure screenshot",path));
        } catch (IOException e) {
        }*/

    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        getTest().skip(result.getThrowable());
    }

    @Override
    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }
}