package com.jason.util;

public class ThreadManager {
	
	public ThreadManager(){
		
	}
	
	/**
	 * start a task
	 * date | time | author    
	 * 2012-6-30 | ÏÂÎç1:13:27 | Jason Shieh
	 */
	public static void postTask(Runnable runnable){
		new Thread(runnable).start();
	}
}
