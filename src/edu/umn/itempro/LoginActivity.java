package edu.umn.itempro;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/** A Class for Login screen
*
*/

public class LoginActivity extends Activity {
      String userName, passWord;
      EditText username, password;
      Button login;    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);

        login.setOnClickListener(loginListener);  
    }
    private OnClickListener loginListener = new OnClickListener() {
      public void onClick(View v) {
                   
            if(username.getText().toString().equals("stud1") && password.getText().toString().equals("stud1")){
                  Toast.makeText(getApplicationContext(), "Login Successfully !!!", Toast.LENGTH_LONG).show();      
            }else
                  Toast.makeText(getApplicationContext(), "Login Not Successful !!!", Toast.LENGTH_LONG).show();                           
      }
    };
}
