package com.jason.adapters;

import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jason.app.R;
public class AdapterApps extends BaseAdapter{
	/**
	 * apps installed
	 */
	private List<ApplicationInfo> mApps;
	private Context mContext;
	public AdapterApps(Context context, List<ApplicationInfo> apps){
		mContext = context;
		if(apps == null){
			throw new RuntimeException("datas can not be null");
		}
		mApps = apps;
	}
	
	@Override
	public int getCount() {
		return mApps.size();
	}

	@Override
	public Object getItem(int position) {
		if(position < mApps.size()){
			return mApps.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_app, null);
		}
		ApplicationInfo info = mApps.get(position);
		ImageView icon = (ImageView) convertView.findViewById(R.id.app_icon);
		icon.setImageDrawable(info.loadIcon(mContext.getPackageManager()));
		TextView label = (TextView) convertView.findViewById(R.id.app_label);
		label.setText(info.loadLabel(mContext.getPackageManager()));
		return convertView;
	}
	
}
