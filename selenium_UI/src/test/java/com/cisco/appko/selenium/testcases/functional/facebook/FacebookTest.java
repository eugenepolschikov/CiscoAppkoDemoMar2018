package com.cisco.appko.selenium.testcases.functional.facebook;

import com.cisco.appko.automation.selenium.pages.facebook.FbLoginPage;
import com.cisco.appko.automation.user.BaseUser;
import com.cisco.appko.selenium.listeners.CustomTestListener;
import com.cisco.appko.selenium.testcases.TestSuitesBase;
import com.cisco.appko.selenium.testcases.core.Screen;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

import java.io.IOException;

import static com.cisco.appko.selenium.testcases.TestHelper.driverInit;

/**
 * Created by eugene polschikov on 2018-03-11.
 */
@Listeners({CustomTestListener.class})
public class FacebookTest extends TestSuitesBase implements Screen {

    private final static Logger log = LoggerFactory.getLogger(FacebookTest.class);

    private String baseUrl = configs.getProdBaseUrl();
    private WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public void init() throws IOException {
        driver = driverInit(driver, baseUrl);
        configs.masterusersDataLoader();

    }


    @Title("Facebook login and navigate ")
    @Features("Facebook automation")
    @Stories("Login to facebook, navigate to the psot, and extract reactions number")
    @Test(groups = {"functional", "E2E", "regresion", "smoke"})
    public void facebookEndToEndTest() throws Exception {
        SoftAssert softAssertion = new SoftAssert();

        BaseUser fbMasterUser = new BaseUser(configs.getFacebookLogin(), configs.getFacebookPassword());
        log.info("facebook landing page and login");
        new FbLoginPage(driver).facebookLogin(fbMasterUser);

        //finalizing soft assertion
        softAssertion.assertAll();

    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        try {
            driver.close();
        } catch (Exception e) {
            log.info(" oops, it seems that driver instance have been already closed.");
        }
    }

    @Override
    public WebDriver getDriver() {

        return this.driver;
    }
}
