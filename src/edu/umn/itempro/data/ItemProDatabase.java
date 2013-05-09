package edu.umn.itempro.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ItemProDatabase extends SQLiteOpenHelper {
	
	private static final String DEBUG_TAG = "ItemProDatabase";
    private static final int DB_VERSION = 5;
    
    private static final String DB_NAME = "itempro_data";
    
    public static final String TABLE_USER = "User";
    public static final String COL_UID_KEY = "uid"; // "user_id";
    public static final String COL_UID = "user_id";
    public static final String COL_PWD = "password";
    
    public static final String TABLE_ITEM = "Item";
    public static final String COL_IID = "iid";
    //public static final String COL_INAME = "item_name";
    
    
    public static final String TABLE_CATEGORY = "Category";
    
    public static final String TABLE_STATUS = "Status";
    public static final String COL_SIID = "item_id";
    public static final String COL_TARGET = "Target"; //"TargetStatus";
    public static final String COL_BESYBUY ="BestBuy"; //"BestBuyStatus";
    
    public static final String TABLE_PROMOS = "Promotions";
    public static final String COL_PIID = "item_id";
    public static final String COL_TARGETP = "Target"; //"TargetPromos";
    public static final String COL_BESYBUYP = "BestBuy"; //"BestBuyPromos";
    
    public static final String TABLE_PROMO_DETAIL = "PromotionDetails";
    public static final String COL_PDIID = "item_id";
    public static final String COL_IDETAIL = "ItemDetail";
    public static final String COL_ORIGPRICE = "OriginalPrice";
    public static final String COL_PROMOPRICE = "PromotionPrice";
    
    
    private static final String CREATE_TABLE_USER = "create table " + TABLE_USER
    + " (" + COL_UID_KEY + " integer primary key autoincrement, " + COL_UID + " text not null, " + COL_PWD + " text not null)";
    
    private static final String CREATE_TABLE_ITEM =   "CREATE TABLE " + TABLE_ITEM + " ("
	+ Item._ID + " INTEGER PRIMARY KEY,"
	+ Item.NAME + " TEXT,"
	+ Item.AMOUNT + " FLOAT,"
	+ Item.UNIT + " TEXT,"
	+ Item.ONLIST + " INTEGER,"
	+ Item.CATEGORY + " INTEGER"
	+");";
    
    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY + " ("
    + Category._ID + " INTEGER PRIMARY KEY,"
    + Category.NAME + " TEXT"
    +");";
    
    private static final String CREATE_TABLE_STATUS = "create table " + TABLE_STATUS
    	    + " (" + COL_SIID + " integer not null, " + COL_TARGET + " text not null, "
    		+ COL_BESYBUY + " text not null)";

    private static final String CREATE_TABLE_PROMOS = "create table " + TABLE_PROMOS
    	    + " (" + COL_PIID + " integer not null, " + COL_TARGETP + " text not null, "
    		+ COL_BESYBUYP + " text not null)";
    
    private static final String CREATE_TABLE_PROMO_DETAIL = "create table " + TABLE_PROMO_DETAIL
    	    + " (" + COL_PDIID + " integer not null, " + COL_IDETAIL + "  text not null, " 
    		+ COL_ORIGPRICE +  " text not null, " + COL_PROMOPRICE +" text not null )";
    
   
   // private static final String DB_SCHEMA = CREATE_TABLE_CONTACT;
    
    public ItemProDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(CREATE_TABLE_USER);
		db.execSQL(CREATE_TABLE_ITEM);
		db.execSQL(CREATE_TABLE_CATEGORY);
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
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATUS);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROMOS);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROMO_DETAIL);
	    onCreate(db);
	}
	
	private void seedData(SQLiteDatabase db) {
		db.execSQL("insert into " + TABLE_USER + "  (user_id, password) values ('Malcom', 'malcom');");
		db.execSQL("insert into " + TABLE_USER + "  (user_id, password) values ('Dave', 'dave');");

	//	db.execSQL("insert into " + TABLE_ITEM + "  (user_id, password) values ('Dave', 'dave');");
		
		
		
		/*		db.execSQL("insert into Contact (name, email, title, phone, twitterId) values ('Zoe Washburne', 'zoe@serenity.com', 'First Mate', '612-555-5678', 'zoewashburne');");
		db.execSQL("insert into Contact (name, email, title, phone, twitterId) values ('Hoban Washburne', 'wash@serenity.com', 'Pilot', '612-555-9012', 'wash');");
		db.execSQL("insert into Contact (name, email, title, phone, twitterId) values ('Jayne Cobb', 'jayne@serenity.com', 'Muscle', '612-555-3456', 'heroofcanton');");
		db.execSQL("insert into Contact (name, email, title, phone, twitterId) values ('Kaylee Frye', 'kaylee@serenity.com', 'Engineer', '612-555-7890', 'kaylee');");
		db.execSQL("insert into Contact (name, email, title, phone, twitterId) values ('Simon Tam', 'simon@serenity.com', 'Doctor', '612-555-4321', 'simontam');");
		db.execSQL("insert into Contact (name, email, title, phone, twitterId) values ('River Tam', 'river@serenity.com', 'Doctor''s Sister', '612-555-8765', 'miranda');");
		db.execSQL("insert into Contact (name, email, title, phone, twitterId) values ('Shepherd Book', 'shepherd@serenity.com', 'Shepherd', '612-555-2109', 'shepherdbook');");
 */   }
	

}
