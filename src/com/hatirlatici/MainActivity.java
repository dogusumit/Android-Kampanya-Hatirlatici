package com.hatirlatici;

import java.util.List;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
public class MainActivity extends AppCompatActivity {
	
	private Toolbar mToolbar;
	private ListView list_view;
	private List<ListeElemani> kampanyalar;
	private OzelAdapter adaptorumuz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        
        listyukle();
        baslatServis();
    }
    
    private void listyukle()
    {
        kampanyalar=new Veritabani(getApplicationContext()).dbSelect();
        list_view=(ListView) findViewById(R.id.liste);
        adaptorumuz=new OzelAdapter(this,kampanyalar);
        adaptorumuz.notifyDataSetChanged();
        list_view.setAdapter(adaptorumuz);
    }
    
    
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    listyukle();
    }
    
    private void baslatServis()
    {
		ActivityManager servisyoneticisi=(ActivityManager) getSystemService(ACTIVITY_SERVICE);
		for(RunningServiceInfo servis:servisyoneticisi.getRunningServices(Integer.MAX_VALUE))
		{
			if(getApplication().getPackageName().equals(servis.service.getPackageName()))
			{
				return;
			}
		}
		startService(new Intent(MainActivity.this,HatirlaticiServis.class));
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_ekle) {
			Intent intentEkle=new Intent(getApplicationContext(),EkleActivity.class);
		    startActivityForResult(intentEkle,1);
            return true;
        }
        if (item.getItemId() == R.id.action_kapat) {
        	super.onBackPressed();
            return true;
        }
 
        return super.onOptionsItemSelected(item);
    }
    
}
