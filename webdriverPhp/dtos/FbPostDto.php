<?php
/**
 * Created by PhpStorm.
 * User: eugene polschikov
 * Date: 2018-02-19
 * Time: 22:21
 */

namespace Dtos;

class FbPostDto
{
    private $numberOfLikes;
    private $url;
    private $peopleWhoLiked = array();


    //array_push($array, $x);


    /**
     * FbPostDto constructor.
     */
    public function __construct()
    {
    }


    /**
     * @return mixed
     */
    public function getNumberOfLikes()
    {
        return $this->numberOfLikes;
    }

    /**
     * @param mixed $numberOfLikes
     */
    public function setNumberOfLikes($numberOfLikes): void
    {
        $this->numberOfLikes = $numberOfLikes;
    }

    /**
     * @return mixed
     */
    public function getUrl()
    {
        return $this->url;
    }

    /**
     * @param mixed $url
     */
    public function setUrl($url): void
    {
        $this->url = $url;
    }

    public function addFacebookPersonToLikesList(FbPersonDto $person)
    {
        array_push($this->peopleWhoLiked, $person);
    }


    public function printFacebookPostDataCollected()
    {
//        $trimmedLikesNum = rtrim(ltrim($this->numberOfLikes,"\'"),"\'");
//        $trimmedLikesNum = rtrim($trimmedLikesNum,"\'");
//        print_r("####ABCDE". var_export(rtrim(ltrim($this->numberOfLikes,"\'"),"\'"))."######");
//        print_r("####ABCDE". var_export( filter_var($this->numberOfLikes),FILTER_SANITIZE_STRING));
//        print_r("####ABCDE". var_export( filter_var($this->numberOfLikes),FILTER_SANITIZE_SPECIAL_CHARS));

        print_r("###########for the post: '" . $this->url . "' likes number: " . $this->numberOfLikes . "######");

        foreach($this->peopleWhoLiked as $singleObj)
        {
            print_r("#####".$singleObj." ############");
        }

    }


}