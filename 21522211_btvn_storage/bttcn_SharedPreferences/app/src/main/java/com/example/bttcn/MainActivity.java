package com.example.bttcn;
import java.util.Date;
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

public class MainActivity extends Activity implements OnClickListener {
    Button btnSimplePref;
    Button btnFancyPref;
    TextView txtCaption1;
    Boolean fancyPrefChosen = false;
    View  myLayout1Vertical;

    final int mode = Activity.MODE_PRIVATE;
    final String MYPREFS = "MyPreferences_001";
    // create a reference to the shared preferences object
    SharedPreferences mySharedPreferences;
    // obtain an editor to add data to my SharedPreferences object
    SharedPreferences.Editor myEditor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        myLayout1Vertical = (View)findViewById(R.id.linLayout1Vertical);


        txtCaption1 = (TextView) findViewById(R.id.txtCaption1);
        txtCaption1.setText("This is a sample line \n"
                + "suggesting the way the UI looks \n"
                + "after you choose your preference");

        // create a reference to the shared preferences object
        mySharedPreferences = getSharedPreferences(MYPREFS, 0);
        // obtain an editor to add data to (my)SharedPreferences object
        myEditor = mySharedPreferences.edit();
        // has a Preferences file been already created?
        if (mySharedPreferences != null
                && mySharedPreferences.contains("backColor")) {
            // object and key found, show all saved values
            applySavedPreferences();
        } else {
            Toast.makeText(getApplicationContext(),
                    "No Preferences found", 1).show();
        }

        btnSimplePref = (Button) findViewById(R.id.btnPrefSimple);
        btnSimplePref.setOnClickListener(this);
        btnFancyPref = (Button) findViewById(R.id.btnPrefFancy);
        btnFancyPref.setOnClickListener(this);

    }// onCreate

    public void onClick(View v) {
        // clear all previous selections
        myEditor.clear();

        // what button has been clicked?
        if (v.getId() == btnSimplePref.getId()) {
            myEditor.putInt("backColor", Color.BLACK);// black background
            myEditor.putInt("textSize", 12); 		  // humble small font
        } else { // case btnFancyPref
            myEditor.putInt("backColor", Color.BLUE); // fancy blue
            myEditor.putInt("textSize", 20); 		  // fancy big
            myEditor.putString("textStyle", "bold");  // fancy bold
            myEditor.putInt("layoutColor", Color.GREEN);//fancy green
        }
        myEditor.commit();
        applySavedPreferences();
    }

    @Override
    protected void onPause() {
        // warning: activity is on its last state of visibility!
        // It's on the edge of been killed! Better save all current
        // state data into Preference object (be quick!)
        myEditor.putString("DateLastExecution", new Date().toLocaleString());
        myEditor.commit();
        super.onPause();
    }

    public void applySavedPreferences() {
        // extract the  pairs, use default param for missing data
        int backColor = mySharedPreferences.getInt("backColor",Color.BLACK);
        int textSize = mySharedPreferences.getInt("textSize", 12);
        String textStyle = mySharedPreferences.getString("textStyle", "normal");
        int layoutColor = mySharedPreferences.getInt("layoutColor", Color.DKGRAY);
        String msg = "color " + backColor + "\n" + "size " + textSize
                + "\n" + "style " + textStyle;
        Toast.makeText(getApplicationContext(), msg, 1).show();

        txtCaption1.setBackgroundColor(backColor);
        txtCaption1.setTextSize(textSize);
        if (textStyle.compareTo("normal")==0){
            txtCaption1.setTypeface(Typeface.SERIF,Typeface.NORMAL);
        }
        else {
            txtCaption1.setTypeface(Typeface.SERIF,Typeface.BOLD);
        }
        myLayout1Vertical.setBackgroundColor(layoutColor);
    }// applySavedPreferences

}