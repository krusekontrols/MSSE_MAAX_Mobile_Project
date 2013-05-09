package edu.umn.itempro;

import edu.umn.itempro.data.Category;
import edu.umn.itempro.data.Item;
import edu.umn.itempro.data.ItemProDatabase;
import edu.umn.itempro.data.ItemProProvider;
import android.app.Activity;
import android.content.ContentValues;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.os.Bundle;

public class RestoreDefaultDatabase extends Activity{

	public static final String RESTORE_DEFAULT = "edu.umn.itempro.action.RESTORE_DEFAULT";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restoredefault);
		new Thread() {
			public void run() {
				
				
				getContentResolver().delete(ItemProProvider.getContentURI(ItemProDatabase.TABLE_ITEM), null, null);
				getContentResolver().delete(ItemProProvider.getContentURI(ItemProDatabase.TABLE_CATEGORY), null, null);

				XmlResourceParser xmlParser = getResources().getXml(R.xml.defaultshoppinglist);
				int eventType;
				long categoryID = -1;
				try {
					eventType = xmlParser.getEventType();
					while (eventType != XmlResourceParser.END_DOCUMENT){
						if (eventType == XmlResourceParser.START_TAG){
							String tagName = xmlParser.getName();
							if (tagName.equals("category")){

								for (int i = 0; i < xmlParser.getAttributeCount(); i++){
									if (xmlParser.getAttributeName(i).equals("name")){
										String categoryName = xmlParser.getAttributeValue(i);
										ContentValues values = new ContentValues();
										values.put(Category.NAME, categoryName);
										getContentResolver().insert(ItemProProvider.getContentURI(ItemProDatabase.TABLE_CATEGORY), values);
										//Retrieve the auto-generated ID:
										Cursor cursor = getContentResolver().query(ItemProProvider.getContentURI(ItemProDatabase.TABLE_CATEGORY), null, Category.NAME +" = '"+categoryName+"'", null, null);
										cursor.moveToFirst();
										categoryID = cursor.getLong(cursor.getColumnIndex(Category._ID));
										break;
									}
								}
							} else if (tagName.equals("item")){
								for (int i = 0; i < xmlParser.getAttributeCount(); i++){
									if (xmlParser.getAttributeName(i).equals("name")){
										String itemName = xmlParser.getAttributeValue(i);
										ContentValues values = new ContentValues();
										values.put(Item.NAME, itemName);
										values.put(Item.CATEGORY, categoryID);
										getContentResolver().insert(ItemProProvider.getContentURI(ItemProDatabase.TABLE_ITEM), values);
										break;
									}
								}
							}
						}
						eventType = xmlParser.next();
					}
				} catch (Exception e) {
					//Log.w(TAG, "Error parsing default shopping list XML.");
				} finally {
					finish();
				}
			}
		}.start();
	}    
}
