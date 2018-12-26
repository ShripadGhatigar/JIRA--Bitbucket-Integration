package com.example.tutorial.plugins;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class XrayApi {
    public static  String IMPORTED_SCENARIO_DIRECTORY = System.getProperty("importedScenarioDirectory","target/TestsImportedFromJira");

    public static Logger log = LoggerFactory.getLogger(XrayApi.class);

    private static String encodeBase64String(String inputString) {
        return Base64.encodeBase64URLSafeString(inputString.getBytes());
    }


  private static boolean fileExists(String pathToFile) {
      return new File(pathToFile).exists();
  }

    public static String getIssuekeyfromJIRA(String JIRAissueKey) throws IOException{
          String[] command = {"C:\\Users\\shripadg\\AppData\\Local\\Apps\\curl-7.46.0-win64\\curl-7.46.0-win64\\bin\\curl.exe", "-D-", "-X", "GET", "-H",
                "Authorization: Basic " + encodeBase64String("admin" + ":" + "admin"),
                "http://localhost:2990/jira" + "/rest/raven/1.0/export/test?keys=" +JIRAissueKey//,"-o", pathToOutputFile
        };
        String pathToOutputFile = "";
        ProcessBuilder process = new ProcessBuilder(command);
        log.info(" *********The Process********** {} ", process);
       //  byte[] fileContent = null;
        Runtime runtime = Runtime.getRuntime();
        Process p;
        try {
            System.out.println("\ninfo: Starting process that accepts curl GET command\n");
           // p = process.start();
            p = runtime.exec(command);
            //p.wait(5000);
            try {
                p.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            InputStream in = p.getInputStream();
            parseHTTPHeaders(in);
            BufferedReader is = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = is.readLine()) != null)
            {
                sb.append("\n");
                pathToOutputFile=sb.append(line).toString();
            }
            log.info("\n the Response Body is {}", sb);


            log.info("\n the process Outputstream is {}", pathToOutputFile);
        } catch (IOException e) {
            System.out.print("\nerror: Tried to execute curl command and output to a file, something went wrong\n");
            e.printStackTrace();
        }

        return pathToOutputFile;
    }

    public static Map<String, String> parseHTTPHeaders(InputStream inputStream)
            throws IOException {
        int charRead;
        StringBuffer sb = new StringBuffer();
        while (true) {
            sb.append((char) (charRead = inputStream.read()));
            if ((char) charRead == '\r') {
                sb.append((char) inputStream.read());
                charRead = inputStream.read();
                if (charRead == '\r') {
                    sb.append((char) inputStream.read());
                    break;
                } else {
                    sb.append((char) charRead);
                }
            }
        }

        String[] headersArray = sb.toString().split("\r\n");
        Map<String, String> headers = new HashMap<String,String>();
        for (int i = 1; i < headersArray.length - 1; i++) {
            headers.put(headersArray[i].split(": ")[0],
                    headersArray[i].split(": ")[1]);
        }

        return headers;
    }

}

