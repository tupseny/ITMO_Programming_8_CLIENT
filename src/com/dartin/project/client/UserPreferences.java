package com.dartin.project.client;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Daniil Y on 29.06.2017.
 */
public class UserPreferences {

	private static final String USER_PREFERENCES = "com.dartin.project.client.user.properties";
	private static final String L10N_PROPERTIES = "com.dartin.project.gui.resouces.l10n.TextLocalization";

	public static final String USER_LOCALE = "user_locale";
	public static final String DEFAULT_LOCALE = "default_locale";

	public static ResourceBundle userPrefs;
	public static ResourceBundle localeResources;

	static {
		reload();
	}

	private static ResourceBundle loadPrefs(String filename) {
		return ResourceBundle.getBundle(filename);
	}

	private static ResourceBundle loadLocale() {
		Locale currentLocale = null;
		try {
			currentLocale = Locale.forLanguageTag(userPrefs.getString(USER_LOCALE));
		} catch (MissingResourceException e) {
			currentLocale = Locale.forLanguageTag(userPrefs.getString(DEFAULT_LOCALE));
		} finally {
			return ResourceBundle.getBundle(L10N_PROPERTIES, currentLocale);
		}
	}

	private static void reload() {
		userPrefs = loadPrefs(USER_PREFERENCES);
		localeResources = loadLocale();
	}
}
