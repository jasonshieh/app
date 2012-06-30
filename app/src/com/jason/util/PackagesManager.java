package com.jason.util;


public class PackagesManager {
	
	/**
	 *  lazyman singlton
	 */
	private static PackagesManager sInstance;
	private PackagesManager(){
	}
	public static PackagesManager getInstance(){
		if(sInstance == null){
			synchronized (PackagesManager.class) {
				if(sInstance == null){
					sInstance = new PackagesManager();
				}
			}
		}
		return sInstance;
	}
	
}
