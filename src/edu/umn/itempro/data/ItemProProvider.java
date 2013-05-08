package edu.umn.itempro.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class ItemProProvider extends ContentProvider {

	private ItemProDatabase iDB;
	
	private static final String AUTHORITY = "edu.umn.itempro.data.ItemProProvider";
	
	public static final int USER = 100;
	public static final int ITEM = 110;
	public static final int STATUS = 120;
	public static final int PROMOS = 130;
	public static final int PROMODETAIL = 140;
	
	
	//public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + ITEMPRO_BASE_PATH);
	public static final String CONTENT_USER_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
	        + "/" + ItemProDatabase.TABLE_USER ;
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
	        + "/" + ItemProDatabase.TABLE_ITEM;
	public static final String CONTENT_STATUS_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
	        + "/" + ItemProDatabase.TABLE_STATUS;
	public static final String CONTENT_PROMOS_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
	        + "/" + ItemProDatabase.TABLE_PROMOS;
	public static final String CONTENT_PROMODETAIL_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
	        + "/" + ItemProDatabase.TABLE_PROMO_DETAIL;
	
	private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, ItemProDatabase.TABLE_USER, USER);
        sURIMatcher.addURI(AUTHORITY, ItemProDatabase.TABLE_ITEM, ITEM);
        sURIMatcher.addURI(AUTHORITY, ItemProDatabase.TABLE_STATUS, STATUS);
        sURIMatcher.addURI(AUTHORITY, ItemProDatabase.TABLE_PROMOS, PROMOS);
        sURIMatcher.addURI(AUTHORITY, ItemProDatabase.TABLE_PROMO_DETAIL, PROMODETAIL);
        
    }
    
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
	        return CONTENT_ITEM_TYPE;
	        
	    case STATUS:
	        return CONTENT_STATUS_TYPE;
	    case PROMOS:
	        return CONTENT_PROMOS_TYPE;
	    case PROMODETAIL:
	        return CONTENT_PROMODETAIL_TYPE;
	    default:
	        return null;
	    }
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
	        String[] selectionArgs, String sortOrder) {
	    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
	    int uriType = sURIMatcher.match(uri);
	    switch (uriType) {
	    case USER:
	    	queryBuilder.setTables(ItemProDatabase.TABLE_USER);
	        break;
	    case ITEM:
	    	 queryBuilder.setTables(ItemProDatabase.TABLE_ITEM);
	        break;
	    case STATUS:
	    	 queryBuilder.setTables(ItemProDatabase.TABLE_STATUS);
	        break;
	    case PROMOS:
	    	 queryBuilder.setTables(ItemProDatabase.TABLE_PROMOS);
	        break;
	    case PROMODETAIL:
	    	 queryBuilder.setTables(ItemProDatabase.TABLE_PROMO_DETAIL);
	        break;
	    default:
	        throw new IllegalArgumentException("Unknown URI");
	    }
	    Cursor cursor = queryBuilder.query(iDB.getReadableDatabase(),
	            projection, selection, selectionArgs, null, null, sortOrder);
	    cursor.setNotificationUri(getContext().getContentResolver(), uri);
	    return cursor;
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
	        int uriType = sURIMatcher.match(uri);
	        SQLiteDatabase sqlDB = iDB.getWritableDatabase();

	        long rowsAffected;

	        switch (uriType) {
	        case USER:
	            rowsAffected = sqlDB.insert(ItemProDatabase.TABLE_USER , null, values);
	            
	            break;
	        case ITEM:
	            rowsAffected = sqlDB.insert(ItemProDatabase.TABLE_ITEM , null, values);
	        	
		            break;
	                 
	        case STATUS:
	            rowsAffected = sqlDB.insert(ItemProDatabase.TABLE_STATUS , null, values);
	            
	        	break;
	        case PROMOS:
	            rowsAffected = sqlDB.insert(ItemProDatabase.TABLE_PROMOS , null, values);
	            
	        	break;
	        case PROMODETAIL:
	            rowsAffected = sqlDB.insert(ItemProDatabase.TABLE_PROMO_DETAIL , null, values);
	            
	        	break;
	                     
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
                 
        case STATUS:
            rowsAffected = sqlDB.update(ItemProDatabase.TABLE_STATUS,
                    values, selection, selectionArgs);
            break;
        case PROMOS:
            rowsAffected = sqlDB.update(ItemProDatabase.TABLE_PROMOS,
                    values, selection, selectionArgs);
            break;
        case PROMODETAIL:
            rowsAffected = sqlDB.update(ItemProDatabase.TABLE_PROMO_DETAIL,
                    values, selection, selectionArgs);
            break;
                     
        default:
            throw new IllegalArgumentException("Unknown URI");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsAffected;
    }
	
	
	
}
