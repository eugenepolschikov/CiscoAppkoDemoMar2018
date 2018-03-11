<?php
/**
 * Created by PhpStorm.
 * User: eugene polschikov
 * Date: 2018-02-25
 * Time: 15:03
 */

namespace Dtos;

class FbPersonDto{

    private $name;
    private $url;

    /**
     * @return mixed
     */
    public function getName()
    {
        return $this->name;
    }

    /**
     * @param mixed $name
     */
    public function setName($name): void
    {
        $this->name = $name;
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

    public function __toString()
    {
        return "Facebook profile. Name: ".$this->name."; url:".$this->url;
    }


}