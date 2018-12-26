package com.example.tutorial.plugins;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.io.*;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public class BitbucketAPI {

    public static String username = "deshapuraja";
    public static String apppassword = "Tirumala!d8";
    public static String reponame = "testbdd";
    // public static   byte[] encodedAuth = Base64.getEncoder().encode((username+":"+apppassword).getBytes());
    public static String url = "https://api.bitbucket.org/2.0/repositories";
    public static String hashKey, hash_key;

    public static Logger log = LoggerFactory.getLogger(BitbucketAPI.class);


    public static void createFileBitbucket(String fileName, String fileContent) throws IOException {

        log.info("\n\n\t#+#+# BitbucketAPI createFileBitbucket: STATIC INITIALIZIER\n");
        URL url = new URL("https://api.bitbucket.org/2.0/repositories/" + username + "/" + reponame + "/src/");

        String encoded = Base64.getEncoder().encodeToString((username + ":" + apppassword).getBytes(StandardCharsets.UTF_8));

        StringBuilder postData = new StringBuilder();
        postData.append(fileName).append("=").append(fileContent);

        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", "Basic " + (encoded));
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(postData.toString());
        wr.flush();
        wr.close();

        int responseCode = connection.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
    }
}






