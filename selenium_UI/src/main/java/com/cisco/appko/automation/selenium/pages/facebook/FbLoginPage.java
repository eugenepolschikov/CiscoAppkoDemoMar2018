package com.cisco.appko.automation.selenium.pages.facebook;

import com.cisco.appko.automation.selenium.pages.core.Page;
import com.cisco.appko.automation.user.BaseUser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by eugene polschikov on 2018-03-11.
 */
public class FbLoginPage extends Page {
    public FbLoginPage(WebDriver driver) {
        super(driver);
    }

    private final static String EMAIL_INPUT_CSS = "#email";
    private final static String PASSWORD_INPUT_CSS = "#pass";
    private final static String LOGIN_BUTTON_CSS = "#loginbutton";

    @FindBy(css = EMAIL_INPUT_CSS)
    private WebElement emailInput;

    @FindBy(css = PASSWORD_INPUT_CSS)
    private WebElement passwordInput;

    @FindBy(css = LOGIN_BUTTON_CSS)
    private WebElement loginButton;

    @Step("login into facebook")
    public FbAuthorizedUserPage facebookLogin(BaseUser user) {
        log.info("entering login");
        emailInput.clear();
        emailInput.sendKeys(user.getEmail());

        log.info("entering password");
        passwordInput.clear();
        passwordInput.sendKeys(user.getPassword());

        log.info("pressing submit/sign-in button");
        loginButton.click();

        return new FbAuthorizedUserPage(driver);
    }


}
