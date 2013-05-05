package edu.umn.itempro;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import edu.umn.itempro.data.ItemProProvider;
import edu.umn.itempro.data.ItemProDatabase;

/** A Class for Login screen
*
*/

public class LoginActivity extends Activity {
      String userName, passWord;
      EditText username, password;
      Button login;    

      private static final int ITEM_LIST_LOADER = 0x01;
      private SimpleCursorAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);

        login.setOnClickListener(loginListener);  
        
        // mek from contactlist app:
        String[] uiBindFrom = { ItemProDatabase.COL_UID, ItemProDatabase.COL_PWD };
	    int[] uiBindTo = { R.id.username, R.id.password };
	    
	   // getLoaderManager().initLoader(ITEM_LIST_LOADER, null, this);
	    
	    adapter = new ContactAdapter(
	            getApplicationContext(), R.layout.login,
	            null, uiBindFrom, uiBindTo,
	            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
	    	//setListAdapter(adapter);
	 
        
    }
    private OnClickListener loginListener = new OnClickListener() {
      public void onClick(View v) {
                   
            if(username.getText().toString().equals("stud1") && password.getText().toString().equals("stud1")){
                  Toast.makeText(getApplicationContext(), "Login Successfully !!!", Toast.LENGTH_LONG).show();      
            }else
                  Toast.makeText(getApplicationContext(), "Login Not Successful !!!", Toast.LENGTH_LONG).show();                           
      }
    };
    
    /* We need to provide a custom adapter in order to use a custom list item view.
	 */
	public class ContactAdapter extends SimpleCursorAdapter {
	
		public ContactAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags)
		{
			super(context, layout, c, from, to, flags);
		}
	
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View item = inflater.inflate(R.layout.details, parent, false);
			return item;
		}
	}
}