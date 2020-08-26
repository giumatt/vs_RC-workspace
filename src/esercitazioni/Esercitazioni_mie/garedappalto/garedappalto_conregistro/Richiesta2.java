package Esercitazioni_mie.garedappalto.garedappalto_conregistro;

import java.io.Serializable;

public class Richiesta2 implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int idEnte;
    private String descrizione;
    private int importoMassimo;

    public Richiesta2 (int idEnte, String descrizione, int importoMassimo) {
        this.idEnte = idEnte;
        this.descrizione = descrizione;
        this.importoMassimo = importoMassimo;
    }

    public int getIdEnte() { return idEnte; }

    public String getDescrizione() { return descrizione; }

    public int getImportoMassimo() { return importoMassimo; }

    @Override
    public String toString() {
        return "Richiesta {" + "idEnte: " + idEnte + ", descrizione: " + descrizione + ", Importo massimo: " + importoMassimo + "}";
    }
}