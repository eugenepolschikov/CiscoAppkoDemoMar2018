package com.cisco.appko.selenium.testcases;


import com.cisco.appko.selenium.utility.EnvironmentDataLoader;
import com.cisco.appko.automation.user.BaseUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * base class for functional test suites execution
 */
public abstract class TestSuitesBase {
    private final static Logger log = LoggerFactory.getLogger(TestSuitesBase.class);

    protected EnvironmentDataLoader configs = new EnvironmentDataLoader();

    protected void masterUsersDataInit() throws IOException {
        configs.masterusersDataLoader();
    }


    protected String urlShortifier(String testBaseUrl) throws MalformedURLException {
        URL fullUrl = new URL(testBaseUrl);
        return fullUrl.getProtocol() + "://" + fullUrl.getHost();
    }


}
