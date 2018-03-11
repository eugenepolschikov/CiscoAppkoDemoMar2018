<?php
/**
 * Created by PhpStorm.
 * User: eugene polschikov
 * Date: 2018-02-15
 * Time: 12:49
 */

namespace Core;

use Facebook\WebDriver\Remote\RemoteWebDriver;

class CookiesOperations
{
    /**
     * @param $driver
     * @param $fileName
     */
    static public function saveCookiesInTxtFile(RemoteWebDriver $driver, $fileName): void
    {
        $cookiesAfterLogin = $driver->manage()->getCookies();
        print_r($cookiesAfterLogin);
        // re-write with fresh cookies
        file_put_contents($fileName, serialize($cookiesAfterLogin));
        print_r("###cookies have been successfully saved###");
    }

    /**
     * @param $driver
     * @param $fileName
     */
    static public function loadCookiesFromTxtFile(RemoteWebDriver $driver, $fileName): void
    {
        try {
            print_r("###clearing webdriver cookies###");
            $driver->manage()->deleteAllCookies();

            print_r("###loading cookies from .txt for newly created driver instance!###");
            $cookiesLoaded = unserialize(file_get_contents($fileName));
            if (!empty($cookiesLoaded)) {

                foreach ($cookiesLoaded as $singleCookie) {
                    print_r("###adding the cookie object to driver instance: " . var_export($singleCookie) . "###");
                    $driver->manage()->addCookie($singleCookie);
                }
                print_r("###successfully loaded ALL cookies###");

            } else {
                print_r("ooops, file with cookies look to be empty");
            }
        } catch (Exception $e) {
            print_r("####  ooops. Something went wrong during cookies load  and substitute####");

        }
    }


}

// @TODO: analyze cookie expiry (per time -> to milliseconds converter)
// http://www.ruddwire.com/handy-code/date-to-millisecond-calculators/#.WoSPDIPFKpo

// seconds-> to date conversion
// echo date('Y/m/d H:i:s', $numberofsecs);
