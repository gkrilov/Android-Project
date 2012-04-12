package finalProject.gregMo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
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
		
		ListView listView = (ListView) findViewById(android.R.id.list);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				SimpleDateFormat date_format_together = new SimpleDateFormat("yyyyMMdd");
				date_format_together.setTimeZone(TimeZone.getTimeZone("EST"));
				SimpleDateFormat date_format_nice = new SimpleDateFormat("EEEE, dd MMMM yyyy");
				date_format_nice.setTimeZone(TimeZone.getTimeZone("EST"));
				
				Date date = null;
				
				try {
					date = date_format_nice.parse(((TextView) view).getText().toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
        		
        		Intent intent = new Intent(DailyInformationActivity.this, TodayActivity.class);
        		intent.putExtra("date", date_format_together.format(date));
        		startActivity(intent);
        	}			
		});
		
		setup();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		setup();
	}
	
	private void setup() {
		mCursor = getContentResolver().query(NutritionContentProvider.CONTENT_URI_DATE, null, null, null, DateTable.COLUMN_DATE + " DESC");

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
						String formatedText = null;
						SimpleDateFormat date_format_together = new SimpleDateFormat("yyyyMMdd");
						date_format_together.setTimeZone(TimeZone.getTimeZone("EST"));
						SimpleDateFormat date_format_nice = new SimpleDateFormat("EEEE, dd MMMM yyyy");
						date_format_nice.setTimeZone(TimeZone.getTimeZone("EST"));
						
						Date date = null;
						
						try {
							date = date_format_together.parse(text);
							formatedText = date_format_nice.format(date);
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
