package Esercitazione1;

import java.io.*;
import java.net.*;
public class DownloadHTML {
    public static void main(String[] args) {
        try {
            String URL = "stackoverflow.com";
            @SuppressWarnings("all")
            Socket s = new Socket(URL, 80);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out.println("GET/questions HTTP/1.1");
            out.println("Host: " + URL);
            out.println();
            boolean more = true;
            while (more) {
                String line = in.readLine();
                if (line == null) more = false;
                else System.out.println(line);
            }
        } catch (IOException e) { System.out.println("Error" + e); }
    }
}