package com.example.tutorial.plugins;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class XrayApi {
    public static Logger log = LoggerFactory.getLogger(XrayApi.class);

    private static String encodeBase64String(String inputString) {
        return Base64.encodeBase64URLSafeString(inputString.getBytes());
    }

    private static boolean fileExists(String pathToFile) {
        return new File(pathToFile).exists();
    }

    public static void makeDirectory(String newDirString) {
        File file = new File(newDirString);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("\ninfo: Directory was created!\n");
            } else {
                System.out.println("\nerror: Failed to create directory!\n");
            }
        }
    }

    public static byte[] getIssuekeyfromJIRA(String JIRAissueKey, String pathToOutputFile){
        log.info("\n\n\t#+#+# XrayApi getIssuekeyfromJIRA: STATIC INITIALIZIER\n");

        String[] command = {"C:\\Users\\shripadg\\AppData\\Local\\Apps\\curl-7.46.0-win64\\curl-7.46.0-win64\\bin\\curl.exe", "-D-", "-X", "GET", "-H",
                "Authorization: Basic " + encodeBase64String("admin" + ":" + "admin"),
                "http://localhost:2990/jira" + "/rest/raven/1.0/export/test?keys=" + JIRAissueKey,"-o", pathToOutputFile
        };
        ProcessBuilder process = new ProcessBuilder(command);
        Process p = null;
       // log.info(" *********The Process**********  " + process.toString() +" "+ JIRAissueKey);
         byte[] fileContent = null;
        try {
            System.out.println("\ninfo: Starting process that accepts curl GET command\n");
            p = process.start();
           // log.info(" The Process is started accepting the Curl Get Commands " + p.toString() +" "+ JIRAissueKey);
            p.wait(5000);
            //p.getOutputStream();
            // IssueCreatedResolvedListener.makeDirectory(IMPORTED_SCENARIO_DIRECTORY + issue.getKey() + "/tests_imported_from_jira.feature" );
        } catch (Exception e) {
            //log.error("\nerror: Tried to execute curl command and output to a file, something went wrong\n");
            e.printStackTrace();
        }
        finally
        {
            if(p != null && p.isAlive())
                p.destroy();
        }
return fileContent;

    }
}

