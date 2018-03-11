package com.cisco.appko.automation.selenium.pages.core;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.concurrent.TimeUnit.SECONDS;

public abstract class Page {


    private final long DEFAULT_TIMEOUT = 60;
    private final long EXPLICIT_WAIT_TIMEOUT = 2;

    protected final long IMPLICIT_DEFAULT_TIMEOUT = 5;

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Logger log;


    public Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, DEFAULT_TIMEOUT);
        this.log = LoggerFactory.getLogger(this.getClass());
        driver.manage().timeouts().implicitlyWait(EXPLICIT_WAIT_TIMEOUT, SECONDS);
        PageFactory.initElements(driver, this);


    }

    protected void switchToDefaultContent() {
        log.info("swithching back to default content , i.e moving out from frame");
        driver.switchTo().defaultContent();
    }


}
