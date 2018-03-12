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

1)  open 'CiscoAppkoDemoMar2018\webdriverPhp'  folder , run    'php runner.php' => http://take.ms/DuIwK 


=========================================================================


# java: selenium webdriver login test. It does the following actions: it opens facebook login page. Checks that login input, password input and sign-in buttons are present on the page.

This example is implemented using java based project using: maven as dependency manager for the project, testNG- as test runner, 
allure- for test reporting. 

### Execution: 

1) open 'CiscoAppkoDemoMar2018\selenium_UI' folder,  open console window (Win+R -> cmd) in this folder  ,
execute the following command :   'mvn clean test -D "testng.suite.name=FunctionalUiDebug"  -P prod,masterusersprod,hubipseleniumtests'   ,http://take.ms/eba8U ,  http://take.ms/IFl28
2) after the execution open 'selenium_UI\target' folder , and execute the following command:  'allure serve'  , http://take.ms/v8wEi  

