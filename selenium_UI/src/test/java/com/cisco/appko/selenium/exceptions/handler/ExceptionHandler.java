package com.cisco.appko.selenium.exceptions.handler;


import com.cisco.appko.selenium.exceptions.custom.BrowserConsoleException;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.SkipException;

public class ExceptionHandler {

    public final static Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    public static String handleException(Exception ex) {
        String errorMessage = "";
        /*if (ex instanceof MissingSmartXmlException) {
            errorMessage += "Player Initialization Failed";
        } else if (StringUtils.containsIgnoreCase(ex.getMessage(), "Request not found")) {
            errorMessage += ex.getMessage();
        } else if (ex instanceof UnityPlayerInitializationException) {
            errorMessage += "Player Module Problem: " + ex.getMessage();
        } else if (ex instanceof ParsingResponseException) {
            errorMessage += "Response Parsing Problem:  " + ex.getMessage();
        } else if (ex instanceof BrowserException) {
            errorMessage += "Browser Problem:  " + ex.getMessage();
        } else if (ex instanceof PageLoadException) {
            errorMessage += "Page Load Problem:  " + ex.getMessage();
        } else if (ex instanceof ModulePresenceException) {
            errorMessage += "Module Presence Problem:  " + ex.getMessage();
        } else if (ex instanceof TimeoutException) {
            errorMessage += "Page Load Problem";
        } else if (ex instanceof LoginException) {
            errorMessage += "Login Problem: " + ex.getMessage();
        } else if (ex instanceof RunTestException) {
            errorMessage += "General Run Test Problem:  " + ex.getMessage();  }*/
        if (ex instanceof StaleElementReferenceException) {
            errorMessage += "The Web Element no longer appears on the DOM of the page";
        } else if (ex instanceof NoSuchElementException) {
            errorMessage += "The Web Element with " + getLocatorName(ex.getMessage()) + " was not found in the DOM of the page";
        } else if (ex instanceof ElementNotVisibleException) {
            errorMessage += "Cannot interact with web element: it is not visible";
        } else if (ex instanceof org.openqa.selenium.WebDriverException) {
            if (StringUtils.containsIgnoreCase(ex.getMessage(), "Connection reset")
                    || StringUtils.containsIgnoreCase(ex.getMessage(), "terminated due to SO_TIMEOUT")) {
                errorMessage += "Browser is not available anymore";
            } else {
                errorMessage += "Browser error: " + handleUnknownError(ex, errorMessage);
            }
        } else if (ex instanceof SkipException) {
            throw new SkipException(ex.getMessage());
        } else {
            errorMessage = handleUnknownError(ex, errorMessage);

        }
        return errorMessage;
    }

    public static String handleException(Throwable t) {
        String errorMessage = "";
        if (t instanceof StaleElementReferenceException) {
            errorMessage += "The Web Element no longer appears on the DOM of the page";
        } else if (t instanceof NoSuchElementException) {
            errorMessage += "The Web Element with " + getLocatorName(t.getMessage()) + " was not found in the DOM of the page";
        } else if (t instanceof ElementNotVisibleException) {
            errorMessage += "Cannot interact with web element: it is not visible";
        } else if (t instanceof BrowserConsoleException) {
            errorMessage += t.getMessage();//"CRITICAL Browser console ERROR!";
        } else if (t instanceof org.openqa.selenium.WebDriverException) {
            if (StringUtils.containsIgnoreCase(t.getMessage(), "Connection reset")
                    || StringUtils.containsIgnoreCase(t.getMessage(), "terminated due to SO_TIMEOUT")) {
                errorMessage += "Browser is not available anymore";
            } else {
                errorMessage += "Browser error: " + handleUnknownError(new Exception(t), errorMessage);
            }
        } else if (t instanceof SkipException) {
            throw new SkipException(t.getMessage());
        } else {
            errorMessage = handleUnknownError(new Exception(t), errorMessage);

        }
        return errorMessage;
    }

    private static String handleUnknownError(Exception ex, String errorMessage) {
        logger.error("Unexpected exception happened: {} {} , {}", ex.getClass().getName(), ex.getMessage(), ex.getStackTrace());
        errorMessage += "Unexpected exception happened: " + ex.getClass().getName();
        errorMessage += "</br>" + " Please contact test developers to handle this situation";
        return errorMessage;
    }

    private static String getLocatorName(String errorMessage) {
        errorMessage = StringUtils.substringAfter(errorMessage, "Unable to locate element: {");
        errorMessage = StringUtils.substringBefore(errorMessage, "}");
        return StringUtils.substringAfter(errorMessage, ",");
    }

}