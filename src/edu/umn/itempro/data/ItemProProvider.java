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

	private ItemProDatabase cDB;
	
	private static final String AUTHORITY = "edu.umn.contactviewer.data.ItemProProvider";
	
	public static final int CONTACT = 100;
	public static final int CONTACT_ID = 110;
	private static final String CONTACT_BASE_PATH = "Contact";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
	        + "/" + CONTACT_BASE_PATH);
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
	        + "/Contact";
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
	        + "/Contact";
	
	private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, CONTACT_BASE_PATH, CONTACT);
        sURIMatcher.addURI(AUTHORITY, CONTACT_BASE_PATH + "/#", CONTACT_ID);
    }

	@Override
	public String getType(Uri uri) {
	    int uriType = sURIMatcher.match(uri);
	    switch (uriType) {
	    case CONTACT:
	        return CONTENT_TYPE;
	    case CONTACT_ID:
	        return CONTENT_ITEM_TYPE;
	    default:
	        return null;
	    }
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
	        String[] selectionArgs, String sortOrder) {
	    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
	    queryBuilder.setTables(ItemProDatabase.TABLE_CONTACT);
	    int uriType = sURIMatcher.match(uri);
	    switch (uriType) {
	    case CONTACT_ID:
	        queryBuilder.appendWhere(ItemProDatabase.ID + "="
	                + uri.getLastPathSegment());
	        break;
	    case CONTACT:
	        // no filter
	        break;
	    default:
	        throw new IllegalArgumentException("Unknown URI");
	    }
	    Cursor cursor = queryBuilder.query(cDB.getReadableDatabase(),
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
		cDB = new ItemProDatabase(getContext());
        return true;
	}

	@Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = cDB.getWritableDatabase();

        int rowsAffected;

        switch (uriType) {
        case CONTACT_ID:
            String id = uri.getLastPathSegment();
            StringBuilder modSelection = new StringBuilder(ItemProDatabase.ID
                    + "=" + id);

            if (!TextUtils.isEmpty(selection)) {
                modSelection.append(" AND " + selection);
            }

            rowsAffected = sqlDB.update(ItemProDatabase.TABLE_CONTACT,
                    values, modSelection.toString(), null);
            break;
        case CONTACT:
            rowsAffected = sqlDB.update(ItemProDatabase.TABLE_CONTACT,
                    values, selection, selectionArgs);
            break;
        default:
            throw new IllegalArgumentException("Unknown URI");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsAffected;
    }
	
	
	
}
