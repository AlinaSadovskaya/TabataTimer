package com.lab2.tabatatimer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatDelegate;

import com.lab2.tabatatimer.DataBase.DataBaseHelper;
import com.lab2.tabatatimer.Model.TimerModel;

import java.util.List;
import java.util.Locale;

public class Settings extends PreferenceActivity {
    SharedPreferences sp;
    int language_def;
    int font_def;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp.getBoolean("theme", true)) {
            setTheme(R.style.Theme_AppCompat);
        }
        String font = sp.getString("fontSize", "Малый");
        String listValue = sp.getString("test_lang", "Английский");
        Configuration configuration = new Configuration();

        Locale locale;
        if (listValue.equals("English") || listValue.equals("Английский")) {
            font_def = 1;
            locale = new Locale("en");
        } else {
            font_def = 0;
            locale = new Locale("ru");
        }
        Locale.setDefault(locale);
        configuration.locale = locale;

        if (font.equals("Малый") || font.equals("Small")) {
            language_def = 0;
            configuration.fontScale = (float) 0.85;
        } else if (font.equals("Нормальный") || font.equals("Normal")) {
            language_def = 1;
            configuration.fontScale = (float) 1;
        } else {
            language_def = 2;
            configuration.fontScale = (float) 1.15;
        }

        getBaseContext().getResources().updateConfiguration(configuration, null);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        super.onCreate(savedInstanceState);
    }


    public static class MyPreferenceFragment extends PreferenceFragment {

        private DataBaseHelper db;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            db = App.getInstance().getDatabase();
            addPreferencesFromResource(R.xml.settings);
            Preference button = findPreference("DeleteAll");
            ListPreference language = (ListPreference) findPreference("test_lang");
            Preference theme = findPreference("theme");
            ListPreference font = (ListPreference) findPreference("fontSize");
            font.setValueIndex(((Settings) getActivity()).language_def);
            language.setValueIndex(((Settings) getActivity()).font_def);
            theme.setOnPreferenceChangeListener(this::onThemeChange);
            language.setOnPreferenceChangeListener(this::onLanguageChange);
            button.setOnPreferenceClickListener(this::onDeleteClick);
            font.setOnPreferenceChangeListener(this::onFontChange);
        }

        private boolean onThemeChange(Preference preference, Object newValue) {
            if ((boolean) newValue) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            getActivity().recreate();
            return true;
        }

        private boolean onLanguageChange(Preference preference, Object newValue) {
            Locale locale;
            if (newValue.toString().equals("English") || newValue.toString().equals("Английский")) {
                locale = new Locale("en");
            } else {
                locale = new Locale("ru");
            }
            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.locale = locale;
            getActivity().getResources().updateConfiguration(configuration, null);
            getActivity().recreate();
            return true;
        }

        private boolean onDeleteClick(Preference preference) {
            List<TimerModel> timerList = db.timerDao().getAll();
            for (int j = 0; j < timerList.size(); j++) {
                db.timerDao().delete(timerList.get(j));
            }
            Intent intent = new Intent();
            getActivity().setResult(RESULT_OK, intent);
            getActivity().finish();
            return true;
        }

        private boolean onFontChange(Preference preference, Object newValue) {
            Configuration configuration = getResources().getConfiguration();
            if (newValue.toString().equals("Малый") || newValue.toString().equals("Small")) {
                configuration.fontScale = (float) 0.85;
            } else if (newValue.toString().equals("Нормальный") || newValue.toString().equals("Normal")) {
                configuration.fontScale = (float) 1;
            } else {
                configuration.fontScale = (float) 1.15;
            }
            DisplayMetrics metrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.fontScale * metrics.density;
            getActivity().getBaseContext().getResources().updateConfiguration(configuration, metrics);
            getActivity().recreate();
            return true;
        }
    }
}