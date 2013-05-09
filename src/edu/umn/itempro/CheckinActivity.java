package edu.umn.itempro;

import android.app.Activity;
import android.content.Intent;
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
    	Toast msg = Toast.makeText(getBaseContext(), "You are connected to Best Buy", Toast.LENGTH_LONG);                                                                 
    	msg.show();
    	Intent intent = new Intent(getApplicationContext(), ItemList.class);
    	startActivity(intent);
    }                                
    });

    b2.setOnClickListener(new OnClickListener()
    {
    public void onClick(View v)
    {
    	Toast msg = Toast.makeText(getBaseContext(), "You are connected to Target", Toast.LENGTH_LONG);
    	msg.show();
    	Intent intent = new Intent(getApplicationContext(), ItemList.class);
    	startActivity(intent); 
    }
    });
  }
  
  @Override
  public void onBackPressed() {
  }

}
