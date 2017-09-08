package com.hatirlatici;

import java.util.List;

public class ListeElemani {
	private String db_ID;
    private String  banka;
    private String  tarih;
    private String  tarih2;
    private String  aciklama;

    public ListeElemani(List<String> liste) {
        super();
        this.db_ID = liste.get(0);
        this.banka = liste.get(1)+"\t"+liste.get(5)+" TL";
        this.tarih = "Kampanya : "+liste.get(2)+" - "+liste.get(3);
        this.tarih2 = "Bonus : "+liste.get(6)+" - "+liste.get(7);
        this.aciklama = liste.get(4);
    }

    public String getdb_ID() {
        return db_ID;
    }
    public String getBanka() {
        return banka;
    }
    public String getTarih() {
        return tarih;
    }
    public String getTarih2() {
        return tarih2;
    }
    public String getAciklama() {
        return aciklama;
    }
}
