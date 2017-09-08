package com.hatirlatici;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class EkleActivity extends AppCompatActivity {

	private Toolbar mToolbar;
	private Calendar myCalendar;
	private Button btnEkle;
	private Button btnGeri;
	private List<EditText> edtxt;
	private CheckBox chkbox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ekle);

		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setIcon(R.drawable.ic_add);

		AutoCompleteTextView autotext = (AutoCompleteTextView) findViewById(R.id.edBanka);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.BANKALAR));
		autotext.setAdapter(adapter);
		autotext.setThreshold(2);
		
		edtxt = new ArrayList<EditText>();
		edtxt.add((EditText) findViewById(R.id.edBanka));
		edtxt.add((EditText) findViewById(R.id.edBas));
		edtxt.add((EditText) findViewById(R.id.edBit));
		edtxt.add((EditText) findViewById(R.id.edKampanya));
		edtxt.add((EditText) findViewById(R.id.edBns));
		edtxt.add((EditText) findViewById(R.id.edBnsBas));
		edtxt.add((EditText) findViewById(R.id.edBnsBit));

		edtxt.get(1).setOnClickListener(edtxtClick);
		edtxt.get(2).setOnClickListener(edtxtClick);
		edtxt.get(5).setOnClickListener(edtxtClick);
		edtxt.get(6).setOnClickListener(edtxtClick);
		edtxt.get(5).setVisibility(View.GONE);
		edtxt.get(6).setVisibility(View.GONE);

		chkbox = (CheckBox) findViewById(R.id.soru);
		chkbox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (((CheckBox) arg0).isChecked()) {
					edtxt.get(5).setVisibility(View.VISIBLE);
					edtxt.get(6).setVisibility(View.VISIBLE);
					edtxt.get(5).setEnabled(true);
					edtxt.get(6).setEnabled(true);
				} else {
					edtxt.get(5).setVisibility(View.GONE);
					edtxt.get(6).setVisibility(View.GONE);
					edtxt.get(5).setEnabled(false);
					edtxt.get(6).setEnabled(false);
					edtxt.get(5).setText("");
					edtxt.get(6).setText("");
				}
			}
		});
		
		btnEkle = (Button) findViewById(R.id.btnEkle);
		btnEkle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				List<String> veri = new ArrayList<String>();
				for (EditText ed : edtxt) {
					veri.add(ed.getText().toString());
				}
				toastla(new Veritabani(getApplicationContext()).dbInsert(veri));
			}
		});

		btnGeri = (Button) findViewById(R.id.btnGeri);
		btnGeri.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent returnIntent = new Intent();
				setResult(Activity.RESULT_CANCELED, returnIntent);
				finish();
			}
		});
	}

	public void toastla(String str) {
		Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onBackPressed() {
		Intent returnIntent = new Intent();
		setResult(Activity.RESULT_CANCELED, returnIntent);
		finish();
	}

	private View.OnClickListener edtxtClick = new View.OnClickListener() {

		@Override
		public void onClick(final View v) {
			myCalendar = Calendar.getInstance();

			DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					myCalendar.set(Calendar.YEAR, year);
					myCalendar.set(Calendar.MONTH, monthOfYear);
					myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
					String myFormat = "yyyy-MM-dd";
					SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
					EditText tmp_ed = (EditText) v;
					tmp_ed.setText(sdf.format(myCalendar.getTime()));
				}
			};
			new DatePickerDialog(EkleActivity.this, date, myCalendar.get(Calendar.YEAR),
					myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_ekle, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_geri) {
			Intent returnIntent = new Intent();
			setResult(Activity.RESULT_CANCELED, returnIntent);
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}