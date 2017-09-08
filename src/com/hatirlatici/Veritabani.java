package com.hatirlatici;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Veritabani {
	private SQLiteDatabase mydb;
	private Context context;

	public Veritabani(Context argcont) {
		context = argcont;
	}

	public String dbDelete(String id) {
		try {
			mydb = context.openOrCreateDatabase("veritabani.db", 0, null);
			mydb.execSQL("DELETE FROM tablo WHERE id='" + id + "';");
			mydb.close();
			return "Silindi!";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public String dbInsert(List<String> veri) {
		try {
			mydb = context.openOrCreateDatabase("veritabani.db", 0, null);
			mydb.execSQL("CREATE TABLE IF NOT EXISTS tablo(id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "banka TEXT,baslangic TEXT,bitis TEXT,kampanya TEXT,"
					+ "bonus TEXT,bnsbas TEXT,bnsbit TEXT);");
			String sorgu = "INSERT INTO tablo(banka,baslangic,bitis,kampanya,bonus,bnsbas,bnsbit) VALUES('"
					+ veri.get(0)
					+ "','"
					+ veri.get(1)
					+ "','"
					+ veri.get(2)
					+ "','"
					+ veri.get(3)
					+ "','" + veri.get(4) + "','" + veri.get(5) + "','" + veri.get(6) + "');";
			mydb.execSQL(sorgu);
			mydb.close();
			return "Eklendi!";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	public String dbUpdate(List<String> veri,String id) {
		try {
			mydb = context.openOrCreateDatabase("veritabani.db", 0, null);
			String sorgu = "UPDATE tablo SET banka='"+veri.get(0)+
					"',baslangic='"+veri.get(1)+"',bitis='"+veri.get(2)+
					"',kampanya='"+veri.get(3)+"',bonus='"+veri.get(4)+
					"',bnsbas='"+veri.get(5)+"',bnsbit='"+veri.get(6)+
					"' WHERE id='"+id+"';";
			mydb.execSQL(sorgu);
			mydb.close();
			return "Kaydedildi!";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public List<ListeElemani> dbSelect() {
		List<ListeElemani> kampanyalar = new ArrayList<ListeElemani>();
		try {

			SQLiteDatabase mydb = context.openOrCreateDatabase("veritabani.db", 0, null);
			Cursor c = mydb.rawQuery("SELECT * FROM tablo;", null);
			if (c != null) {
				if (c.moveToFirst()) {
					do {
						List<String> strlist = new ArrayList<String>();
						for (int i = 0; i < c.getColumnCount(); i++) {
							strlist.add(c.getString(i));
						}
						kampanyalar.add(new ListeElemani(strlist));
					} while (c.moveToNext());
				}

				else {
					List<String> strlist = new ArrayList<String>();
					strlist.add("");
					strlist.add("Kampanya bulunamadı !");
					strlist.add("");
					strlist.add("");
					strlist.add("");
					strlist.add("");
					strlist.add("");
					strlist.add("");
					kampanyalar.add(new ListeElemani(strlist));
				}
			}
			c.close();
			mydb.close();
		} catch (Exception e) {
			List<String> strlist = new ArrayList<String>();
			strlist.add("");
			strlist.add("Kampanya bulunamadı !");
			strlist.add("");
			strlist.add("");
			strlist.add("");
			strlist.add("");
			strlist.add("");
			strlist.add("");
			kampanyalar.add(new ListeElemani(strlist));
		}
		return kampanyalar;
	}

	public List<String> dbSelectById(String id) {
		List<String> strlist = new ArrayList<String>();
		try {
			SQLiteDatabase mydb = context.openOrCreateDatabase("veritabani.db", 0, null);
			Cursor c = mydb.rawQuery("SELECT * FROM tablo WHERE id='" + id + "';", null);
			if (c != null) {
				if (c.moveToFirst()) {
					for (int i = 0; i < c.getColumnCount(); i++) {
						strlist.add(c.getString(i));
					}
				}
			}
			c.close();
			mydb.close();
		} catch (Exception e) {}
		return strlist;
	}

}