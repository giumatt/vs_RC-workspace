package Esercitazioni_mie.IndexServer;

import java.io.*;
import java.util.Arrays;

public class File implements Serializable {
    private String filename;
    private String[] keywords;
    private String contenuto;

    public File(String filename, String[] keywords, String contenuto) {
        super();
        this.filename = filename;
        this.keywords = keywords;
        this.contenuto = contenuto;
    }

    public String getFilename() { return filename; }

    public String[] getKeywords() { return keywords; }

    public String getContenuto() { return contenuto; }

    @Override
    public String toString() {
        return "File [filename: " + filename + ", keywords: " + Arrays.toString(keywords) + ", contenuto: " + contenuto + "].";
    }
}