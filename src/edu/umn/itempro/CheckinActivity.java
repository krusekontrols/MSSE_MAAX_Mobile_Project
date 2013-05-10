package edu.umn.itempro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/** A Class for User to Checkin to a particular store
 * We are considering two stores: Best Buy, Target 
*
*/

public class CheckinActivity extends Activity {

  
	public static final String CHECKED_IN_STORE = "CHECKED_IN_STORE";
	public static final String BEST_BUY = "Best Buy";
	public static final String 	TARGET = "Target";
	Button b1,b2;

	/** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.checkin);

    b1 = (Button) findViewById(R.id.button1);
    b2 = (Button) findViewById(R.id.button2);

    b1.setOnClickListener(new OnClickListener()
    {
    public void onClick(View v)
    {
    	
    	Toast msg = Toast.makeText(getBaseContext(), "You are connected to " + BEST_BUY, Toast.LENGTH_LONG);                                                                 
    	msg.show();
    	changePreference(BEST_BUY);
    	
    	Intent intent = new Intent(getApplicationContext(), ItemList.class);
    	startActivity(intent);
    }                                
    });

    b2.setOnClickListener(new OnClickListener()
    {
    public void onClick(View v)
    {
    	Toast msg = Toast.makeText(getBaseContext(), "You are connected to " + TARGET, Toast.LENGTH_LONG);
    	msg.show();
    	changePreference(TARGET);
    	Intent intent = new Intent(getApplicationContext(), ItemList.class);
    	startActivity(intent); 
    }
    });
  }
  
  @Override
  public void onBackPressed() {
  }
  
  private void changePreference(String store){
  	SharedPreferences preferences = getSharedPreferences(CheckinActivity.class.getName(), Context.MODE_PRIVATE);
  	SharedPreferences.Editor editor = preferences.edit();
  	editor.putString(CheckinActivity.CHECKED_IN_STORE, store);
  	editor.commit();
  }

}
