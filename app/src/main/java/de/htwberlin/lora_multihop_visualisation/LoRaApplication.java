package de.htwberlin.lora_multihop_visualisation;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import de.htwberlin.lora_multihop_logic.components.lora.LoRaConfig;

public class LoRaApplication extends Application {
    private String PREF_CONF_KEY = "config";

    private LoRaConfig loRaConfig;

    @Override
    public void onCreate() {
        super.onCreate();

        if (loRaConfig == null) {
            loRaConfig = loadConfig();
        }
    }

    public LoRaConfig getLoRaConfig() {
        return loRaConfig;
    }

    /**
     * Save the given config as a JSON string in app preferences.
     */
    public boolean persistConfig(LoRaConfig config) {
        SharedPreferences prefs = this.getSharedPreferences(PREF_CONF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_CONF_KEY, new Gson().toJson(config));

        loRaConfig = config;

        return editor.commit();
    }

    /**
     * Load a stored config or return a default one.
     */
    private LoRaConfig loadConfig() {
        SharedPreferences prefs = this.getSharedPreferences(PREF_CONF_KEY, Context.MODE_PRIVATE);
        if (prefs.getString(PREF_CONF_KEY, null) != null) {
            return new Gson().fromJson(prefs.getString(PREF_CONF_KEY, null), LoRaConfig.class);
        }

        return new LoRaConfig();
    }
}
