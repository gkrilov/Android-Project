package finalProject.gregMo.database;

import android.database.sqlite.SQLiteDatabase;

public class FoodTable {

	public static final String TABLE_NAME = "food";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME= "foodName";
	public static final String COLUMN_FOOD_CALORIES = "calories";
	public static final String COLUMN_FOOD_FATG = "fat_grams";
	public static final String COLUMN_FOOD_CARBSG = "carbs_grams";
	public static final String COLUMN_FOOD_PROTEING = "protein_grams";
	public static final String COLUMN_FOOD_FIBERG = "fiber_grams";
	public static final String COLUMN_FOOD_SFATG = "saturated_grams";
	public static final String COLUMN_FOOD_CHOLESTEROL = "cholesterol_grams";
	public static final String COLUMN_FOOD_SODIUM = "sodium_grams";
	public static final String COLUMN_FOOD_NOTE = "note";
	public static final String COLUMN_DAILY_ID = "daily_id";
	
	private static final String DATABASE_CREATE = 
			"create table " + TABLE_NAME + " ("
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_FOOD_CALORIES + " text not null, "
			+ COLUMN_FOOD_FATG + " text not null, "
			+ COLUMN_FOOD_CARBSG + " text not null, "
			+ COLUMN_FOOD_PROTEING + " text not null, "
			+ COLUMN_FOOD_FIBERG + " text not null, "
			+ COLUMN_FOOD_SFATG + " text not null, "
			+ COLUMN_FOOD_CHOLESTEROL + " text not null, "
			+ COLUMN_FOOD_SODIUM + " text not null, "
			+ COLUMN_FOOD_NOTE + " text not null, "
			+ COLUMN_DAILY_ID + " text not null, "
			+ "FOREIGN KEY(" + COLUMN_DAILY_ID + ") REFERENCES " + DailyIntakeTable.TABLE_NAME + "(" + DateTable.COLUMN_ID + ")"  
			+ ");";
	
	public static void onCreate(SQLiteDatabase db)
	{
		db.execSQL(DATABASE_CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		 db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		 onCreate(db);
	}
	
}
