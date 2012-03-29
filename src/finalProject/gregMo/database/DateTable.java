package finalProject.gregMo.database;

import android.database.sqlite.SQLiteDatabase;

public class DateTable {

	public static final String TABLE_NAME = "date";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_DATE= "date";
	
	private static final String DATABASE_CREATE = 
			"create table " + TABLE_NAME + " ("
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_DATE + " text not null"
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
