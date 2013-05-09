package edu.umn.itempro;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import edu.umn.itempro.data.Category;
import edu.umn.itempro.data.Item;
import edu.umn.itempro.data.ItemProDatabase;
import edu.umn.itempro.data.ItemProProvider;

public class CreateItem extends Activity{
	private EditText nameText;
	private Spinner spinner;
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createitem);
        
        Button createButton = (Button) findViewById(R.id.create);
        Button cancelButton = (Button) findViewById(R.id.cancel);
        spinner = (Spinner) findViewById(R.id.category);
      	listCategories();
      	
        createButton.setOnClickListener(new View.OnClickListener() {

        	public void onClick(View view) {
        		if (spinner.getChildCount() == 0)
        		  alertNoCategories();
        		else
        		  handleCreateClick();
        	}
          
        });
        
        cancelButton.setOnClickListener(new View.OnClickListener() {

        	public void onClick(View view) {
        	    setResult(RESULT_CANCELED);
        	    finish();
        	}
          
        });
        
        Button edit = (Button) findViewById(R.id.edit_categories);
        
        edit.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		handleEditCategoriesClick();
        	} });

        nameText = (EditText) findViewById(R.id.name);
        
        Intent intent = getIntent();
        if (intent.getAction().equals(Intent.ACTION_EDIT))
        	prepareForEdit(intent);
        else 
        	prepareForCreate(intent);
    }
    
    private void handleCreateClick(){
		ContentValues values = new ContentValues();
		values.put(Item.NAME, nameText.getText().toString());
		values.put(Item.CATEGORY, spinner.getSelectedItemId());
		
    	if (Intent.ACTION_INSERT.equals(getIntent().getAction())){
    		getContentResolver().insert(getIntent().getData(), values);
			
		} else { //edit existing item
			getContentResolver().update(getIntent().getData(), values, null, null);
		}
	    finish();
    }
    
    private void prepareForEdit(Intent intent){
    	Cursor cursor = getContentResolver().query(intent.getData(), null, null, null, null);
    	cursor.moveToFirst();
    	nameText.append(cursor.getString(cursor.getColumnIndex(Item.NAME)));
    	selectCategory(cursor.getLong(cursor.getColumnIndex(Item.CATEGORY)));
    	setTitle(R.string.edit_item_title);
    	cursor.close();
    }
    
    private void prepareForCreate(Intent intent){
    	//Automatically capitalize first letter:
    	Bundle extras = intent.getExtras();
    	if (extras != null){
    		String name = extras.getString("name");
    		if (name.length() > 0)
    			nameText.append(name.substring(0, 1).toUpperCase());
    		if (name.length() > 1)
    			nameText.append(name.subSequence(1, name.length()));  	
    		if (extras.containsKey("category"))
    			selectCategory(intent.getExtras().getLong("category"));
    	}
    }
    
    private void selectCategory(long id){
		for (int i = 0; i < spinner.getCount(); i++){
			if (spinner.getItemIdAtPosition(i) == id)
				spinner.setSelection(i);
		}	
    }
    
    private void listCategories() {
    	Cursor cursor = managedQuery(ItemProProvider.getContentURI(ItemProDatabase.TABLE_CATEGORY), null, null, null, null);
        String[] from = new String[]{Category.NAME};
        int[] to = new int[]{android.R.id.text1};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor, from, to);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
	}

	private void handleEditCategoriesClick(){
		Intent intent = new Intent(Intent.ACTION_EDIT, ItemProProvider.getContentURI(ItemProDatabase.TABLE_CATEGORY));
		startActivity(intent);
    }

    private void alertNoCategories(){
    	new AlertDialog.Builder(this)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle(R.string.alert_no_categories_text)
        .setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        })       
        .create().show();
    }

}
