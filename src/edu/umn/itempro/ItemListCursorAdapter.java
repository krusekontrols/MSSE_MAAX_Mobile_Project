package edu.umn.itempro;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import edu.umn.itempro.data.Item;
import edu.umn.itempro.data.ItemProProvider;

public class ItemListCursorAdapter extends CursorAdapter {
	private LayoutInflater inflater;
	public ItemListCursorAdapter(Context context, Cursor c){
		super(context, c);
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		updateView(view, cursor);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = inflater.inflate(R.layout.item, null);
		updateView(view, cursor);
		return view;
	}
	
    private void updateView(View view, Cursor cursor){
		TextView nameLabel = (TextView)view.findViewById(R.id.name);
		
		nameLabel.setText(cursor.getString(cursor.getColumnIndex(Item.NAME)));
    	
    	TextView amount_unitText = (TextView) view.findViewById(R.id.amount_unit);
    	if (cursor.getInt(cursor.getColumnIndex(Item.ONLIST)) == 0){
    		nameLabel.setTextColor(Color.GRAY);    		
    		amount_unitText.setText("");
    	} else {
    		nameLabel.setTextColor(Color.WHITE);
    		amount_unitText.setTextColor(Color.WHITE);
    		double amountDouble = cursor.getDouble(cursor.getColumnIndex(Item.AMOUNT));
    		String amount;
    		if (amountDouble == 0)
    			amount = "";
    		else
    			amount = ItemProProvider.format.format(amountDouble);
    		String unit = cursor.getString(cursor.getColumnIndex(Item.UNIT));
    		if (unit == null)
    			unit = "";
    		amount_unitText.setText(amount);
    		if (!unit.equals("")){
    			amount_unitText.append(" ");
    			amount_unitText.append(unit);
    		}
    	}
     
    }
}
