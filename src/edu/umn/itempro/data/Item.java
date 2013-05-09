package edu.umn.itempro.data;

import android.provider.BaseColumns;

public class Item implements BaseColumns {    	
    /**
     * The name of the item
     * <P>Type: TEXT</P>
     */
    public static final String NAME = "name";
    
    /**
     * The category of the item
     * <P>Type: TEXT</P>
     */
    public static final String CATEGORY = "category";
    
    /**
     * The amount
     * <P>Type: INTEGER</P>
     */
    public static final String AMOUNT = "amount";
    
    /**
     * The unit (e.g. "grams", "liters")
     * <P>Type: TEXT</P>
     */
    public static final String UNIT = "unit";
    
    /**
     * Is the item on the shopping list?
     * <P>Type: INTEGER</P>
     */
    public static final String ONLIST = "onlist";
}
