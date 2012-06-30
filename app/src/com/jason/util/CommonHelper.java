package com.jason.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

public class CommonHelper {

	private static final String SCHEME = "package";
	/**
	 * ����ϵͳInstalledAppDetails���������Extra����(����Android 2.1��֮ǰ�汾)
	 */
	private static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";
	/**
	 * ����ϵͳInstalledAppDetails���������Extra����(����Android 2.2)
	 */
	private static final String APP_PKG_NAME_22 = "pkg";
	/**
	 * InstalledAppDetails���ڰ���
	 */
	private static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";
	/**
	 * InstalledAppDetails����
	 */
	private static final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";
	/**
	 * Activity Action: Show screen of details about a particular application.
	 * <p>
	 * In some cases, a matching Activity may not exist, so ensure you safeguard
	 * against this.
	 * <p>
	 * Input: The Intent's data URI specifies the application package name to be
	 * shown, with the "package" scheme. That is "package:com.my.app".
	 * <p>
	 * Output: Nothing.
	 */
	public static final String ACTION_APPLICATION_DETAILS_SETTINGS = "android.settings.APPLICATION_DETAILS_SETTINGS";

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
	
	/**
	 * ���ݰ���ж�����
	 * 
	 * @param context
	 *            �� ϵͳ������
	 * @param packageName
	 *            �� �������
	 * @time 2012-3-7 | ����11:18:38
	 */
	public static void uninstallApk(Context context, String packageName) {
		Uri packageURI = Uri.parse("package:" + packageName);
		Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	

	/**
	 * Checks whether the package can launch or not.
	 * 
	 * @param context
	 * @param packageName
	 *            The package to checks
	 * @return true if the package can launch, else false.
	 */
	public static boolean canLaunch(Context context, String packageName) {
		boolean canLaunch = false;
		Intent launchIntent = context.getPackageManager()
				.getLaunchIntentForPackage(packageName);
		if (launchIntent != null) {
			launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(launchIntent);
			canLaunch = true;
		}
		return canLaunch;
	}

	/**
	 * ����ϵͳInstalledAppDetails������ʾ�Ѱ�װӦ�ó������ϸ��Ϣ�� ����Android 2.3��Api Level
	 * 9�����ϣ�ʹ��SDK�ṩ�Ľӿڣ� 2.3���£�ʹ�÷ǹ����Ľӿڣ��鿴InstalledAppDetailsԴ�룩��
	 * 
	 * @param context
	 * 
	 * @param packageName
	 *            Ӧ�ó���İ���
	 */
	public static void showInstalledAppDetails(Context context,
			String packageName) {
		Intent intent = new Intent();
		final int apiLevel = Build.VERSION.SDK_INT;
		if (apiLevel >= 9) { // 2.3��ApiLevel 9�����ϣ�ʹ��SDK�ṩ�Ľӿ�
			intent.setAction(ACTION_APPLICATION_DETAILS_SETTINGS);
			Uri uri = Uri.fromParts(SCHEME, packageName, null);
			intent.setData(uri);
		} else { // 2.3���£�ʹ�÷ǹ����Ľӿڣ��鿴InstalledAppDetailsԴ�룩
			// 2.2��2.1�У�InstalledAppDetailsʹ�õ�APP_PKG_NAME��ͬ��
			final String appPkgName = (apiLevel == 8 ? APP_PKG_NAME_22
					: APP_PKG_NAME_21);
			intent.setAction(Intent.ACTION_VIEW);
			intent.setClassName(APP_DETAILS_PACKAGE_NAME,
					APP_DETAILS_CLASS_NAME);
			intent.putExtra(appPkgName, packageName);
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
