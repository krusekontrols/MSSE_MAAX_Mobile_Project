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
	private Context myActivity;
	public ItemListCursorAdapter(Context context, Cursor c){
		super(context, c);
		inflater = LayoutInflater.from(context);
		myActivity = context;
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
    	
    	TextView promotionText = (TextView) view.findViewById(R.id.promotion);
    	
    	if (cursor.getInt(cursor.getColumnIndex(Item.ONLIST)) == 0){
    		nameLabel.setTextColor(Color.GRAY);    		
    		amount_unitText.setText("");
    		promotionText.setText("");
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
    		
    		if(((ItemList) myActivity).getMode() == ItemList.SHOPPING_MODE)
    		{
	    		String store = ((ItemList) myActivity).getStore();
	    		String promotion  = null;
	    		if(CheckinActivity.BEST_BUY.equals(store))
	    		{
	    			promotion  = cursor.getString(cursor.getColumnIndex(Item.BESTBUYPROMOTION));
	    		}
	    		else
	    		{
	    			promotion = cursor.getString(cursor.getColumnIndex(Item.TARGETPROMOTION));
	    		}
	    		
	    		if(promotion == null) promotion = "Not In Stock";
	    		if("Not in Stock".equals(promotion)) promotionText.setTextColor(Color.RED);
	    		else if("No Promotion Avaiable".equals(promotion)) promotionText.setTextColor(Color.YELLOW);
	    		else promotionText.setTextColor(Color.GREEN);
	    		
	    		promotionText.setText(promotion);
    		}
    		else
    		{
    			promotionText.setText("");
    		}
    	}
    }
}
