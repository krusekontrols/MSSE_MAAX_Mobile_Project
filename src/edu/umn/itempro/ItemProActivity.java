package edu.umn.itempro;

import edu.umn.itempro.*;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ItemProActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView textView = (TextView)findViewById(R.id.details_text);
        String color = getIntent().getStringExtra("color");
        textView.setText(color);

    }
}