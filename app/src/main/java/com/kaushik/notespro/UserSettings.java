package com.kaushik.notespro;

import android.app.Application;

public class UserSettings extends Application {

    public static final String PREFERENCES = "customTheme";

    public static final String CUSTOM_THEME = "customTheme";
    public static final String LIGHT_THEME = "lightTheme";
    public static final String DARK_THEME = "darkTheme";

    public String getCustomTheme() {
        return customTheme;
    }

    public void setCustomTheme(String customTheme) {
        this.customTheme = customTheme;
    }

    private String customTheme;

}
