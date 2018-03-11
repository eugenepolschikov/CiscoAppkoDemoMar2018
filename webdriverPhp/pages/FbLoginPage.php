<?php

namespace Pages;

use core\BaseUser;
use Core\WebdriverInit;
use Core\WebdriverUtils;
use Facebook\WebDriver\Exception\NoSuchElementException;
use Facebook\WebDriver\Exception\TimeOutException;
use Facebook\WebDriver\Remote\RemoteWebDriver;
use Facebook\WebDriver\WebDriverBy;
use Facebook\WebDriver\WebDriverExpectedCondition;

class FbLoginPage
{
    private $emailInputCss = "#email";
    private $passwordInputCss = "#pass";
    private $loginButtonCss = "#loginbutton";


    /**
     * @return string
     */
    public function getEmailInputCss(): string
    {
        return $this->emailInputCss;
    }


    /**
     * @return RemoteWebDriver|null
     */
    public function getDriver(): ?RemoteWebDriver
    {
        return $this->driver;
    }


    var $driver = null;

    /**
     * FacebookLoginPage constructor.
     * @param $driver
     */
    public function __construct(RemoteWebDriver $driver)
    {
        $this->driver = $driver;
    }

    public function facebookLanding()
    {

        // initial navigation to facebook
        $this->driver->get('http://www.facebook.com');
//        print_r($this->driver->manage()->getCookies());
        print_r("###wait until page title contains 'Facebook'###");
        // Wait for at most 10s and retry every 500ms if it the title is not correct.
        try {
            $this->driver->wait(WebdriverInit::getDefaultTimeout(), 500)->until(
                WebDriverExpectedCondition::titleContains("Facebook")
            );
        } catch (NoSuchElementException $e) {
            print_r("ERROR! No such element have been found!");
        } catch (TimeOutException $e) {
            print_r("ERROR! Timeout reached!");
        } catch (\Exception $e) {
            print_r("ERROR! Please contact test developers for investigation!");
        }


        return $this;
    }

    /**
     * @param BaseUser $user
     * @return mixed
     */
    public function facebookLogin(BaseUser $user)
    {

        $emailInput = $this->driver->findElement(WebDriverBy::cssSelector($this->emailInputCss));
        WebdriverUtils::fillInput($emailInput, $user->getEmail());
//password input init
        $passwordInput = $this->driver->findElement(WebDriverBy::cssSelector($this->passwordInputCss));

        WebdriverUtils::fillInput($passwordInput, $user->getPassword());

//submit button init0
        $submitButton = $this->driver->findElement(WebDriverBy::cssSelector($this->loginButtonCss));
        $submitButton->click();

        $this->driver->wait(2500);


        echo "response on element presence goes below";
        print_r(var_export(WebdriverUtils::isElementPresent($this->driver, $this->loginButtonCss)));

        return new FbAuthorizedLandingpage($this->driver);

    }

}


//exit;

//restore:

//var_export($driver);
//var_export($driver->manage()->getCookies());
//file_put_contents($file, serialize($driver));



