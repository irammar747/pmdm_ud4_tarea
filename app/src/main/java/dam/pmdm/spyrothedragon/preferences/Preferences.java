package dam.pmdm.spyrothedragon.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private static final String PREFS_NAME = "app_preferences";
    private static final String KEY_GUIDE_COMPLETED = "guide_completed";

    public static void setGuideCompleted(Context context, boolean completed){
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_GUIDE_COMPLETED, completed);
        editor.apply();

    }

    public static boolean isGuideCompleted(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(KEY_GUIDE_COMPLETED, false);
    }

}
