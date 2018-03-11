package com.cisco.appko.selenium.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by  eugene polschikov on 12.12.2016.
 */
public class PlatformOsDetection {
    private final static Logger log = LoggerFactory.getLogger(PlatformOsDetection.class);


    //     PLATFORM defining methods
    private String OS = System.getProperty("os.name").toLowerCase();

    public org.openqa.selenium.Platform getSystemOS() throws IOException {
        org.openqa.selenium.Platform platformSetUp;
        // This Method will get System OS --- To setUp variables based on the OS
        if (_isWindows()) {
            log.info("Running Test on Windows: " + OS);
            platformSetUp = org.openqa.selenium.Platform.WINDOWS;
        } else if (_isMac()) {
            log.info("Running Test on Mac: " + OS);
            platformSetUp = org.openqa.selenium.Platform.MAC;
        } else {
            log.info("Your OS is not support!! Use default Windows Platform" + OS);
            platformSetUp = org.openqa.selenium.Platform.WINDOWS;
        }

        return platformSetUp;
    }


    public boolean _isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    public boolean _isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    public boolean _isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
    }

    public boolean isSolaris() {
        return (OS.indexOf("sunos") >= 0);
    }

}
