package edu.umn.itempro;

import android.app.Activity;
import android.os.Bundle;

/** A Class for User to Checkin a store
*
*/

public class CheckinActivity extends Activity {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkin);
    }
    
    @Override
    public void onBackPressed() {
    }
}
