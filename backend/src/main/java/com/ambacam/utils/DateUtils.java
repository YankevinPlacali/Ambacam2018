package com.ambacam.utils;

import java.util.Date;

public class DateUtils {

	private static final int YEAR_OFFSET = 1900;

	@SuppressWarnings("deprecation")
	public static int getActualYear(Date date) {
		return date.getYear() + YEAR_OFFSET;
	}
}
