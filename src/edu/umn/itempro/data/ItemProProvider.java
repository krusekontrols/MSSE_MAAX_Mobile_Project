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
	
	
	//public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + ITEMPRO_BASE_PATH);
	public static final String CONTENT_USER_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
	        + "/" + ItemProDatabase.TABLE_USER ;
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
	        + "/" + ItemProDatabase.TABLE_ITEM;
	
	private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, ItemProDatabase.TABLE_USER, USER);
        sURIMatcher.addURI(AUTHORITY, ItemProDatabase.TABLE_ITEM, ITEM);
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
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
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
            rowsAffected = sqlDB.update(ItemProDatabase.TABLE_USER,
                    values, selection, selectionArgs);
            break;
        default:
            throw new IllegalArgumentException("Unknown URI");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsAffected;
    }
	
	
	
}
