package finalProject.gregMo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import finalProject.gregKrilov.R;
import finalProject.gregMo.database.DateTable;
import finalProject.gregMo.database.NutritionContentProvider;

public class DailyInformationActivity extends ListActivity {
	Cursor mCursor = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daily_info);
		
		mCursor = getContentResolver().query(NutritionContentProvider.CONTENT_URI_DATE, null, null, null, null);
		
		if (mCursor != null) {
			ListAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, mCursor, new String[] { DateTable.COLUMN_DATE }, new int[] { android.R.id.text1 })
			{
				 @Override
				 public void setViewText(TextView v, String text) {
					 super.setViewText(v, formatText(v, text));
				 }    

				private String formatText(TextView v, String text) {
				
					switch (v.getId()) {
					case android.R.id.text1:
						String formatedText = text;
						SimpleDateFormat date_format = new SimpleDateFormat("yyyyMMdd");
						SimpleDateFormat date_today = new SimpleDateFormat("EEEE, dd MMMM yyyy");
						date_today.setTimeZone(TimeZone.getTimeZone("EST"));
						
						Date date = null;
						
						try {
							date = date_format.parse(text);
							formatedText = date_today.format(date);
						} catch (ParseException e) {
							e.printStackTrace();
						}

						return formatedText;
					}
					return text;
				}
			};

			setListAdapter(adapter);	
		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mCursor.close();
	}
}
