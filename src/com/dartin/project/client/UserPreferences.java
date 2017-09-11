package com.dartin.project.client;

import com.dartin.project.AppLauncher;
import com.dartin.project.util.Preferences;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author Daniil Y on 29.06.2017.
 */
public class UserPreferences {

	private static final String USER_PREFERENCES = "com.dartin.project.client.user";
	private static final String L10N_PROPERTIES = "com.dartin.project.gui.resources.l10n.TextLocalization";

	public static final String USER_LOCALE = "user_locale";
	public static final String DEFAULT_LOCALE = "default_locale";

	public static volatile Preferences userPrefs;
	public static ResourceBundle localeResources;

	static {
		reload();
	}


	private static ResourceBundle loadLocale() {
		Locale currentLocale;
		try {
			currentLocale = Locale.forLanguageTag(userPrefs.getString(USER_LOCALE));
		} catch (MissingResourceException e) {
			currentLocale = Locale.forLanguageTag(userPrefs.getString(DEFAULT_LOCALE));
		}
		return ResourceBundle.getBundle(L10N_PROPERTIES, currentLocale);
	}

	public static void switchLanguage(String locale) {
		userPrefs.setString(USER_LOCALE, locale);
		try {
			userPrefs.updateFile();
		} catch (FileNotFoundException e) {
			System.err.println("Unable to upload preferences!");
			e.printStackTrace();
		}
		reload();
		AppLauncher.redraw();
	}

	public static void reload() {
		System.out.println("Reloading locale");
		try {
			userPrefs = new Preferences(USER_PREFERENCES);
		} catch (IOException e) {
			System.err.println("Unable to reload prefs!");
			e.printStackTrace();
		}
		localeResources = loadLocale();
		System.out.println("Current locale: " + localeResources.getLocale());
	}
}
