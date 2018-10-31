package com.gemini.ambacam.utils;

import java.util.Date;
import java.util.UUID;

public class RequeteGroupeUtils {

	@SuppressWarnings("deprecation")
	public static String generateRequeteGroupeName() {
		Date date = new Date();

		String name = String.format("%04d-%02d-%02d-%02d-%02d-%02d-%s", DateUtils.getActualYear(date), date.getMonth(),
				date.getDay(), date.getHours(), date.getMinutes(), date.getSeconds(), UUID.randomUUID().toString());

		return name;
	}
}
