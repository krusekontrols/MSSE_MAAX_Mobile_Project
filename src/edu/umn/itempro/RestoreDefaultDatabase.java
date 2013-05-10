package edu.umn.itempro;

import java.util.Random;

import android.app.Activity;
import android.content.ContentValues;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.os.Bundle;
import edu.umn.itempro.data.Category;
import edu.umn.itempro.data.Item;
import edu.umn.itempro.data.ItemProDatabase;
import edu.umn.itempro.data.ItemProProvider;

public class RestoreDefaultDatabase extends Activity{

	public static final String RESTORE_DEFAULT = "edu.umn.itempro.action.RESTORE_DEFAULT";
	public static final String[] PROMOTION_CODE = {"Not in Stock", "No Promotion Avaiable", "Buy 2 Get 1 free", "Buy 1 Get 1 at Half Price", "Buy 1 Get 1 Free",
		"Buy 1 Get 2 Free", "5% Off", "10% Off", "15% Off", "20% Off", "25% Off", "30% Off", "35% Off", "40% Off", "45% Off", "50% Off", "55% Off", "60% Off", 
		"65% Off", "70% Off", "75% Off"};
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
				String categoryName = null;
				try {
					eventType = xmlParser.getEventType();
					while (eventType != XmlResourceParser.END_DOCUMENT){
						if (eventType == XmlResourceParser.START_TAG){
							String tagName = xmlParser.getName();
							if (tagName.equals("category")){

								for (int i = 0; i < xmlParser.getAttributeCount(); i++){
									if (xmlParser.getAttributeName(i).equals("name")){
										categoryName = xmlParser.getAttributeValue(i);
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
										Random rand =new Random();
										Random rand1 = new Random();
										if(!"Electronics".equalsIgnoreCase(categoryName) && !"Video Games".equalsIgnoreCase(categoryName))
										{
											values.put(Item.BESTBUYPROMOTION, PROMOTION_CODE[0]);
										}
										else
										{
											values.put(Item.BESTBUYPROMOTION, PROMOTION_CODE[rand.nextInt(20) + 1]);
										}
										values.put(Item.TARGETPROMOTION, PROMOTION_CODE[rand1.nextInt(21)]);
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
