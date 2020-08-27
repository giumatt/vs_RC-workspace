package Esercitazioni_mie.VoliAeroportoWSDL;

public class Volo {
    private String citta;
    private Data data;
    private Orario orario;
    private String voloId;

    public Volo(String citta, Data data, Orario orario, String voloId) {
        super();
        this.citta = citta;
        this.data = data;
        this.orario = orario;
        this.voloId = voloId;
    }

    public String getCitta() { return citta; }

    public Data getData() { return data; }

    public Orario getOrario() { return orario; }

    private String getVoloId() { return voloId; }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }
}