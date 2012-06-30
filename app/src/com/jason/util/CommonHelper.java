package com.jason.util;

import android.content.Context;

public class CommonHelper {

	/**
	 * DIPת����ֵ
	 * 
	 * @param context
	 *            �� ϵͳ������
	 * @param dipValue
	 *            :DIPֵ
	 * @return ����ֵ
	 * @time 2012-3-7 | ����11:03:17
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * ����ֵתDIP
	 * 
	 * @param context
	 *            �� ϵͳ������
	 * @param pxValue
	 *            ����ֵ
	 * @return DIPֵ
	 * @time 2012-3-7 | ����11:03:46
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
