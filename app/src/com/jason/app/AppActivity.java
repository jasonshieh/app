package com.jason.app;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.GridView;
import java.util.List;

import com.jason.adapters.AdapterApps;
import com.jason.util.ThreadManager;
public class AppActivity extends Activity {
	
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
    	ThreadManager.postTask(new Runnable() {
			
			@Override
			public void run() {
		        PackageManager manager = AppActivity.this.getPackageManager();
		        mApps = manager.getInstalledApplications(0);
		        mHandler.sendEmptyMessage(MSG_LOAD_COMPLETE);
			}
		});
    }
}