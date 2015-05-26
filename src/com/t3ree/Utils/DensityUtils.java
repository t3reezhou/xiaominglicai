package com.t3ree.Utils;

import android.content.Context;

public class DensityUtils {
	/**
	 * �����ֻ��ķֱ��ʴ�dip�ĵ�λת��Ϊpx
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * �����ֻ��ķֱ��ʴ�px�ĵ�λת��Ϊdip
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scal = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scal + 0.5f);
	}
}
