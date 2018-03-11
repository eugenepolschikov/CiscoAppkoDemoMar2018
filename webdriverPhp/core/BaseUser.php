<?php
/**
 * Created by PhpStorm.
 * User: eugene polschikov
 * Date: 2018-02-11
 * Time: 19:37
 */

//require_once('../vendor/autoload.php');
namespace Core;
class BaseUser
{
    private $email;
    private $password;

    /**
     * BaseUser constructor.
     * @param $email
     * @param $password
     */
    public function __construct($email, $password)
    {
        $this->email = $email;
        $this->password = $password;
    }

    /**
     * @return mixed
     */
    public function getEmail()
    {
        return $this->email;
    }

    /**
     * @return mixed
     */
    public function getPassword()
    {
        return $this->password;
    }


}