<?php
/**
 * Created by PhpStorm.
 * User: eugene polschikov
 * Date: 2018-02-19
 * Time: 20:33
 */

namespace Pages;


use Dtos\FbPersonDto;
use Dtos\FbPostDto;
use Facebook\WebDriver\Exception\NoSuchElementException;
use Facebook\WebDriver\Exception\TimeOutException;
use Facebook\WebDriver\Remote\RemoteWebDriver;
use Facebook\WebDriver\WebDriverBy;
use Facebook\WebDriver\WebDriverExpectedCondition;


class FbPostCommonPage
{
    // example of post: https://www.facebook.com/Smoda.es/posts/1106315606137275
    private $numberOfLikesForAllPostsInTheGroupCSS = "div[class*=\"UFILikeSentence\"] span[data-hover=\"tooltip\"]";
    //button to click to see reactions -> to open  likes
    private $reactionsButtonCSS = "div[class*=\"UFILikeSentence\"] div span>a[role=\"button\"]";
    //locators for the open pop-up
    private $listOfPeopleWhoReactedToThePost = "ul[id*=\"reaction_profile_browser\"] li div div a";

    //	private $listOfReactionsNumToClick = "//div[contains(@class,'UFILikeSentence')]//a//span[contains(text(),'1,2')]";

    var $dataCollected;

    /**
     * @return mixed
     */
    public function getDataCollected(): FbPostDto
    {
        return $this->dataCollected;
    }

    var $driver = null;

    /**
     * FbPostCommonPage constructor.
     * @param $driver
     */
    public function __construct(RemoteWebDriver $driver)
    {
        $this->driver = $driver;
        $this->dataCollected = new FbPostDto();
    }

    /**
     * @return $this
     */
    public function waitForNumberOfLikesAppearUnderThePost()
    {

        //visibilityOfAnyElementLocated
        try {
            $this->driver->wait(16, 250)->until(
                WebDriverExpectedCondition::visibilityOfElementLocated(WebDriverBy::cssSelector($this->numberOfLikesForAllPostsInTheGroupCSS))
            );
        } catch (NoSuchElementException $e) {
            print_r("oops, not such element found");
        } catch (TimeOutException $e) {
            print_r("ooops, timeout exception faced");
        } catch (\Exception $e) {
            print_r("oops, unknown exception, please contact test developerrs for investigation");
        }
        // setting current post URL in Facebook post  DTO object
        $this->dataCollected->setUrl($this->driver->getCurrentURL());


        $likesForDifferentPostss = $this->driver->findElements(WebDriverBy::cssSelector($this->numberOfLikesForAllPostsInTheGroupCSS));
        print_r("####number of posts in the current groups on just opened page is: " . count($likesForDifferentPostss) . "######");

        $this->dataCollected->setNumberOfLikes(($likesForDifferentPostss)[0]->getText());

//        print_r(var_export(($likesForDifferentPostss)[0]->getText()));


        return $this;
    }

    public function clickOnReactionsInThePostToOpenLikesPopup()
    {

        try {
            $this->driver->wait(25, 1000)->until(
                WebDriverExpectedCondition::visibilityOfElementLocated(WebDriverBy::cssSelector($this->reactionsButtonCSS))
            );
        } catch (NoSuchElementException $e) {
            print_r("oops, not such element found");
        } catch (TimeOutException $e) {
            print_r("ooops, timeout exception faced");
        } catch (\Exception $e) {
            print_r("oops, unknown exception, please contact test developerrs for investigation");
        }
        $likesForDifferentPostss = $this->driver->findElements(WebDriverBy::cssSelector($this->reactionsButtonCSS));
        print_r("###########clicking  on likes section to see the list of people who liked the post###########");
        ($likesForDifferentPostss)[0]->click();

        return $this;

    }

    public function getTheListOfPeopleWhoLikedThePost()
    {
        try {
            $this->driver->wait(25, 1000)->until(
                WebDriverExpectedCondition::visibilityOfElementLocated(WebDriverBy::cssSelector($this->listOfPeopleWhoReactedToThePost))
            );
        } catch (NoSuchElementException $e) {
            print_r("oops, not such element found");
        } catch (TimeOutException $e) {
            print_r("ooops, timeout exception faced");
        } catch (\Exception $e) {
            print_r("oops, unknown exception, please contact test developers for investigation");
        }
        $peopleList = $this->driver->findElements(WebDriverBy::cssSelector($this->listOfPeopleWhoReactedToThePost));

        foreach ($peopleList as $singleObj) {
            $person = new FbPersonDto();
            $person->setUrl($singleObj->getAttribute('href'));
            $person->setName($singleObj->getText());
            $this->dataCollected->addFacebookPersonToLikesList($person);
//            print_r("####" . $singleObj->getText() . " ############");
        }
        return $this;
    }


    public function navigateToAnotherPostByUrl($postUrl)
    {
        $this->driver->get($postUrl);
        $this->driver->wait(30, 500);
        return new FbPostCommonPage($this->driver);

    }


}