package com.jason.app;

import java.util.List;

import net.youmi.android.appoffers.EarnedPointsNotifier;
import net.youmi.android.appoffers.YoumiOffersManager;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jason.adapters.AdapterApps;
import com.jason.util.CommonHelper;
import com.jason.util.ThreadManager;
public class AppActivity extends Activity {


	/**
	 * �˵�������
	 */
	private PopupWindow mMenuPopupWindow;
	
	/**
	 * �����Ѱ�װ������б�
	 */
	private GridView mGridViewApps;
	private TextView mTextViewGotoMarket;
	
	private final static int MSG_LOAD_COMPLETE = 0;
	private List<ApplicationInfo> mApps;
	private AdapterApps mAdapterApps;
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case MSG_LOAD_COMPLETE:
				mAdapterApps = new AdapterApps(AppActivity.this, mApps);
				mGridViewApps.setAdapter(mAdapterApps);
				break;
			}
		}
	
	};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	YoumiOffersManager.init(this, com.jason.util.Constants.DEV_ID, com.jason.util.Constants.SOFT_ID);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initViews();
        initEvents();
    }
    
    /**
     * initialization the references of views
     * date | time | author    
     * 2012-6-30 | ����12:50:18 | Jason Shieh
     */
    private void initViews(){
    	mGridViewApps = (GridView) findViewById(R.id.gridview_app);
    	mTextViewGotoMarket = (TextView) findViewById(R.id.goto_market);
    }
    
    /**
     * initialization the events of views
     * date | time | author    
     * 2012-6-30 | ����12:50:48 | Jason Shieh
     */
    private void initEvents(){
    	mGridViewApps.setOnItemClickListener(mTitlebarListener);
    	ThreadManager.postTask(new Runnable() {
			
			@Override
			public void run() {
		        PackageManager manager = AppActivity.this.getPackageManager();
		        mApps = manager.getInstalledApplications(0);
		        for(int i = mApps.size() - 1; i >= 0; i--){
		        	if((mApps.get(i).flags & ApplicationInfo.FLAG_SYSTEM) != 0){
		        		mApps.remove(i);
		        	}
		        }
		        mHandler.sendEmptyMessage(MSG_LOAD_COMPLETE);
			}
		});
    	mTextViewGotoMarket.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				YoumiOffersManager.showOffers(AppActivity.this, YoumiOffersManager.TYPE_REWARDLESS_APPLIST, null);
			}
		});
    }
    
	private void showMenu(View mMenuButton, final String packageName) {
		ViewGroup pop = (ViewGroup)LayoutInflater.from(this).inflate(R.layout.main_menupop_window, null);
		
		// �����¼�
		pop.findViewById(R.id.main_menu_checkupdate_button).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommonHelper.canLaunch(AppActivity.this, packageName);
			}
		});
		pop.findViewById(R.id.main_menu_exit_button).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommonHelper.uninstallApk(AppActivity.this, packageName);
			}
		});
		pop.findViewById(R.id.main_menu_detail).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommonHelper.showInstalledAppDetails(AppActivity.this, packageName);
			}
		});
		mMenuPopupWindow = new PopupWindow(pop, pop.getChildAt(0).getLayoutParams().width, pop.getChildAt(0).getLayoutParams().height, true);
		
		mMenuPopupWindow.setOutsideTouchable(true);
		// �����谭�����ؼ��Ĵ���
		mMenuPopupWindow.setTouchable(true);
		mMenuPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		mMenuPopupWindow.showAsDropDown(mMenuButton, 
				mMenuButton.getWidth()/2 - mMenuPopupWindow.getWidth()/2,
				- mMenuButton.getHeight());
	}
	
	private GridView.OnItemClickListener mTitlebarListener = new GridView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View mMenuButton, int position,
				long arg3) {
			showMenu(mMenuButton, mApps.get(position).packageName);
			
		}
	};
}