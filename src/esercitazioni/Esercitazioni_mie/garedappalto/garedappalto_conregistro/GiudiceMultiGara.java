package Esercitazioni_mie.garedappalto.garedappalto_conregistro;

import java.io.IOException;

public class GiudiceMultiGara {
    public final static RegistroGare registro = new RegistroGare();

    public void avvia() {
        try {
            new ThreadRichiesteHandler(registro).start();
            new ThreadOfferteHandler(registro).start();
        } catch (IOException e) { e.printStackTrace(); }
    }
    public static void main(String[] args) {
        new GiudiceMultiGara().avvia();
    }
}