package com.matteoveroni;

import com.matteoveroni.localization.SupportedCountries;
import java.io.File;
import java.util.Locale;

/**
 *
 * @author Matteo Veroni
 */
public class App {

	public final static String NAME = "WordLearning";
	public final static String VERSION = "0.0.11";
	public final static String PATH = System.getProperty("user.home") + File.separator + "WordLearning";
	public final static Locale DEFAULT_LOCALE = SupportedCountries.USA.getLocale();
	public final static double[][] WINDOW_DIMENSIONS = {{1920, 1080}, {1024, 768}, {800, 600}};
	public static double WINDOW_WIDTH = 800;
	public static double WINDOW_HEIGHT = 600;

}
