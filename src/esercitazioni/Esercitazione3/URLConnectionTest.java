package Esercitazione3;

import java.io.*;
import java.net.*;

public class URLConnectionTest {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://www.w3.org");
            URLConnection connection = url.openConnection();
            connection.connect();

            int n = 1;
            String key;
            while ((key = connection.getHeaderFieldKey(n)) != null) {
                String value = connection.getHeaderField(n);
                System.out.println(key + ": " + value);
                n++;
            }
            System.out.println("-------");
            System.out.println("getContentType: " + connection.getContentType());
            System.out.println("getContentLength: " + connection.getContentLength());
            System.out.println("getCOntentEncoding: " + connection.getContentEncoding());
            System.out.println("getDate: "+connection.getDate());
            System.out.println("getExpiration: "+connection.getExpiration());
            System.out.println("getLastModifed: "+connection.getLastModified());
            System.out.println("-------");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            n = 1;
            while ((line = in.readLine()) != null && n <= 10) {
                System.out.println(line);
                n++;
            }
            if (line != null) System.out.println(". . .");
        } catch (IOException e) { System.out.println("Error: " + e); }
    }
}