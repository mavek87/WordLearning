package com.matteoveroni;

import com.matteoveroni.localization.SupportedCountries;
import java.util.Locale;

/**
 *
 * @author Matteo Veroni
 */
public class App {

    public final static String NAME = "WordLearning";
    public final static String VERSION = "0.0.7";
    public final static double[][] WINDOW_DIMENSIONS = {{1920, 1080}, {1024, 768}, {800, 600}};
    public static double WINDOW_WIDTH = 800;
    public static double WINDOW_HEIGHT = 600;
    public static final Locale DEFAULT_LOCALE = SupportedCountries.USA.getLocale();
}
