package jp.gmo.user.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Properties;

public class Utils {

	public static Long getDateTimeCurrent() {
		LocalDateTime localDateTime = LocalDateTime.now();
		ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
		return zdt.toInstant().toEpochMilli();
	}

	public static String getMessage(String messageCode) {
		try {
			InputStream utf8in = Utils.class.getClassLoader().getResourceAsStream("i18n/messages.properties");
			Properties props = new Properties();
			if(utf8in != null){
				Reader reader = new InputStreamReader(utf8in, StandardCharsets.UTF_8);
				props.load(reader);
			}
			return props.getProperty(messageCode);
		} catch (Exception e) {
			return "";
		}
	}

	public static String getUpdateBy(String email){
		return email.split("@")[0];
	}
}
