package com.cisco.appko.automation.webdriver;


import com.cisco.appko.automation.utils.CaptureSnapshot;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;


public class ElementsUtil {

    private static final Logger log = LoggerFactory.getLogger(ElementsUtil.class);

    private static long defaultTimeout = 30;

    private static long extendedTimeout = 35;
    private static long reducedTimeout = 5;

    public static void waitForPageLoaded(WebDriver driver) {
        ExpectedCondition<Boolean> expectation = driver1 -> ((JavascriptExecutor) driver1).executeScript("return document.readyState").equals("complete");
        Wait<WebDriver> wait = new WebDriverWait(driver, extendedTimeout);
        try {
            wait.until(expectation);
        } catch (Exception error) {
            log.error("Timeout waiting for Page Load Request to complete.", true);
        }

    }

    public static boolean waitForPageJQueryRendered(WebDriver webDriver) {
        WebDriverWait wait = new WebDriverWait(webDriver, defaultTimeout);
        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = driver -> {
            try {
                return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
            } catch (Exception e) {
                // no jQuery present
                return true;
            }
        };
        // wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState")
                .toString().equals("complete");
        return wait.until(jQueryLoad) && wait.until(jsLoad);
    }

    public static void waitForElementToBeClickable(WebDriver driver, By locator) {
        Wait<WebDriver> wait = new WebDriverWait(driver, defaultTimeout);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Throwable error) {
            log.error("Timeout waiting for element to be clickable", true);
        }
    }

    public static void waitForElementToBeClickable(WebDriver driver, WebElement element) {
        Wait<WebDriver> wait = new WebDriverWait(driver, defaultTimeout);
        try {
            log.info("waiting for element located by '" + getLocatorOfWebElement(element) + "'  becomes clickable");
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Throwable error) {
            log.error("Timeout waiting for element to be clickable", true);
        }
    }

    /**
     * wait for webelement to disappear on the page
     *
     * @param driver
     * @param locator
     */
    public static void waitForElementNotPresentOnPage(WebDriver driver, By locator) {
        Wait<WebDriver> wait = new WebDriverWait(driver, defaultTimeout);
        try {
            wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(locator)));
        } catch (Throwable error) {
            log.error("Waiting for element located by '" + locator.toString() + "' to disappear on the page "
                    + driver.getCurrentUrl().toString(), true);
        }
    }

    public static void waitForElementWithTextNotVisibleOnPage(WebDriver driver, By locator, String text) {
        Wait<WebDriver> wait = new WebDriverWait(driver, defaultTimeout);
        try {
            wait.until(ExpectedConditions.invisibilityOfElementWithText(locator, text));
        } catch (Throwable error) {
            log.error("Timeout waiting for element to get invisible with text: " + text, true);
        }
    }

    public static WebElement fluentWait(WebDriver driver, final By locator) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(defaultTimeout, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement foo = wait.until(
                (Function<WebDriver, WebElement>) driver1 -> driver1.findElement(locator)
        );
        return foo;
    }

    public static WebElement fluentWait(WebDriver driver, final By locator, long customTimeout) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(customTimeout, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement foo = wait.until(
                (Function<WebDriver, WebElement>) driver1 -> driver1.findElement(locator)
        );
        return foo;
    }

    public static WebElement fluentWait(WebDriver driver, final WebElement element) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(defaultTimeout, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement foo = wait.until(
                (Function<WebDriver, WebElement>) driver1 -> element
        );
        return foo;
    }


    public static void waitForElementGetsVisible(WebDriver driver, WebElement element) {
        new WebDriverWait(driver, extendedTimeout).until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForListOfElementsToBeVisibleOnThePage(WebDriver driver, By selector) {
        List<WebElement> elementList = null;

        boolean listNotEmpty = false;
        int attempts = 0;
        while (attempts < 3) {
            try {
                elementList = driver.findElements(selector);
                listNotEmpty = elementList.size() > 0;
                sleepUninterruptibly(1000, TimeUnit.MILLISECONDS);
                break;
            } catch (Exception e) {
            }
            attempts++;
        }
        Assert.assertTrue((listNotEmpty && elementList != null), "oops,  was unable to find elements on the page " + driver.getCurrentUrl() +
                " By selector:" + selector);

        log.info("waiting for every element in the list to become visible");
        for (WebElement iterator : elementList) {
            waitForElementGetsVisible(driver, iterator);
        }

    }

    /**
     * handling stale element exception
     *
     * @param driver
     * @param by
     * @return
     */
    public static boolean clickWithStaleRefHandle(WebDriver driver, By by) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 2) {
            try {
                driver.findElement(by).click();
                result = true;
                break;
            } catch (StaleElementReferenceException e) {
            }
            attempts++;
        }
        return result;
    }

    public static boolean clickWithExceptionHandle(WebDriver driver, WebElement element) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 3) {
            try {
                element.click();
                result = true;
                break;
            } catch (Exception e) {
            }
            attempts++;
        }
        return result;
    }

    public static boolean isElementPresent(WebDriver driver, By locatorKey) {
        WebDriver.Timeouts timeouts = driver.manage().timeouts();

        timeouts.implicitlyWait(reducedTimeout, TimeUnit.MILLISECONDS);
        try {
            //driver.findElement(locatorKey);
            fluentWait(driver, locatorKey, reducedTimeout);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            timeouts.implicitlyWait(reducedTimeout, TimeUnit.SECONDS);
        }
    }


    public static boolean isElementDisplayedAndEnabled(WebElement element) {
        return (element.isDisplayed() && element.isEnabled());
    }

    public static boolean isElementDisplayed(WebElement element) {
        return element.isDisplayed();
    }

    public static void checkElementPresent(WebDriver driver, String cssLocator, String alertMsg) {
        fluentWait(driver, By.cssSelector(cssLocator));

        if (!isElementPresent(driver, By.cssSelector(cssLocator))) {
            Assert.fail(alertMsg);
        }
    }

    public static void actionBuilderFocusInputEnterValue(WebDriver driver, String cssSelector, String valueToEnter) {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.cssSelector(cssSelector)));
        actions.click();
        actions.sendKeys(valueToEnter);
        actions.build().perform();
    }

    public static WebDriver actionBuilderMouserOverAndCLick(WebDriver driver, WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.click(element);
        actions.build().perform();
        waitForPageLoaded(driver);

        return driver;

    }

    public static WebDriver moveToElementActionbuilder(WebDriver driver, WebElement element) {
        log.info("moving to set the focus on webelement " + element);
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.build().perform();
        sleepUninterruptibly(500, TimeUnit.MILLISECONDS);

        return driver;

    }


    public static void waitForFrameToBeAvailableAndSwitchToIt(WebDriver driver, String frameName) {
        new WebDriverWait(driver, defaultTimeout)
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
    }

    public static void waitForFrameToBeAvailableAndSwitchToIt(WebDriver driver, By locator) {
        new WebDriverWait(driver, defaultTimeout)
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

    public static void waitForFrameToBeAvailableAndSwitchToIt(WebDriver driver, WebElement frameWebelement) {
        new WebDriverWait(driver, defaultTimeout)
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameWebelement));
    }


    public static void switchToReportWindow(WebDriver driver) {
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
        }
    }


    private static String getLocatorOfWebElement(WebElement element) {
        String elemParsed = element.toString();
        String elemLocatorParsed = elemParsed.substring(elemParsed.indexOf(">") + 2, elemParsed.length() - 1);
        return elemLocatorParsed;
    }


    /**
     * filing in   Select webelement in a random way
     *
     * @param myDriver
     * @param select
     * @param highestBoundary
     * @return
     */
    public static WebDriver selectRandomizer(WebDriver myDriver, WebElement select, int highestBoundary) {

        Select realSelect = new Select(select);
        int randomIndex = ThreadLocalRandom.current().nextInt(1, highestBoundary);
        realSelect.selectByIndex(randomIndex);

        return myDriver;
    }


    /**
     * random selection of the option in dropdown List
     *
     * @param driver
     * @param button
     * @param dynamicListOfValues
     * @return
     */
    @Step("selecting DDL option in a random way (NOTE: with NO DDL options validation)")
    public static WebDriver ulListRandomizer(WebDriver driver, WebElement button, List<WebElement> dynamicListOfValues) {

        int attempts = 0;
        while (attempts < 3) {
            try {
                button.click();
                sleepUninterruptibly(1000, TimeUnit.MILLISECONDS);

                if (dynamicListOfValues.size() > 0)
                    log.info("list of DDL options is not empty");
                waitForElementGetsVisible(driver, dynamicListOfValues.get(dynamicListOfValues.size() - 1));


                break;
            } catch (Exception e) {
            }
            attempts++;
        }

/*
        try {
            waitForElementGetsVisible(driver, dynamicListOfValues.get(dynamicListOfValues.size() - 1));
        } catch (Exception e) {
            log.info("ooops, something went wrong when waiting for DDL options get visible; clicking on the button again");
            button.click();
            sleepUninterruptibly(1500, TimeUnit.MILLISECONDS);
        }
*/
        CaptureSnapshot.captureSnapshotForAllure(driver);
        dynamicListOfValues.get(ThreadLocalRandom.current().nextInt(1, dynamicListOfValues.size())).click();
        waitForPageLoaded(driver);
        sleepUninterruptibly(500, TimeUnit.MILLISECONDS);
        return driver;
    }

    @Step("selecting DDL option in a random way (NOTE: with NO DDL options validation)")
    public static void ulListRandomizer(WebDriver driver, WebElement button, List<WebElement> dynamicListOfValues, String
            textToSelect) {
        /*button.click();
        try {
            waitForElementGetsVisible(driver, dynamicListOfValues.get(dynamicListOfValues.size() - 1));
        } catch (Exception e) {
            log.info("ooops, something went wrong when waiting for DDL options get visible; clicking on the button again");
            button.click();
            sleepUninterruptibly(1500, TimeUnit.MILLISECONDS);
        }*/

        int attempts = 0;
        while (attempts < 3) {
            try {
                button.click();
                sleepUninterruptibly(1000, TimeUnit.MILLISECONDS);

                if (dynamicListOfValues.size() > 0)
                    log.info("list of DDL options is not empty");
                waitForElementGetsVisible(driver, dynamicListOfValues.get(dynamicListOfValues.size() - 1));


                break;
            } catch (Exception e) {
            }
            attempts++;
        }

        CaptureSnapshot.captureSnapshotForAllure(driver);
        Function<WebElement, String> getText = (WebElement w) -> w.getText().trim();


        int indexToClick = Lists.transform(dynamicListOfValues, getText).indexOf(textToSelect);
        if (indexToClick < 0) {
            log.warn("ooops, there is no '" + textToSelect + "' element among DDL options: " + Lists.transform(dynamicListOfValues, getText).toString());

            dynamicListOfValues.get(ThreadLocalRandom.current().nextInt(1, dynamicListOfValues.size())).click();

        } else {
            dynamicListOfValues.get(indexToClick).click();

        }

        waitForPageLoaded(driver);
        sleepUninterruptibly(500, TimeUnit.MILLISECONDS);


    }


    /**
     * clicking on button to make DDL  options to appear ,
     * verifies that all expected options appeared,
     * and picks up a DDL option in a random way
     *
     * @param driver
     * @param button
     * @param dynamicListOfValues
     * @param expectedOptions
     * @return
     */
    @Step("selecting DDL option in a random way with validation of all DDL options")
    public static void ddlOptionsVerifyAndRandomPickup(SoftAssert softAssertion, WebDriver driver, WebElement button,
                                                       List<WebElement> dynamicListOfValues, String[] expectedOptions) {

        log.info("clicking on button to make DDL options appear");
        button.click();

        log.info("waiting for the first element of DDL to appear");
        waitForElementGetsVisible(driver, dynamicListOfValues.get(dynamicListOfValues.size() - 1));
        CaptureSnapshot.captureSnapshotForAllure(driver);

        Function<WebElement, String> getElemsText = (WebElement w) -> w.getText().trim();
        Object[] textExtracted = Lists.transform(dynamicListOfValues, getElemsText).toArray();
        log.info("checking that ACTUAL DDLs options " + Arrays.toString(textExtracted) + "  " +
                "are the same as expected ones " + Arrays.toString(expectedOptions));
        softAssertion.assertTrue(Arrays.deepEquals(textExtracted, expectedOptions), "Page: " +
                driver.getCurrentUrl() + "\nooops, " +
                "for some reasons expected DDL optinos:" + Arrays.toString(expectedOptions) +
                " and actual " + Arrays.toString(textExtracted) + " are the different, " +
                "please contact test developers for investigation");

        dynamicListOfValues.get(ThreadLocalRandom.current().nextInt(0, dynamicListOfValues.size())).click();

        waitForPageLoaded(driver);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    }


    public static void waitTillPageTitleChanges(WebDriver driver, String titlePattern) {
        WebDriverWait wait = new WebDriverWait(driver, extendedTimeout);
        wait.until(ExpectedConditions.urlContains(titlePattern));
    }


    public static void waitUntilRedirectHappensFromParticularPage(WebDriver driver, String pageSubTitle) {

        WebDriverWait wait = new WebDriverWait(driver, extendedTimeout);
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains(pageSubTitle)));
    }

}