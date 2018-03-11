package com.cisco.appko.automation.utils;


import au.com.bytecode.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CsvWriter {
    private static final Logger log = LoggerFactory.getLogger(CsvWriter.class);

    public static void dumpUserToCSV(String filenameCsv, String myUser) throws IOException {

        CSVWriter writer = new CSVWriter(new FileWriter(filenameCsv, true));

        String[] userFields = myUser.toString().split(",");

        writer.writeNext(userFields);

        writer.close();
        log.info("user have been successfully saved to '" + filenameCsv + "' CSV file!");
    }


    private String csvDumpFilename;

    public CsvWriter(String myFileName) {
        this.csvDumpFilename = myFileName;
    }

    public void csvWriter(ResultSet resultSet) throws IOException, SQLException {

        CSVWriter writer = new CSVWriter(new FileWriter(this.csvDumpFilename, true));
        writer.writeAll(resultSet, true); //writer is instance of CSVWriter
        writer.close();
    }


    public void setCsvDumpFilename(String csvDumpFilename) {
        this.csvDumpFilename = csvDumpFilename;
    }
}
