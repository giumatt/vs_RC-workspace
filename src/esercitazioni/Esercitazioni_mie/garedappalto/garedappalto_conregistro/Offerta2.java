package Esercitazioni_mie.garedappalto.garedappalto_conregistro;

import java.io.Serializable;

public class Offerta2 implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int idPartecipante;
    private int idGara;
    private int importoRichiesto;

    public Offerta2 (int idPartecipante, int idGara, int importoRichiesto) {
        this.idPartecipante = idPartecipante;
        this.idGara = idGara;
        this.importoRichiesto = importoRichiesto;
    }

    public int getIdPartecipante() { return idPartecipante; }

    public int getIdGara() { return idPartecipante; }

    public int getImportoRichiesto() { return importoRichiesto; }

    @Override
    public String toString() {
        return "Offerta{" + "idPartecipante=" + idPartecipante + ", idGara: " + idGara + ", importoRichiesto=" + importoRichiesto + '}';
    }
}