package com.example.tutorial.plugins;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;



public class BitbucketAPI {

    public static String  username = "deshapuraja";
    public static String  apppassword = "Tirumala!d8";
    public static  String  reponame = "testbdd";
    public static   byte[] encodedAuth = Base64.getEncoder().encode((username+":"+apppassword).getBytes());

   public static  Logger log = LoggerFactory.getLogger(BitbucketAPI.class);
		/*String[] command = {"C:\\Users\\shripadg\\AppData\\Local\\Apps\\curl-7.46.0-win64\\curl-7.46.0-win64\\bin\\curl.exe", "-X", "POST", "-H","-F", "/repo/path/to/image.png=@image.png",
	                "Authorization: Basic " + new String(encodedAuth),
	                "https://api.bitbucket.org/2.0/repositories/"+username+"/"+reponame+"/src/master/"
	        };
		 ProcessBuilder process = new ProcessBuilder(command);
		 Process p;
		 System.out.println("\ninfo: Starting process that accepts curl POST command\n");
	     p = process.start();
		*/

		public static void createFileBitbucket(String fileName) throws IOException{

          log.info("\n\n\t#+#+# BitbucketAPI createFileBitbucket: STATIC INITIALIZIER\n");
            HttpURLConnection connection = (HttpURLConnection) new URL("https://api.bitbucket.org/2.0/repositories/" + username + "/" + reponame + "/src/").openConnection();
            connection.setRequestProperty("Authorization", "Basic " + new String(encodedAuth));
            //connection.setRequestProperty("--data-urlencode '/path/to/me.txt=Lorem ipsum.'","Curl");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            //connection.setRequestProperty("Content-Type", "application/json");
            //connection.setRequestProperty("Accept", "application/x-www-form-urlencoded");
            connection.setRequestMethod("POST");
            connection.connect();
            if(fileName != null)
            {
                OutputStream os = connection.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                os.write(fileName.getBytes("UTF-8"));
                osw.flush();
                os.close();
            }
           /* else
            {
                OutputStream os = connection.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                os.write(content.getBytes());
                osw.flush();
                os.close();
            }*/
	        /*OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");

            os.write(fileName.getBytes("UTF-8"));
            *//*long MAX_FILE_SIZE = 0;
            if (fileName.length() > MAX_FILE_SIZE) {
                throw new FileNotFoundException();
            }
            byte[] buffer = new byte[(int) fileName.length()];
            log.info("\n the Buffer is {}",buffer );
            InputStream ios = null;
            try {
                ios = new FileInputStream(fileName);
                if (ios.read(buffer) == -1) {
                    throw new IOException(
                            "EOF reached while trying to read the whole file");
                }
            } finally {
                try {
                    if (ios != null)
                        ios.close();
                } catch (IOException e) {
                }
            }*//*
            log.info("\n The File Name in Bitbucket class is {} \n", fileName);
            osw.flush();
            os.close();*/

            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            System.out.println("" + sb.toString());

        }




       /* public static void readFileContent()throws IOException{
            HttpURLConnection connection = (HttpURLConnection) new URL("https://api.bitbucket.org/2.0/repositories/" + username + "/" + reponame + "/src/").openConnection();
            connection.setRequestProperty("Authorization", "Basic " + new String(encodedAuth));
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.connect();
        }*/

}
