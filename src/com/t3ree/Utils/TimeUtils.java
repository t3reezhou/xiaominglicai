package com.t3ree.Utils;

public class TimeUtils {
	private int year;
	private int month;
	private int day;
	private int[] previous = { 1, 2, 4, 6, 8, 9, 11 };
	private int[] next = { 12, 2, 4, 6, 7, 9, 11 };

	public TimeUtils(String time) {
		this.year = Integer.valueOf(time.substring(0, 4));
		this.month = Integer.valueOf(time.substring(5, 7));
		this.day = Integer.valueOf(time.substring(8, 10));
	}

	public String previous() {
		if (1 == day) {
			if (previous.equals(month)) {
				day = 31;
			} else if (month == 3) {
				judge(year);
			} else {
				day = 30;
			}
		} else {
			day--;
		}
		if (month == 1) {
			month = 12;
			year--;
		} else
			month--;
		return null;
	}

	public String next() {
		if (next.equals(month)) {

		} else if (month == 1) {

		} else {

		}
		return null;
	}

	private void judge(int year) {
		// TODO 自动生成的方法存根
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
			day = 29;
		else
			day = 28;
	}
}
