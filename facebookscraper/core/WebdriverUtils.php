<?php
/**
 * Created by PhpStorm.
 * User: eugene
 * Date: 2018-02-12
 * Time: 12:48
 */


namespace Core;

use Exception;
use Facebook\WebDriver\Exception\NoSuchElementException;
use Facebook\WebDriver\Exception\TimeOutException;
use Facebook\WebDriver\Remote\RemoteWebDriver;
use Facebook\WebDriver\WebDriverBy;
use Facebook\WebDriver\WebDriverElement;
use Facebook\WebDriver\WebDriverExpectedCondition;

class WebdriverUtils
{

    static public function isElementPresent(RemoteWebDriver $driver, $elementCssSelector)
    {
        try {
            $driver->findElement(WebDriverBy::cssSelector($elementCssSelector));
            return true;

        } catch (Exception $e) {
            return false;
        }
    }

    static public function fillInput(WebDriverElement $webElement, $text)
    {
        // clear input  field
        $webElement->clear();
        // enter text in input
        $webElement->sendKeys($text);
    }

    static public function waitForAllElementsByClassname(RemoteWebDriver $driver, $classname)
    {

        try {
            $driver->wait(WebdriverInit::getDefaultTimeout(), 250)->until(
                WebDriverExpectedCondition::presenceOfAllElementsLocatedBy(
                    WebDriverBy::className($classname) //'gsc-result'
                )
            );
        } catch (NoSuchElementException $e) {
            print_r("ERROR! No such element have been found!");
        } catch (TimeOutException $e) {
            print_r("ERROR! Timeout reached!");
        } catch (Exception $e) {
            print_r("ERROR! Please contact test developers for investigation!");
        }
    }

// wait for title of the page
//$driver->wait(10, 500)->until(
//    WebDriverExpectedCondition::titleIs('My Page')
//);


    static public function takeScreenshot(RemoteWebDriver $driver)
    {
        // screenshot of the page in current state
        $image = $driver->takeScreenshot();
        return $image;
    }

    static public function takeScreenshotAndSaveTofile(RemoteWebDriver $driver, $filenameFullpath)
    {
        // e.g $filenameFullpath "D:\\projects\\2018_02_php_fb_scraper\\_repo\\php-selenium\\facebookscraper\\image.png"
        $driver->takeScreenshot($filenameFullpath);

    }

    static public function isAlertPresent(RemoteWebDriver $driver)
    {
        try {
            $driver->wait()->until(
                WebDriverExpectedCondition::alertIsPresent(),
                'I am expecting an alert!'
            );
            return true;
        } catch (NoSuchElementException $e) {
            print_r("alert not found!");
            return false;

        } catch (TimeOutException $e) {
            print_r("alert not found!");
            return false;

        } catch (Exception $e) {
            print_r("alert not found!");
            return false;

        }
    }


}