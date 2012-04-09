package finalProject.gregMo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import finalProject.gregMo.database.NutritionContentProvider;
import finalProject.gregMo.database.PersonalInformationTable;

public class FirstActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = new Intent(this, MainMenu.class);
		
		String[] projection = {
				PersonalInformationTable.COLUMN_WEIGHT
		};
		
		Cursor cursor = getContentResolver().query(NutritionContentProvider.CONTENT_URI_PERSONAL, projection, null, null, null);
		
		if (cursor != null) {
			cursor.moveToFirst();
			
			if (cursor.getCount() == 0) {
				intent = new Intent(this, InitialInformation.class);
			}

			cursor.close();
		}

		startActivity(intent);
	}	
}

