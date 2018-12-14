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
import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class JiraRest {
    public static Object obj;
   /* public static void main(String[] args) throws ClientProtocolException, IOException {


    String value = getCucumbervalue();
    System.out.println(value);
    }*/

    public static String getCucumbervalue(String issueKey) throws ClientProtocolException, IOException {
        String cucumbervalue = "";
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
            System.out.println(response.append(line));
            JSONParser parser = new JSONParser();

            try {
                Object obj = parser.parse(line);
                JSONObject jsonObject = (JSONObject) obj;
                JSONObject fields = (JSONObject) jsonObject.get("fields");
                JSONObject value = (JSONObject) fields.get("customfield_10000");
                cucumbervalue = (String) value.get("value");
               // System.out.println(cucumbervalue);
                //  JSONObject posts = ((Object) jsonobject).getJSONObject("fields");
                // JSONArray fields = (JSONArray) jsonobject.get("fields");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cucumbervalue;
    }
}