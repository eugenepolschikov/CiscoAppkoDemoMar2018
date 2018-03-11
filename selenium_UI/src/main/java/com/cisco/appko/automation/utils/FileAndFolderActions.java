package com.cisco.appko.automation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class FileAndFolderActions {
    private static final Logger log = LoggerFactory.getLogger(FileAndFolderActions.class);

    public static File createFolderIfNotExists(String folderName) {
        File baseFolder = new File(Paths.get("").toAbsolutePath().toString());
        //   creation a destination folder in NOT exists
        File folderToCreate = new File(baseFolder + File.separator + folderName);
        if (!folderToCreate.exists()) {
            log.info("creating folder '" + folderName + "'");
            folderToCreate.mkdir();
        }

        return folderToCreate;

    }

    public static String createFileIfNotExist(String fileName) throws IOException {


        File txtToCreate = new File(createFolderIfNotExists("testusers").getAbsolutePath().toString()
                + File.separator + fileName);

        if (!txtToCreate.exists()) {
            log.info("creating file  '" + fileName + "'");
            txtToCreate.createNewFile();
        }


        return txtToCreate.getAbsolutePath();

    }

}
