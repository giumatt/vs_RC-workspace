package Esercitazione1;

import java.io.*;
import java.net.*;

public class SocketTest {
    public static void main(String[] args) {
        try {
            @SuppressWarnings("all")
            Socket s = new Socket("ntp1.inrim.it", 13);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            boolean more = true;
            while (more) {
                String line = in.readLine();
                if (line == null)
                more = false;
                else
                System.out.println(line);
            }
        } catch (IOException e) { System.out.println(e); }
    }
}

