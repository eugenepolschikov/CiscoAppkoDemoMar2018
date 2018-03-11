package com.cisco.appko.selenium.utility;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import static com.cisco.appko.automation.utils.FileAndFolderActions.createFolderIfNotExists;

/**
 * Created by eugene polschikov on 2017-07-17.
 */
public class MultiBrowserDriverInit {
    private final static Logger log = LoggerFactory.getLogger(MultiBrowserDriverInit.class);
    private static EnvironmentDataLoader environmentData = new EnvironmentDataLoader();
//    private static PlatformOsDetector systemToDefine = new PlatformOsDetector();
//    private static BrowserMobProxyServer proxy = null;

    /**
     * Browser hard-coded values
     */
    public static final String CHROME = "chrome";
    public static final String INTERNET_EXPLORER = "ie";


    /**
     * Method to create webdriver instance based on browser name
     *
     * @param browser   browser name
     * @param webDriver webdriver instance
     * @return customized webdriver instance
     */
    public static WebDriver getInstance(String browser, WebDriver webDriver) {
        if (CHROME.equals(browser)) {
            setChromeDriver();
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities = setChromeCapabilities(capabilities);
            webDriver = new ChromeDriver(capabilities);
        } else if (INTERNET_EXPLORER.equals(browser)) {
            //isSupportedPlatform(browser);
            setIEDriver();
            DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

            webDriver = new InternetExplorerDriver(capabilities);
        }
        return webDriver;
    }

    /**
     * Helper method to set ChromeDriver location into the right system property
     */
    private static void setChromeDriver() {
        String os = System.getProperty("os.name").toLowerCase().substring(0, 3);
        String chromeBinary = "src/main/custom-resources/drivers/chromedriver"
                + (os.equals("win") ? ".exe" : "");
        System.setProperty("webdriver.chrome.driver", chromeBinary);
    }

    /**
     * Setting IE driver binary path to system variable
     */
    private static void setIEDriver() {
        String ieBinary = "src/main/custom-resources/drivers/IEDriverServer.exe";
        System.setProperty("webdriver.ie.driver", ieBinary);
    }

    private static void isSupportedPlatform(String browser) {
        boolean is_supported = true;
        Platform current = Platform.getCurrent();
        if (INTERNET_EXPLORER.equals(browser)) {
            is_supported = Platform.WINDOWS.is(current);
        }
        assert is_supported : "Platform is not supported by " + browser.toUpperCase() + " browser";
    }

    /**
     * Setting chrome capabilities
     *
     * @param capabilities chrome capabilities
     * @return chrome cpabilities
     */
    private static DesiredCapabilities setChromeCapabilities(DesiredCapabilities capabilities) {
        ChromeOptions options = new ChromeOptions();
        String downloadFilepath = createFolderIfNotExists("reports").getAbsolutePath().toString();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.default_directory", downloadFilepath);
        prefs.put("download.prompt_for_download", "false");
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("profile.content_settings.pattern_pairs.*.multiple-automatic-downloads", 1);
        prefs.put("extensions.ui.developer_mode", true);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--test-type");
        options.addArguments("-incognito");
        capabilities.setCapability("nativeEvents", true);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setBrowserName("chrome");
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.WARNING);
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        return capabilities;
    }

}
