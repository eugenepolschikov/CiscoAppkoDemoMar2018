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

        BaseUser fbMasterUser = new BaseUser(configs.getFacebookLogin(), configs.getFacebookPassword());

        SoftAssert softAssertion = new SoftAssert();
        log.info("facebook landing page and login");


        new FbLoginPage(driver).facebookLogin(fbMasterUser);
//                            .registerToday()
//                            .hsbcRegisterUser()
//                            .fillInHsbcRegistrationForm(stubForNewUser,
//                                    configs.getEnvironmentTagForNewTestUser());

//            log.info("HSBC sign-up: registered new user: " + justRegisteredUser.toString());
//            dumpUserToCsv(justRegisteredUser, "HsbcTestUsersDev.csv");
/*
            sleepUninterruptibly(5000, TimeUnit.MILLISECONDS);
            driver.get(parseConfirmationUrl(emailBox));
            waitForPageLoaded(driver);
            //wait till CONFIRMATION link popup completely loaded
            sleepUninterruptibly(10000, TimeUnit.MILLISECONDS);
            justRegisteredUser.setStatus(SignUpUserStatuses.REGISTERED_ENTERED_CONFIRMATION_LINK.getSignUpStatus());
            driver = driverRestart(driver, baseUrl);

            log.info("HSBC sign-up: login into application for the first time to finish sign-up and enter" +
                    "user details");

            new HbscLandingPage(driver)
                    .signIn()
                    .doFirstLoginAfterRegistration(justRegisteredUser)
                    .connectToLinkedInClick()
                    .linkedinAuthOnHsbcFirstLogin(linkedin, emailBox)
                    .fillUserProfileDetails(justRegisteredUser)
                    .fillOccupationCompanyPosition(softAssertion)
                    .fillEmailToConfirm()
                    .rateSignupAndSubmit()
                    .waitHsbcOnCompleteMessage();
            justRegisteredUser
                    .setStatus(SignUpUserStatuses.REGISTERED_CONFIRMED_EMAIL_ENTERED_USERDETAILS.getSignUpStatus());
            log.info("HSBC sign-up: filled in USER details; sign-up COMPLETED for the user " +
                    justRegisteredUser.toString());
            dumpUserToCsv(justRegisteredUser, "HsbcTestUsersDev.csv");
*/

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
