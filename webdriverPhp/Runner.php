<?php
/**
 * Created by PhpStorm.
 * User: eugene polschikov
 * Date: 2018-02-15
 * Time: 13:33
 */

namespace Facebook\WebDriver;

//require_once('vendor/autoload.php');
use Core\BaseUser;
use Core\CookiesOperations;
use Core\FbNavigationHelper;
use Core\WebdriverInit;
use Pages\FbAuthorizedLandingpage;
use Pages\FbLoginPage;

require __DIR__ . '/vendor/autoload.php';

$driver = WebdriverInit::webdriverRemoteInit('localhost');

$fbLanding = (new FbLoginPage($driver))->facebookLanding();
//CookiesOperations::loadCookiesFromTxtFile($driver, 'cookiesDump.txt');
CookiesOperations::loadCookiesFromTxtFile($driver, 'cookiesDump.txt');

FbNavigationHelper::reloginOrContinueWorkInSession($driver, new BaseUser("egorenko.1970@bk.ru", "Trigger_61689"), 'cookiesDump.txt');
// wait until the page completely loads!
$driver->wait(30, 250);


$fbPostPage = (new FbAuthorizedLandingpage($driver))->navigateToPostByUrl("https://www.facebook.com/Smoda.es/posts/1106315606137275")
    ->waitForNumberOfLikesAppearUnderThePost();
$fbPostPage ->clickOnReactionsInThePostToOpenLikesPopup() -> getTheListOfPeopleWhoLikedThePost();
$fbPostPage->getDataCollected()->printFacebookPostDataCollected();

$fbPostPage = $fbPostPage->navigateToAnotherPostByUrl("https://www.facebook.com/groups/freelancers.belarus/permalink/1682316221852440/")
    ->waitForNumberOfLikesAppearUnderThePost();
$fbPostPage ->clickOnReactionsInThePostToOpenLikesPopup() -> getTheListOfPeopleWhoLikedThePost();
$fbPostPage->getDataCollected()->printFacebookPostDataCollected();


//close the page
$driver->close();
