package edu.umn.itempro;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import edu.umn.itempro.data.Item;

public class ConfirmEdit extends Activity{
	public static final String SET_EDIT_MODE = "edu.umn.itempro.action.SET_EDIT_MODE";
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.confirmedit);
        
        Button okButton = (Button) findViewById(R.id.ok_to_edit);
        Button cancelButton = (Button) findViewById(R.id.cancel_edit);
        
        okButton.setOnClickListener(new View.OnClickListener() {

        	public void onClick(View view) {
        		setResult(RESULT_OK);
        	    finish();
        	}
          
        });
        
        cancelButton.setOnClickListener(new View.OnClickListener() {

        	public void onClick(View view) {
        		setResult(RESULT_CANCELED);
        	    finish();
        	}
          
        });
    }
    
    
    
}