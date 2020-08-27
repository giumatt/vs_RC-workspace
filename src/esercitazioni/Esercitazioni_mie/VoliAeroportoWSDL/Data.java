package Esercitazioni_mie.VoliAeroportoWSDL;

import java.io.Serializable;

public class Data implements Serializable {
    private int giorno, mese, anno;

    public Data(int giorno, int mese, int anno) {
        this.giorno = giorno;
        this.mese = mese;
        this.anno = anno;
    }

    public int getGiorno() { return giorno; }

    public int getMese() { return mese; }

    public int getAnno() { return anno; }

    @Override
    public int hashCode() {
        return (anno + "-" + mese + "-" + anno).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    public String toString() {
        return "Data [giorno: " + giorno + ", mese: " + mese + ", anno: " + anno + "]"; 
    }
}