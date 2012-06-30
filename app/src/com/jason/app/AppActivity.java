package com.jason.app;

import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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

import com.jason.adapters.AdapterApps;
import com.jason.util.CommonHelper;
import com.jason.util.ThreadManager;
public class AppActivity extends Activity {


	/**
	 * 菜单弹出框
	 */
	private PopupWindow mMenuPopupWindow;
	
	/**
	 * 本地已安装软件的列表
	 */
	private GridView mGridViewApps;
	
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initViews();
        initEvents();
    }
    
    /**
     * initialization the references of views
     * date | time | author    
     * 2012-6-30 | 下午12:50:18 | Jason Shieh
     */
    private void initViews(){
    	mGridViewApps = (GridView) findViewById(R.id.gridview_app);
    }
    
    /**
     * initialization the events of views
     * date | time | author    
     * 2012-6-30 | 下午12:50:48 | Jason Shieh
     */
    private void initEvents(){
    	mGridViewApps.setOnItemClickListener(mTitlebarListener);
    	ThreadManager.postTask(new Runnable() {
			
			@Override
			public void run() {
		        PackageManager manager = AppActivity.this.getPackageManager();
		        mApps = manager.getInstalledApplications(0);
		        mHandler.sendEmptyMessage(MSG_LOAD_COMPLETE);
			}
		});
    }
    
	private void showMenu(View mMenuButton) {
		if (null == mMenuPopupWindow) {
			ViewGroup pop = (ViewGroup)LayoutInflater.from(this).inflate(R.layout.main_menupop_window, null);

//			// 监听事件
//			pop.findViewById(R.id.main_menu_checkupdate_button).setOnClickListener(mTitlebarListener);
//			pop.findViewById(R.id.main_menu_exit_button).setOnClickListener(mTitlebarListener);

			mMenuPopupWindow = new PopupWindow(pop, pop.getChildAt(0).getLayoutParams().width, pop.getChildAt(0).getLayoutParams().height, true);
			mMenuPopupWindow.setOutsideTouchable(true);

			mMenuPopupWindow.setOutsideTouchable(true);
			// 不会阻碍其他控件的触摸
			mMenuPopupWindow.setTouchable(false);
			mMenuPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		}
		if (!mMenuPopupWindow.isShowing()) {
			mMenuPopupWindow.showAsDropDown(mMenuButton, 
					0,
					- mMenuButton.getHeight());
		}
	}
	
	private GridView.OnItemClickListener mTitlebarListener = new GridView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View mMenuButton, int arg2,
				long arg3) {
			showMenu(mMenuButton);
			
		}
	};
}