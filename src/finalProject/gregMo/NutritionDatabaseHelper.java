package finalProject.gregMo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class NutritionDatabaseHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "Nutrition Database";
	private static final int DATABASE_VERSION = 1;
	
	public NutritionDatabaseHelper(Context ctx)
	{
		super(ctx, DATABASE_NAME , null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		PersonalInformationTable.onCreate(db);
		DateTable.onCreate(db);
		DailyIntakeTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		PersonalInformationTable.onUpgrade(db, oldVersion, newVersion);
	}
	
}
