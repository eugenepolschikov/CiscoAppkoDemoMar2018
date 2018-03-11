package com.cisco.appko.selenium.listeners;


import com.cisco.appko.selenium.testcases.core.Screen;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.nio.charset.Charset;
import java.util.logging.Level;

public class CustomTestListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            Object currentClass = result.getInstance();
            if (currentClass instanceof Screen) {
                WebDriver driver = ((Screen) currentClass).getDriver();
                byte[] srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                saveScreenshot(srcFile);
                postBrowserLogs(driver);
            }
        } catch (Exception e) {
            Error e1 = new Error(e.getMessage());
            e1.setStackTrace(e.getStackTrace());
            throw e1;
        }

    }

    @Override
    public void onTestSuccess(ITestResult result) {

        Object currentClass = result.getInstance();
        if (currentClass instanceof Screen) {
            WebDriver driver = ((Screen) currentClass).getDriver();
            byte[] srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            saveScreenshot(srcFile);
            postBrowserLogs(driver);
        }

    }


    @Attachment(value = "Page screenshot", type = "image/png")
    private byte[] saveScreenshot(byte[] screenshot) {
        return screenshot;
    }


    @Attachment(value = "WebDriver browser console logs", type = "text/html")
    public byte[] postBrowserLogs(WebDriver driver) {

        Logs logz = driver.manage().logs();
        LogEntries logEntries = logz.get(LogType.BROWSER);

        String lowSeverityLogSummary = "##############LOW-SEVERITY CONSOLE LOGS:###########\n";
        String highSeverityLogSummary = "#####ERRRR!!###HIGH-SEVERITY CONSOLE LOGS:###ERRR!!!#####\n";


        if (logEntries.getAll().size() > 0) {

            for (LogEntry logEntry : logEntries) {
                if (logEntry.getLevel() == Level.SEVERE && !logEntry.getMessage().contains("chrome-extension")) {
                    highSeverityLogSummary += "ERROR ENCOUNTERED!:###### PAGE:'" + driver.getCurrentUrl() +
                            "'\n##########" + "<mark>" + logEntry.getMessage().toString() + "</mark>";

                } else if (!logEntry.getMessage().contains("chrome-extension")) {
                    lowSeverityLogSummary += "<b>" + logEntry.getMessage().toString() + "</b>" + "\n";
                    lowSeverityLogSummary += "#######\n";
                }

            }  // logging all other low-severity stuff from browser console
//            if (lowSeverityLogSummary.length() > 0) {
//                log.warn("URL\t####" + driver.getCurrentUrl() + "; \t####BROWSER CONSOLE LOGS:####\n" +
//                        lowSeverityLogSummary);
//
//            }


        }
        //byte[] srcFile = ("<mark>Response time:</mark> " + String.valueOf(myRes.getTime()) + " milliseconds");
        return (highSeverityLogSummary + lowSeverityLogSummary).getBytes(Charset.forName("UTF-8"));
    }
}