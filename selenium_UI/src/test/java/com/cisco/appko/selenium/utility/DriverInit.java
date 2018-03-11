package com.cisco.appko.selenium.utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

/**
 * Created by eugene polschikov on 12.12.2016.
 * init webdriver instance
 */
public class DriverInit {
    private final static Logger log = LoggerFactory.getLogger(DriverInit.class);
    private static EnvironmentDataLoader environmentData = new EnvironmentDataLoader();
    private static PlatformOsDetection systemToDefine = new PlatformOsDetection();


    public static WebDriver driverSetUp(WebDriver driver) throws MalformedURLException {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("-incognito");
        DesiredCapabilities capability = DesiredCapabilities.chrome();
        capability.setCapability(ChromeOptions.CAPABILITY, options);
        //System.setProperty("webdriver.chrome.driver", System.getProperty("user.home")+"/Documents/:Proj_folder:/chromedriver");
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        capability.setBrowserName("chrome");
        capability.setCapability("nativeEvents", true);
        LoggingPreferences logs = new LoggingPreferences();
        //Javascript console logs from the browser
        logs.enable(LogType.BROWSER, Level.WARNING);
        //Logs from the client side implementation of the WebDriver protocol (e.g. the Java bindings)
//        logs.enable(LogType.CLIENT, Level.ALL);
        //Logs from the internals of the driver (e.g. FirefoxDriver internals)
//        logs.enable(LogType.DRIVER, Level.ALL);
        //Logs relating to the performance characteristics of the page under test (e.g. resource load timings)
        logs.enable(LogType.PERFORMANCE, Level.ALL);
//        logs.enable(LogType.PROFILER, Level.ALL);
        //Logs from within the selenium server.
//        logs.enable(LogType.SERVER, Level.ALL);

        capability.setCapability(CapabilityType.LOGGING_PREFS, logs);
//        capability.setPlatform(org.openqa.selenium.Platform.WINDOWS);

        String webDriverURL = "http://" + environmentData.getHubIP() + ":" + environmentData.getHubPort() + "/wd/hub";
        log.info("creating driver instance on the URL :#### " + webDriverURL);
        driver = new RemoteWebDriver(new URL(webDriverURL), capability);

        driver.manage().window().maximize();


//        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
//        driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
//        https://github.com/SeleniumHQ/selenium/issues/1895: can not resize win on latest chromedriver
//        driver.manage().window().setSize(new Dimension(1920, 1080));
        return driver;

    }


}
