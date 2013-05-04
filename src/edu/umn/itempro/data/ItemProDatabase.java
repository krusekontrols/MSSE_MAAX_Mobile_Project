package edu.umn.itempro.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ItemProDatabase extends SQLiteOpenHelper {
	
	private static final String DEBUG_TAG = "ItemProDatabase";
    private static final int DB_VERSION = 3;
    
    private static final String DB_NAME = "contact_data";
    
    public static final String TABLE_CONTACT = "Contact";
    public static final String ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_PHONE = "phone";
    public static final String COL_TITLE = "title";
    public static final String COL_EMAIL = "email";
    public static final String COL_TWITTERID = "twitterId";
    private static final String CREATE_TABLE_CONTACT = "create table " + TABLE_CONTACT
    + " (" + ID + " integer primary key autoincrement, " + COL_NAME
    + " text not null, " + COL_PHONE + " text not null, " + COL_TITLE + " text not null, "
    + COL_EMAIL + " text not null, " + COL_TWITTERID + " text not null)";
    private static final String DB_SCHEMA = CREATE_TABLE_CONTACT;
    
    public ItemProDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DB_SCHEMA);
		seedData(db);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(DEBUG_TAG, "Upgrading database. Existing contents will be lost. ["
	            + oldVersion + "]->[" + newVersion + "]");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
	    onCreate(db);
		
	}
	
	private void seedData(SQLiteDatabase db) {
		db.execSQL("insert into Contact (name, email, title, phone, twitterId) values ('Malcom Reynolds', 'mal@serenity.com', 'Captain', '612-555-1234', 'malcomreynolds');");
		db.execSQL("insert into Contact (name, email, title, phone, twitterId) values ('Zoe Washburne', 'zoe@serenity.com', 'First Mate', '612-555-5678', 'zoewashburne');");
		db.execSQL("insert into Contact (name, email, title, phone, twitterId) values ('Hoban Washburne', 'wash@serenity.com', 'Pilot', '612-555-9012', 'wash');");
		db.execSQL("insert into Contact (name, email, title, phone, twitterId) values ('Jayne Cobb', 'jayne@serenity.com', 'Muscle', '612-555-3456', 'heroofcanton');");
		db.execSQL("insert into Contact (name, email, title, phone, twitterId) values ('Kaylee Frye', 'kaylee@serenity.com', 'Engineer', '612-555-7890', 'kaylee');");
		db.execSQL("insert into Contact (name, email, title, phone, twitterId) values ('Simon Tam', 'simon@serenity.com', 'Doctor', '612-555-4321', 'simontam');");
		db.execSQL("insert into Contact (name, email, title, phone, twitterId) values ('River Tam', 'river@serenity.com', 'Doctor''s Sister', '612-555-8765', 'miranda');");
		db.execSQL("insert into Contact (name, email, title, phone, twitterId) values ('Shepherd Book', 'shepherd@serenity.com', 'Shepherd', '612-555-2109', 'shepherdbook');");
    }
	

}
