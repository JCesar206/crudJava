package app.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
	private static Locale locale = new Locale("es");
	private static ResourceBundle bundle =
		ResourceBundle.getBundle("i18n.messages", locale);
	
	public static void setLanguage(String lang) {
		locale = new Locale(lang);
		bundle = ResourceBundle.getBundle("i18n.messages", locale);
	}
	
	public static String t(String key) {
		return bundle.getString(key);
	}
}
