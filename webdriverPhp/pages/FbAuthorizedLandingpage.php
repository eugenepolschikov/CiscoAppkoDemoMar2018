<?php
/**
 * Created by PhpStorm.
 * User: eugene polschikov
 * Date: 2018-02-17
 * Time: 15:19
 */

namespace Pages;


use Facebook\WebDriver\Remote\RemoteWebDriver;

class FbAuthorizedLandingpage
{
    var $driver = null;
    public function __construct(RemoteWebDriver $driver)
    {
        $this->driver = $driver;
    }

    /**
     * @return RemoteWebDriver|null
     */
    public function getDriver(): ?RemoteWebDriver
    {
        return $this->driver;
    }


    public function navigateToPostByUrl($postUrl){
        //$facebookBase = "https://www.facebook.com/";
        $this->driver->get($postUrl);

        $this->driver->wait(30, 500);
        // @TODO: add wait for some particular webelements
        return new FbPostCommonPage($this->driver);

    }



}