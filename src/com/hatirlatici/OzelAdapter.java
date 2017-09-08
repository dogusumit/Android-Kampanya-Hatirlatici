package com.hatirlatici;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class OzelAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<ListeElemani> liste;
	Activity baseActivity;

	public OzelAdapter(Activity activity, List<ListeElemani> paramlist) {
		mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		liste = paramlist;
		baseActivity=activity;
	}

	@Override
	public int getCount() {
		return liste.size();
	}

	@Override
	public ListeElemani getItem(int position) {
		return liste.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View satirView;

		satirView = mInflater.inflate(R.layout.listview_satir, null);
		TextView txtBanka = (TextView) satirView.findViewById(R.id.title);
		TextView txtTarih = (TextView) satirView.findViewById(R.id.year);
		TextView txtTarih2 = (TextView) satirView.findViewById(R.id.year2);
		TextView txtAciklama = (TextView) satirView.findViewById(R.id.genre);

		final ListeElemani eleman = liste.get(position);

		if (!liste.isEmpty()) {
			txtBanka.setText(eleman.getBanka());
			txtTarih.setText(eleman.getTarih());
			txtTarih2.setText(eleman.getTarih2());
			txtAciklama.setText(eleman.getAciklama());

			if (eleman.getdb_ID().length() > 0) {
				satirView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Intent i = new Intent(baseActivity, GosterActivity.class);
						i.putExtra("id", eleman.getdb_ID());
						baseActivity.startActivityForResult(i,1);
					}
				});
			}
		}
		return satirView;
	}

	public void toastla(String str) {
		Toast.makeText(mInflater.getContext(), str, Toast.LENGTH_LONG).show();
	}
}
