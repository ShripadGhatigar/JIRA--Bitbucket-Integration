package com.example.tutorial.plugins;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import org.json.simple.JSONArray;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.client.ClientProtocolException;

public class JiraRest {
    public static Object obj;
   /* public static void main(String[] args) throws ClientProtocolException, IOException {


    String value = getCucumbervalue();
    System.out.println(value);
    }*/
   public static Logger log = LoggerFactory.getLogger(JiraRest.class);

    public static String getCucumbervalue(String issueKey) throws ClientProtocolException, Exception {
        String cucumbervalue = "";
        log.info("\n the getCucumber Method id Calling *******");
        URL url = new URL("http://localhost:2990/jira/rest/api/2/issue/" + issueKey );
        String encoding = Base64.getEncoder().encodeToString(("admin:admin").getBytes());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Basic " + encoding);
        InputStream content = (InputStream) connection.getInputStream();
        BufferedReader in =
                new BufferedReader(new InputStreamReader(content));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = in.readLine()) != null) {
          response.append(line);
            log.info("\n the Response is Received *******");

            try {
                log.info("\n JSON Parser Code is Calling *******");
               // log.info("\n Line Content ******* {}", line);
                line = line.replace("null", "");
                log.info("\n Line Content ******* {}", line);
                JSONParser parser = new JSONParser();
                log.info("\n JSON Parser Code is end *******");
                obj = parser.parse(line);
                log.info("\n Line Content  after parse ******* {}", line);
                log.info("\n Line is Parsed {} *******", obj);
            } catch (Exception e) {
                log.info("Something failed while parsing Json {}", e.getMessage());
                e.printStackTrace();
            }
                log.info("\n JSON Object Code is Started *******");
                JSONObject jsonObject = (JSONObject) obj;
                JSONObject fields = (JSONObject) jsonObject.get("fields");
                JSONObject value = (JSONObject) fields.get("customfield_10000");
                cucumbervalue = (String) value.get("value");
                log.info("\n  Cucumber Value is ********* {}", cucumbervalue);
                //  JSONObject posts = ((Object) jsonobject).getJSONObject("fields");
                // JSONArray fields = (JSONArray) jsonobject.get("fields");


        }
        return cucumbervalue;
    }
}