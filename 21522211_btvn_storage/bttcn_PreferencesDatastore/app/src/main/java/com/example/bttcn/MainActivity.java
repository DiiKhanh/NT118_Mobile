package com.example.bttcn;
import java.util.Date;
import java.util.Map;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava2.RxDataStore;

import io.reactivex.Single;


public class MainActivity extends Activity implements OnClickListener {
    RxDataStore<Preferences> dataStoreRX; //Top Part

    Button btnSimplePref;
    Button btnFancyPref;
    TextView txtCaption1;
    Boolean fancyPrefChosen = false;
    View  myLayout1Vertical;

    final int mode = Activity.MODE_PRIVATE;
    final String MYPREFS = "MyPreferences_001";
    // create a reference to the shared preferences objec
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        myLayout1Vertical = (View)findViewById(R.id.linLayout1Vertical);

        dataStoreRX = new RxPreferenceDataStoreBuilder(this,"preference datastore").build();
        txtCaption1 = (TextView) findViewById(R.id.txtCaption1);
        txtCaption1.setText("This is a sample line \n"
                + "suggesting the way the UI looks \n"
                + "after you choose your preference");

        // create a reference to the shared preferences object
        btnSimplePref = (Button) findViewById(R.id.btnPrefSimple);
        btnSimplePref.setOnClickListener(this);
        btnFancyPref = (Button) findViewById(R.id.btnPrefFancy);
        btnFancyPref.setOnClickListener(this);
        putStringValue("backColor", String.valueOf(Color.rgb(9,139,0)));// black background
        putStringValue("textSize", String.valueOf(12));
        putStringValue("layoutColor", String.valueOf(Color.BLACK));// humble small font
        applySavedPreferences();
    }// onCreate
    public boolean putStringValue(String Key, String value){
        Preferences pref_error = new Preferences() {
            @Nullable
            @Override
            public <T> T get(@NonNull Preferences.Key<T> key) {
                return null;
            }

            @Override
            public <T> boolean contains(@NonNull Preferences.Key<T> key) {
                return false;
            }

            @NonNull
            @Override
            public Map<Preferences.Key<?>, Object> asMap() {
                return null;
            }};
        boolean returnvalue;
        Preferences.Key<String> PREF_KEY = PreferencesKeys.stringKey(Key);
        Single<Preferences> updateResult =  dataStoreRX.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(PREF_KEY, value);
            return Single.just(mutablePreferences);
        }).onErrorReturnItem(pref_error);

        returnvalue = updateResult.blockingGet() != pref_error;
        return returnvalue;
    }
    String getStringValue(String Key) {
        Preferences.Key<String> PREF_KEY = PreferencesKeys.stringKey(Key);
        Single<String> value = dataStoreRX.data().firstOrError().map(prefs -> prefs.get(PREF_KEY)).onErrorReturnItem("null");
        return value.blockingGet();
    }

    public void onClick(View v) {
        // clear all previous selections


        // what button has been clicked?
        if (v.getId() == btnSimplePref.getId()) {
           putStringValue("backColor", String.valueOf(Color.BLACK));// black background
            putStringValue("textSize", String.valueOf(12));
            putStringValue("layoutColor", String.valueOf(Color.DKGRAY));// humble small font
        } else if(v.getId() == btnFancyPref.getId()) { // case btnFancyPref
            putStringValue("backColor", String.valueOf(Color.BLUE)); // fancy blue
            putStringValue("textSize", String.valueOf(20)); 		  // fancy big
            putStringValue("textStyle", "bold");  // fancy bold
            putStringValue("layoutColor", String.valueOf(Color.GREEN));//fancy green
        }

        applySavedPreferences();
    }

    @Override
    protected void onPause() {
        // warning: activity is on its last state of visibility!
        // It's on the edge of been killed! Better save all current
        // state data into Preference object (be quick!)
        super.onPause();
    }

    public void applySavedPreferences() {
        // extract the  pairs, use default param for missing data
         String backColor = getStringValue("backColor");
        String textSize = getStringValue("textSize");
        String textStyle = getStringValue("textStyle");
        String layoutColor = getStringValue("layoutColor");
        String msg = "color " + backColor + "\n" + "size " + textSize
                + "\n" + "style " + textStyle;
        Toast.makeText(getApplicationContext(), msg, 1).show();

        txtCaption1.setBackgroundColor(Integer.parseInt(backColor));
        txtCaption1.setTextSize(Float.parseFloat(textSize));
        if (textStyle.compareTo("normal")==0){
            txtCaption1.setTypeface(Typeface.SERIF,Typeface.NORMAL);
        }
        else {
            txtCaption1.setTypeface(Typeface.SERIF,Typeface.BOLD);
        }
        myLayout1Vertical.setBackgroundColor(Integer.parseInt(layoutColor));
    }// applySavedPreferences

}