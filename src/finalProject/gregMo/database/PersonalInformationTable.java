package finalProject.gregMo.database;
import android.database.sqlite.SQLiteDatabase;

public class PersonalInformationTable {

	public static final String TABLE_NAME = "personal_information";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_WEIGHT = "weight";
	public static final String COLUMN_MAX_CALORIES = "max_calories";
	public static final String COLUMN_MAX_FATG = "max_fat_grams";
	public static final String COLUMN_MAX_CARBSG = "max_carbs_grams";
	public static final String COLUMN_MAX_PROTEING = "max_protein_grams";
	public static final String COLUMN_MAX_FIBERG = "max_fiber_grams";
	public static final String COLUMN_MAX_SFATG = "max_saturated_grams";
	public static final String COLUMN_MAX_CHOLESTEROL = "max_cholesterol_grams";
	public static final String COLUMN_MAX_SODIUM = "max_sodium_grams";
	
	private static final String DATABASE_CREATE = 
			"create table " + TABLE_NAME + " ("
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_WEIGHT + " text not null, "
			+ COLUMN_MAX_CALORIES + " text not null, "
			+ COLUMN_MAX_FATG + " text not null, "
			+ COLUMN_MAX_CARBSG + " text not null, "
			+ COLUMN_MAX_PROTEING + " text not null, "
			+ COLUMN_MAX_FIBERG + " text not null, "
			+ COLUMN_MAX_SFATG + " text not null, "
			+ COLUMN_MAX_CHOLESTEROL + " text not null, "
			+ COLUMN_MAX_SODIUM + " text not null"
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

