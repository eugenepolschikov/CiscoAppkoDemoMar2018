<?php
/**
 * Created by PhpStorm.
 * User: eugene polschikov
 * Date: 2018-02-21
 * Time: 15:23
 */

namespace Core;

use Facebook\WebDriver\Remote\RemoteWebDriver;
use Pages\FbAuthorizedLandingpage;
use Pages\FbLoginPage;

class FbNavigationHelper
{

    static public function reloginOrContinueWorkInSession(RemoteWebDriver $driver, BaseUser $user, $filename)
    {
        $fbLandingTitle =  'Facebook - Log In or Sign Up';

        // re-load the page
        $driver->navigate()->refresh();
        $actualTitle = $driver->getTitle();
        if (strpos($actualTitle, $fbLandingTitle) !== false) {

            print_r("###user landed to login FB page. Doing re-authz....###");
            $fbLanding = new FbLoginPage($driver);
            $fbAuthorized = $fbLanding->facebookLanding()->facebookLogin($user); //new BaseUser("egorenko.1970@bk.ru", "Trigger_61689")

            // Update cookies in provided filename
            CookiesOperations::saveCookiesInTxtFile($driver, $filename);
            return $fbAuthorized->getDriver();


        } else if (strpos($actualTitle, $fbLandingTitle) == false) {
            print_r("####user is authorized, please proceed navigation to facebook post####");
            $fbAuthorized = new FbAuthorizedLandingpage($driver);
            return $fbAuthorized->getDriver();
        } else {
            print_r("###unknown error! Please contact test developers for investigation!");
            return null;
        }

    }

}