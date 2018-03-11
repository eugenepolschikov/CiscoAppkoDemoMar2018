package com.cisco.appko.selenium.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvironmentDataLoader {
    private final static Logger log = LoggerFactory.getLogger(EnvironmentDataLoader.class);


    protected String hubIP;
    protected String hubPort;


    protected String prodBaseUrl;




    // login-passwords
    protected String facebookLogin;
    protected String facebookPassword;


    public EnvironmentDataLoader() {
        try {
            commonPropertiesLoader();
            hubIpPropertiesLoader();
            environmentPropertiesLoader();

        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    // loader of master users for api testing
    public void masterusersDataLoader() throws IOException {
        Properties prop = new Properties();
        String propFileName = "masterusers.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        if (inputStream == null) {
            throw new FileNotFoundException("Property File '" + propFileName + "' not found in the classpath");
        }
        prop.load(inputStream);



        facebookLogin = prop.getProperty("fblogin");
        facebookPassword = prop.getProperty("fbpass");


        inputStream.close();
    }


    //    loader of common properties
    public void commonPropertiesLoader() throws IOException {
        Properties prop = new Properties();
        String propFileName = "common.environment.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        if (inputStream == null) {
            throw new FileNotFoundException("Property File '" + propFileName + "' not found in the classpath");
        }
        prop.load(inputStream);
        hubPort = prop.getProperty("hubPort");
        inputStream.close();
    }

    //  loader of environment-specific properties
    public void environmentPropertiesLoader() throws IOException {
        Properties prop = new Properties();
        String propFileName = "config.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        if (inputStream == null) {
            throw new FileNotFoundException("Property File '" + propFileName + "' not found in the classpath");
        }
        prop.load(inputStream);

        prodBaseUrl = prop.getProperty("facebookBaseUrl");


        inputStream.close();
    }

    public void hubIpPropertiesLoader() throws IOException {
        Properties prop = new Properties();
        String propFileName = "hubip.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        if (inputStream == null) {
            throw new FileNotFoundException("Property File '" + propFileName + "' not found in the classpath");
        }
        prop.load(inputStream);
        hubIP = prop.getProperty("hubIP");
        inputStream.close();
    }

    public String getHubIP() {
        return hubIP;
    }

    public String getHubPort() {
        return hubPort;
    }


    public String getProdBaseUrl() {
        return prodBaseUrl;
    }

    public String getFacebookLogin() {
        return facebookLogin;
    }

    public String getFacebookPassword() {
        return facebookPassword;
    }
}
