package Esercitazioni_mie.garedappalto;

import java.io.Serializable;

public class Richiesta implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String descrizione;
    private int importoMassimo;

    public Richiesta (String descrizione, int importoMassimo) {
        this.descrizione = descrizione;
        this.importoMassimo = importoMassimo;
    }

    public String getDescrizione() { return descrizione; }

    public int getImportoMassimo() { return importoMassimo; }

    @Override
    public String toString() {
        return "Richiesta {" + "descrizione: " + descrizione + ", Importo massimo: " + importoMassimo + "}";
    }
}