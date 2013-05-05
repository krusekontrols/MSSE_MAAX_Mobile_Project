package edu.umn.itempro.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ItemProDatabase extends SQLiteOpenHelper {
	
	private static final String DEBUG_TAG = "ItemProDatabase";
    private static final int DB_VERSION = 3;
    
    private static final String DB_NAME = "itempro_data";
    
    public static final String TABLE_USER = "User";
    public static final int COL_UID_KEY = 0; // "user_id";
    public static final String COL_UID = "user_id";
    public static final String COL_PWD = "password";
    
    public static final String TABLE_ITEM = "Item";
    public static final int COL_IID = 0;
    public static final String COL_INAME = "item_name";
    
    public static final String TABLE_STATUS = "Status";
    //public static final String COL_SIID = "item_id";
    public static final int COL_TARGET = 0; //"TargetStatus";
    public static final int COL_BESYBUY = 0; //"BestBuyStatus";
    
    public static final String TABLE_PROMOS = "Promotions";
    //public static final String COL_PIID = "item_id";
    public static final int COL_TARGETP =  0; //"TargetPromos";
    public static final int COL_BESYBUYP =  0; //"BestBuyPromos";
    
    public static final String TABLE_PROMO_DETAIL = "PromotionDetails";
    //public static final String COL_PDIID = "item_id";
    public static final String COL_IDETAIL = "ItemDetail";
    public static final String COL_ORIGPRICE = "OriginalPrice";
    public static final String COL_PROMOPRICE = "PromotionPrice";
    
    
    private static final String CREATE_TABLE_USER = "create table " + TABLE_USER
    + " (" + COL_UID_KEY + " integer primary key autoincrement, " + COL_UID + " text not null, " + COL_PWD + " text not null)";
    
    private static final String CREATE_TABLE_ITEM = "create table " + TABLE_ITEM
    	    + " (" + COL_IID + " integer primary key autoincrement, " + COL_INAME
    	    + " text not null)";
   
    private static final String CREATE_TABLE_STATUS = "create table " + TABLE_STATUS
    	    + " (" + COL_IID + " integer, " + COL_TARGET + " integer, " 
    		+ COL_BESYBUY + " integer)";

    private static final String CREATE_TABLE_PROMOS = "create table " + TABLE_PROMOS
    	    + " (" + COL_IID + " integer, " + COL_TARGETP + " integer, " 
    		+ COL_BESYBUYP + " integer)";
    
    private static final String CREATE_TABLE_PROMO_DETAIL = "create table " + TABLE_PROMO_DETAIL
    	    + " (" + COL_IID + " integer, " + COL_IDETAIL + "  text not null, " 
    		+ COL_ORIGPRICE +  " text not null, " + COL_PROMOPRICE +" text not null )";
    
   // private static final String DB_SCHEMA = CREATE_TABLE_CONTACT;
    
    public ItemProDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_USER);
		db.execSQL(CREATE_TABLE_ITEM);
		db.execSQL(CREATE_TABLE_STATUS);
		db.execSQL(CREATE_TABLE_PROMOS);
		db.execSQL(CREATE_TABLE_PROMO_DETAIL);
		
		seedData(db);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(DEBUG_TAG, "Upgrading database. Existing contents will be lost. ["
	            + oldVersion + "]->[" + newVersion + "]");
	    db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_USER);
	    onCreate(db);
		
	}
	
	private void seedData(SQLiteDatabase db) {
		db.execSQL("insert into " + TABLE_USER + "  (user_id, password) values ('Malcom Reynolds', 'malcomreynolds');");
/*		db.execSQL("insert into Contact (name, email, title, phone, twitterId) values ('Zoe Washburne', 'zoe@serenity.com', 'First Mate', '612-555-5678', 'zoewashburne');");
		db.execSQL("insert into Contact (name, email, title, phone, twitterId) values ('Hoban Washburne', 'wash@serenity.com', 'Pilot', '612-555-9012', 'wash');");
		db.execSQL("insert into Contact (name, email, title, phone, twitterId) values ('Jayne Cobb', 'jayne@serenity.com', 'Muscle', '612-555-3456', 'heroofcanton');");
		db.execSQL("insert into Contact (name, email, title, phone, twitterId) values ('Kaylee Frye', 'kaylee@serenity.com', 'Engineer', '612-555-7890', 'kaylee');");
		db.execSQL("insert into Contact (name, email, title, phone, twitterId) values ('Simon Tam', 'simon@serenity.com', 'Doctor', '612-555-4321', 'simontam');");
		db.execSQL("insert into Contact (name, email, title, phone, twitterId) values ('River Tam', 'river@serenity.com', 'Doctor''s Sister', '612-555-8765', 'miranda');");
		db.execSQL("insert into Contact (name, email, title, phone, twitterId) values ('Shepherd Book', 'shepherd@serenity.com', 'Shepherd', '612-555-2109', 'shepherdbook');");
 */   }
	

}
