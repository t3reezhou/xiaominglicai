package com.t3ree.Utils;

import android.content.Context;

public class DensityUtils {
	/**
	 * 根据手机的分辨率从dip的单位转成为px
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
	 * 根据手机的分辨率从px的单位转成为dip
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
