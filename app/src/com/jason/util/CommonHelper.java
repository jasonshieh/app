package com.jason.util;

import android.content.Context;

public class CommonHelper {

	/**
	 * DIP转像素值
	 * 
	 * @param context
	 *            ： 系统上下文
	 * @param dipValue
	 *            :DIP值
	 * @return 像素值
	 * @time 2012-3-7 | 上午11:03:17
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 像素值转DIP
	 * 
	 * @param context
	 *            ： 系统上下文
	 * @param pxValue
	 *            像素值
	 * @return DIP值
	 * @time 2012-3-7 | 上午11:03:46
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
