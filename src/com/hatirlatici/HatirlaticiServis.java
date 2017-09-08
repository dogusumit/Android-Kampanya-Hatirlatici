package com.hatirlatici;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class HatirlaticiServis extends Service
{
	private Timer zamanlayici;
	private Handler yardimci;
	private final static long PERIYOT = TimeUnit.HOURS.toMillis(1);

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
    @Override
	public void onCreate() {
    	super.onCreate();
    	
    	zamanlayici = new Timer();
    	yardimci = new Handler();
    	zamanlayici.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				hatirlat();
			}
		}, 0, PERIYOT);
    	
    }
    
    @Override
    public void onDestroy() {
    	zamanlayici.cancel();
    	super.onDestroy();
    }
	
	
	public void hatirlat()
	{
		yardimci.post(new Runnable() {
			@Override
			public void run() {
				kampanyaKontrol();
			}
		});
	}
	
	
	private void kampanyaKontrol(){
    	try{
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
	        String date = sdf.format(Calendar.getInstance().getTime());
    	SQLiteDatabase mydb = openOrCreateDatabase("veritabani.db",0, null);
    	Cursor c = mydb.rawQuery("SELECT banka,kampanya FROM tablo WHERE (date(baslangic)<='"+date+
    			"' AND date(bitis)>='"+date+"') OR (date(bnsbas)<='"+date+
    			"' AND date(bnsbit)>='"+date+"');",null);
    	if (c != null ) {
    	    if  (c.moveToFirst()) {
    	        do {
    	        	createNotification(c.getString(0),c.getString(1));
    	        }while (c.moveToNext());
    	    }
    	}
    	c.close();
    	mydb.close();
    	}
    	catch(Exception e)
    	{}
    }
	
    public void createNotification(String title,String txt)
    {
    	PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification mBuilder =
                new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher))
                .setTicker(title)
                .setContentTitle(title)
                .setContentText(txt)
        		.setSound(alarmSound)
        		.setAutoCancel(true)
        		.setContentIntent(pi)
        		.build();
        NotificationManager mNotificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify((int) (System.currentTimeMillis()%10000), mBuilder);
    }
}