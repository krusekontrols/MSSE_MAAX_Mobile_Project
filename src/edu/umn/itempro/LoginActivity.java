package edu.umn.itempro;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.umn.itempro.data.ItemProDatabase;
import edu.umn.itempro.data.ItemProProvider;

/** A Class for Login screen
*
*/

public class LoginActivity extends Activity {
      String userName, passWord;
      EditText username, password;
      Button login;
      Button clear;
      public static final SharedPreferences pref = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(loginListener); 
        
        clear = (Button)findViewById(R.id.clear);

        clear.setOnClickListener(clearListener);
        
    }
    
    @Override
    public void onBackPressed() {
    }
    
    private OnClickListener loginListener = new OnClickListener() {
      public void onClick(View v) {
            String usernameFromInput = username.getText().toString();
            String passwordFromInput = password.getText().toString();
            String passwordFromDataSource = null;
            String projection[] = { ItemProDatabase.COL_PWD};
    	    Cursor userCursor = getContentResolver().query(
    	           ItemProProvider.getContentURI(ItemProDatabase.TABLE_USER),
    	              projection, ItemProDatabase.COL_UID + " = ?", new String[]{usernameFromInput}, null);
    	    if (userCursor.moveToFirst()) {
    	    	passwordFromDataSource = userCursor.getString(0);
    	    }
    	    
    	    //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            //SharedPreferences.Editor editor = pref.edit();
            //editor.putString("user_id", usernameFromInput);
    	    
            if(passwordFromInput != null && passwordFromInput.equals(passwordFromDataSource)){
                  Toast.makeText(getApplicationContext(), "Login Successfully !!!", Toast.LENGTH_LONG).show();
                  Intent intent = new Intent(getApplicationContext(), CheckinActivity.class);
              	  startActivity(intent); 
                  
            }else {
            	Toast.makeText(getApplicationContext(), "Login Not Successful !!!", Toast.LENGTH_LONG).show();   
            }
            clearFields();	
      }
    };
    
    private OnClickListener clearListener = new OnClickListener() {
        public void onClick(View v) {
        	clearFields();          
        }
      };
     
    private void clearFields() {
    	username.setText(null);
        password.setText(null);
        username.requestFocus();
    }
    
}