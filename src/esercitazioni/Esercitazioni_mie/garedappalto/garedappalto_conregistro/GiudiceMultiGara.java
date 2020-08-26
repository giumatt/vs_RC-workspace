package Esercitazioni_mie.garedappalto.garedappalto_conregistro;

import java.io.IOException;

public class GiudiceMultiGara {
    public final static RegistroGare registro = new RegistroGare();

    public void avvia() {
        try {
            //fase 1-2: ricevi richiesta e invio offerta ai partecipanti
            new ThreadRichiesteHandler(registro).start();

            //fase 3: ricevi offerte dai partecipanti
            new ThreadOfferteHandler(registro).start();
        } catch (IOException e) { e.printStackTrace(); }
    }
    public static void main(String[] args) {
        new GiudiceMultiGara().avvia();
    }
}