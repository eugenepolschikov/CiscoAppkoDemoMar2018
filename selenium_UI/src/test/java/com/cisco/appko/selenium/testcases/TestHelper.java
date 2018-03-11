package com.cisco.appko.selenium.testcases;

import com.cisco.appko.selenium.utility.DriverInit;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.allure.annotations.Step;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TestHelper {
    private final static Logger log = LoggerFactory.getLogger(TestHelper.class);

    public static WebDriver driverInit(WebDriver driver, String startingUrl) throws MalformedURLException {
        driver = DriverInit.driverSetUp(driver);
        driver.get(startingUrl);

        return driver;

    }

    /**
     *
     * @param driver
     * @param startingUrl
     * @return
     * @throws MalformedURLException
     * init firefox INSTANCE
     *   http://www.swtestacademy.com/parallel-tests-selenium-grid-junit/ !!!!!!!
     *   capabilities options: https://www.quora.com/How-do-I-run-a-Selenium-test-on-Chrome-and-Firefox-simultaneously    !!!!!!!!!
     *   http://www.seleniumeasy.com/selenium-tutorials/parallel-execution-in-selenium-grid         !!!!!
     */

    @Step("restarting WebDriver instance")
    public static WebDriver driverRestart(WebDriver driver, String startingUrl) throws MalformedURLException {
        driver.close();
        return driverInit(driver, startingUrl);
    }

    @Step("initializing cookies for API requests")
    public static Map<String, String> initCookieSetForWebapiRequests(WebDriver driver) {
        log.info("exctacting cookies from driver instance...... To use cookies for api requests");
        Map<String, String> cookies = new HashMap<String, String>();

        //        init cookies for resassured
        Set<Cookie> driverCurrentCookies = driver.manage().getCookies();
        for (Cookie c : driverCurrentCookies) {
            cookies.put(c.getName(), c.getValue());
        }

        log.info("cookies extracted: " +cookies.toString());

        return cookies;
    }


}
