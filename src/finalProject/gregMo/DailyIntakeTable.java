package finalProject.gregMo;
import android.database.sqlite.SQLiteDatabase;

public class DailyIntakeTable {

	public static final String TABLE_NAME = "daily_intake";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TODAY_CALORIES = "today_calories";
	public static final String COLUMN_TODAY_FATG = "today_fat_grams";
	public static final String COLUMN_TODAY_CARBSG = "today_carbs_grams";
	public static final String COLUMN_TODAY_PROTEING = "today_protein_grams";
	public static final String COLUMN_TODAY_FIBERG = "today_fiber_grams";
	public static final String COLUMN_TODAY_SFATG = "today_saturated_grams";
	public static final String COLUMN_TODAY_CHOLESTEROL = "today_cholesterol_grams";
	public static final String COLUMN_TODAY_SODIUM = "today_sodium_grams";
	public static final String COLUMN_DATE_ID = "date_id";
	
	private static final String DATABASE_CREATE = 
			"create table " + TABLE_NAME + " ("
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_TODAY_CALORIES + " text not null, "
			+ COLUMN_TODAY_FATG + " text not null, "
			+ COLUMN_TODAY_CARBSG + " text not null, "
			+ COLUMN_TODAY_PROTEING + " text not null, "
			+ COLUMN_TODAY_FIBERG + " text not null, "
			+ COLUMN_TODAY_SFATG + " text not null, "
			+ COLUMN_TODAY_CHOLESTEROL + " text not null, "
			+ COLUMN_TODAY_SODIUM + " text not null, "
			+ COLUMN_DATE_ID + " text not null, "
			+ "FOREIGN KEY(" + COLUMN_DATE_ID + ") REFERENCES " + DateTable.TABLE_NAME + "(" + DateTable.COLUMN_ID + ")"  
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
