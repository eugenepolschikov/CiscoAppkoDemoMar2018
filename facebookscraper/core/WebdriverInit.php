<?php
/**
 * Created by PhpStorm.
 * User: eugene polschikov
 * Date: 2018-02-14
 * Time: 22:17
 */


namespace Core;

use Facebook\WebDriver\Chrome\ChromeOptions;
use Facebook\WebDriver\Remote\DesiredCapabilities;
use Facebook\WebDriver\Remote\RemoteWebDriver;

class WebdriverInit
{

    static private $default_timeout = 10;

    /**
     * @return int
     */
    public static function getDefaultTimeout(): int
    {
        return self::$default_timeout;
    }


    static public function webdriverRemoteInit($hubIp)
    {
        $host = 'http://' . $hubIp . ':4444/wd/hub';
        $capabilities = DesiredCapabilities::chrome();

        // public spec: https://github.com/facebook/php-webdriver/wiki/ChromeOptions
        // handle Facebook notifications popup: http://take.ms/U2tjM   ;
        $options = new ChromeOptions();
        $options->addArguments(array("--disable-notifications"));
        $capabilities->setCapability(ChromeOptions::CAPABILITY, $options);

        $driver = RemoteWebDriver::create($host, $capabilities, 5000);
        return $driver;

    }


}