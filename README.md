### Pre-requisites. 

========= Begin.

1) [Using Docker] execute in console 'docker run -d -p 4444:4444 --name selenium-hub selenium/hub:latest' ,  http://take.ms/453Wv

1) [Using Standard Approach]  Open 'selenium_grid'  folder, execute launchHub batch file, http://take.ms/fDUdd

2) [Using Docker] execute in console 'docker run -d --link selenium-hub:hub selenium/node-chrome:latest' , http://take.ms/ktaaf

2) [Using Standard Approach] Open 'selenium_grid'  folder, execute launchNode batch file, http://take.ms/batii  

3) make sure  selenium grid is UP & running locally by entering 'localhost:4444/grid/console'
in the browser URL:  http://take.ms/5WCet8  

========== End


# php: selenium webdriver facebook robot. It does the following actions: navigates to post, extracts likes(reactions) number for particular post and extracts people list who liked this post. And prints results in console.

This example is implemented using php client for webdriver: https://github.com/facebook/php-webdriver . 
Composer is used as dependency manager in this example. 


### Execution

4)  open 'php-selenium\facebookscraper'  , folder , run    'php runner.php' => http://take.ms/pyfCD 


=========================================================================


# java: 
