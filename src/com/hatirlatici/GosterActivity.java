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

public class GosterActivity extends AppCompatActivity {

	private Toolbar mToolbar;
	private Calendar myCalendar;
	private Button btnDuzenle;
	private Button btnSil;
	private Button btnGeri;
	private List<EditText> edtxt;
	private CheckBox chkbox;
	private boolean kilitli = true;
	private String db_ID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ekle);

		Bundle bundle = getIntent().getExtras();
		db_ID = bundle.getString("id");

		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setIcon(R.drawable.ic_read);

		AutoCompleteTextView autotext = (AutoCompleteTextView) findViewById(R.id.edBanka);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(
						R.array.BANKALAR));
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

		chkbox = (CheckBox) findViewById(R.id.soru);
		chkbox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (((CheckBox) arg0).isChecked()) {
					edtxt.get(5).setEnabled(true);
					edtxt.get(6).setEnabled(true);
				} else {
					edtxt.get(5).setEnabled(false);
					edtxt.get(6).setEnabled(false);
					edtxt.get(5).setText("");
					edtxt.get(6).setText("");
				}
			}
		});

		btnDuzenle = (Button) findViewById(R.id.btnEkle);
		btnDuzenle.setText("Duzenle");
		btnDuzenle.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_edit,0,0);
		btnDuzenle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (kilitli) {
					kilit_ac();
				} else {
					List<String> veri = new ArrayList<String>();
					for (EditText ed : edtxt) {
						veri.add(ed.getText().toString());
					}
					toastla(new Veritabani(getApplicationContext()).dbUpdate(veri, db_ID));
					kilit_kapat();
				}
			}
		});

		btnSil = (Button) findViewById(R.id.btnSil);
		btnSil.setVisibility(View.VISIBLE);
		btnSil.setText("Sil");
		btnSil.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				toastla(new Veritabani(getApplicationContext()).dbDelete(db_ID));
				Intent returnIntent = new Intent();
				setResult(Activity.RESULT_CANCELED, returnIntent);
				finish();
			}
		});
		
		btnGeri = (Button) findViewById(R.id.btnGeri);
		btnGeri.setText("Geri");
		btnGeri.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent returnIntent = new Intent();
				setResult(Activity.RESULT_CANCELED, returnIntent);
				finish();
			}
		});

		kilit_kapat();
		veri_cek();
	}

	public void veri_cek() {
		try {
			List<String> strlist = new Veritabani(getApplicationContext()).dbSelectById(db_ID);
			for (int i = 0; i < edtxt.size(); i++) {
				edtxt.get(i).setText(strlist.get(i + 1));
			}
		} catch (Exception e) {
		}
	}

	public void kilit_ac() {
		kilitli = false;
		for (EditText ed : edtxt) {
			ed.setEnabled(true);
		}
		chkbox.setEnabled(true);
		btnDuzenle.setText("Kaydet");
		btnDuzenle.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_save,0,0);
	}

	public void kilit_kapat() {
		kilitli = true;
		for (EditText ed : edtxt) {
			ed.setEnabled(false);
		}
		chkbox.setEnabled(false);
		btnDuzenle.setText("Duzenle");
		btnDuzenle.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_edit,0,0);
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
			new DatePickerDialog(GosterActivity.this, date, myCalendar.get(Calendar.YEAR),
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