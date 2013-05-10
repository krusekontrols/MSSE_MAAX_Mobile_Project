package edu.umn.itempro.data;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class ItemProProvider extends ContentProvider {

	private ItemProDatabase iDB;
	
	private static final String AUTHORITY = "edu.umn.itempro.data.ItemProProvider";
	
	public static final int USER = 100;
	public static final int ITEM = 110;
	public static final int ITEM_ID = 120;
	public static final int CATEGORY = 130;
    public static final int CATEGORY_ID = 140;

	//public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + ITEMPRO_BASE_PATH);
	public static final String CONTENT_USER_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
	        + "/" + ItemProDatabase.TABLE_USER ;
	public static final String CONTENT_ITEMS_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE 
			+ "/" + ItemProDatabase.TABLE_ITEM;
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
	        + "/" + ItemProDatabase.TABLE_ITEM;
	public static final String CONTENT_CATEGORIES_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/" + ItemProDatabase.TABLE_CATEGORY;
	public static final String CONTENT_CATEGORY_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/" + ItemProDatabase.TABLE_CATEGORY;
	
	private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, ItemProDatabase.TABLE_USER, USER);
        sURIMatcher.addURI(AUTHORITY, ItemProDatabase.TABLE_ITEM, ITEM);
        sURIMatcher.addURI(AUTHORITY, ItemProDatabase.TABLE_ITEM + "/#", ITEM_ID);
        sURIMatcher.addURI(AUTHORITY, ItemProDatabase.TABLE_CATEGORY, CATEGORY);
        sURIMatcher.addURI(AUTHORITY, ItemProDatabase.TABLE_CATEGORY + "/#", CATEGORY_ID);
        
    }
    
    public static boolean isNew = false; 
	public static NumberFormat format = new DecimalFormat("######.##");
    public static final String ITEMS_TABLE_NAME = "items";
    public static final String CATEGORIES_TABLE_NAME = "categories";
    
    public static final Uri getContentURI(String tableName) {
    	return Uri.parse("content://" + AUTHORITY + "/" + tableName);
    }

	@Override
	public String getType(Uri uri) {
	    int uriType = sURIMatcher.match(uri);
	    switch (uriType) {
	    case USER:
	        return CONTENT_USER_TYPE;
	    case ITEM:
	    	return CONTENT_ITEMS_TYPE;
	    case ITEM_ID:
	        return CONTENT_ITEM_TYPE;
	    case CATEGORY:
	    	return CONTENT_CATEGORIES_TYPE;
	    case CATEGORY_ID:
	        return CONTENT_CATEGORY_TYPE;
	    default:
	        return null;
	    }
	}
	
    public void close() {
    	iDB.close();
    }
    
    public void deleteAll(){
    	SQLiteDatabase database = iDB.getReadableDatabase();
    	database.execSQL("DROP TABLE IF EXISTS " + ItemProDatabase.TABLE_USER);
    	database.execSQL("DROP TABLE IF EXISTS " + ItemProDatabase.TABLE_ITEM);
    	database.execSQL("DROP TABLE IF EXISTS " + ItemProDatabase.TABLE_CATEGORY);
    	iDB.onCreate(database);
    }

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
	        String[] selectionArgs, String sortOrder) {
	    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
	    String orderBy = "";
	    int uriType = sURIMatcher.match(uri);
	    switch (uriType) {
	    case USER:
	    	queryBuilder.setTables(ItemProDatabase.TABLE_USER);
	        break;
	    case ITEM:
	    	queryBuilder.setTables(ItemProDatabase.TABLE_ITEM);
	    	orderBy = Item.NAME;
	        break;
	    case ITEM_ID:
	    	queryBuilder.setTables(ItemProDatabase.TABLE_ITEM);
	    	queryBuilder.appendWhere(Item._ID + "=" + uri.getPathSegments().get(1));
            break;
	    case CATEGORY:
	    	queryBuilder.setTables(ItemProDatabase.TABLE_CATEGORY);
            orderBy = Category.NAME;
            break;
        case CATEGORY_ID:
            queryBuilder.setTables(ItemProDatabase.TABLE_CATEGORY);
            queryBuilder.appendWhere(Category._ID + "=" + uri.getPathSegments().get(1));
            break;
        
	    default:
	        throw new IllegalArgumentException("Unknown URI");
	    }
	    Cursor cursor = queryBuilder.query(iDB.getReadableDatabase(),
	            projection, selection, selectionArgs, null, null, orderBy);
	    cursor.setNotificationUri(getContext().getContentResolver(), uri);
	    return cursor;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = iDB.getWritableDatabase();
		int rowsAffected;
        switch (uriType) {
        case USER:
        	rowsAffected = sqlDB.delete(ItemProDatabase.TABLE_USER , selection, selectionArgs);
            break;
        case ITEM:
        	rowsAffected = sqlDB.delete(ItemProDatabase.TABLE_ITEM, selection, selectionArgs);
            break;
        case ITEM_ID:
        	String itemId = uri.getPathSegments().get(1);
        	rowsAffected = sqlDB.delete(ItemProDatabase.TABLE_ITEM, Item._ID + "=" + itemId + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
        	break;
        case CATEGORY:
        	rowsAffected = sqlDB.delete(ItemProDatabase.TABLE_CATEGORY, selection, selectionArgs);
            break;
        case CATEGORY_ID:
        	String categoryId = uri.getPathSegments().get(1);
        	getContext().getContentResolver().delete(ItemProProvider.getContentURI(ItemProDatabase.TABLE_ITEM), Item.CATEGORY + "=" + categoryId, null);
        	rowsAffected = sqlDB.delete(ItemProDatabase.TABLE_CATEGORY, Category._ID + "=" + categoryId + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
        	//database.delete(ITEMS_TABLE_NAME, Items.CATEGORY + "=" + categoryId, whereArgs);
        	break;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsAffected;
	}

	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
	        int uriType = sURIMatcher.match(uri);
	        SQLiteDatabase sqlDB = iDB.getWritableDatabase();

	        long rowID;

	        switch (uriType) {
	        case USER:
	            rowID = sqlDB.insert(ItemProDatabase.TABLE_USER , null, values);
	            break;
	        case ITEM:
	            rowID = sqlDB.insert(ItemProDatabase.TABLE_ITEM , null, values);
	            if (rowID > 0) {
	                Uri noteUri = ContentUris.withAppendedId(getContentURI(ItemProDatabase.TABLE_ITEM), rowID);
	                getContext().getContentResolver().notifyChange(noteUri, null);
	                return noteUri;
	            }
	            throw new SQLException("Failed to insert row into " + uri);
		    
	        case CATEGORY: 
	        	rowID = sqlDB.insert(ItemProDatabase.TABLE_CATEGORY , null, values);
	            if (rowID > 0) {
	                Uri noteUri = ContentUris.withAppendedId(getContentURI(ItemProDatabase.TABLE_CATEGORY), rowID);
	                getContext().getContentResolver().notifyChange(noteUri, null);
	                return noteUri;
	            }
	            throw new SQLException("Failed to insert row into " + uri);
	        default:
	            throw new IllegalArgumentException("Unknown URI");
	        }
	        
	        getContext().getContentResolver().notifyChange(uri, null);
	        return uri;
	    }

	

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		iDB = new ItemProDatabase(getContext());
        return true;
	}

	@Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = iDB.getWritableDatabase();

        int rowsAffected;

        switch (uriType) {
        case USER:
            String id = uri.getLastPathSegment();
            StringBuilder modSelection = new StringBuilder(ItemProDatabase.TABLE_USER
                    + "=" + id);

            if (!TextUtils.isEmpty(selection)) {
                modSelection.append(" AND " + selection);
            }

            rowsAffected = sqlDB.update(ItemProDatabase.TABLE_USER,
                    values, modSelection.toString(), null);
            break;
        case ITEM:
            rowsAffected = sqlDB.update(ItemProDatabase.TABLE_ITEM,
                    values, selection, selectionArgs);
            break;
        
        case ITEM_ID:
        	String itemId = uri.getPathSegments().get(1);
        	rowsAffected = sqlDB.update(ItemProDatabase.TABLE_ITEM, values, Item._ID + "=" + itemId + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
        	break;
        case CATEGORY:
        	rowsAffected = sqlDB.update(ItemProDatabase.TABLE_CATEGORY, values, selection, selectionArgs);
            break;
        case CATEGORY_ID:
        	String categoryId = uri.getPathSegments().get(1);
        	rowsAffected = sqlDB.update(ItemProDatabase.TABLE_CATEGORY, values, Category._ID + "=" + categoryId + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
        	break;
        default:
            throw new IllegalArgumentException("Unknown URI");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsAffected;
    }
	
	
	
}
