package edu.umn.itempro;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ContactDetailsActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.details);
        
        TextView textView = (TextView)findViewById(R.id.details_text);
        String color = getIntent().getStringExtra("color");
        textView.setText(color);
    }
}