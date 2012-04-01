package finalProject.gregMo;

import finalProject.gregKrilov.R;
import finalProject.gregMo.database.NutritionContentProvider;
import finalProject.gregMo.database.PersonalInformationTable;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InitialInformation extends Activity implements OnClickListener {

	EditText weight; 
	Button submit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.initial_information);
		
		submit = (Button) findViewById(R.id.submit);
	
		weight = (EditText) findViewById(R.id.editText1);
		weight.setKeyListener(DigitsKeyListener.getInstance(false,true));

		Cursor cursor = managedQuery(NutritionContentProvider.CONTENT_URI_PERSONAL, null, null, null, null);
		//If there is already a record in the table then don't allow something to be entered in the edittext 
		//and change the submit button into a reset button
		if ( cursor.moveToFirst() ) { 
			
		   weight.setText(cursor.getString(cursor
					.getColumnIndex(PersonalInformationTable.COLUMN_WEIGHT)));
		   weight.setFocusable(false);
					
		   submit.setText("Reset");
		   submit.setOnClickListener( new OnClickListener() {
				public void onClick(View v) {
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(InitialInformation.this);
					alertDialog.setTitle("Confirm Reset Weight");
					alertDialog.setMessage("Are you sure you want to reset your weight?");
					
					alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int arg1) {
			                getContentResolver().delete(NutritionContentProvider.CONTENT_URI_PERSONAL, null, null);
			                weight.setText("");
			                weight.setFocusable(true);
			                submit.setText("Submit");
			                submit.setOnClickListener(InitialInformation.this);
			            }});
					
					alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {}	
					});
					
					alertDialog.setCancelable(true);
					alertDialog.show();
				}
		   });   
		}
		else
		{
			//Set an onClickListener on the activity
			submit.setOnClickListener(this);
		}

		
	}

	public void onClick(View v) {
		 if ( !weight.getText().toString().isEmpty() ) 
		 {
			 String myWeight = weight.getText().toString();
			 //if ( myWeight.)
			 int maxCalories =  (int) (Double.parseDouble(myWeight) * 18); 
			 int gramFat = (int) (maxCalories * 0.3 / 9); //g
			 int gramCarbs =(int) (maxCalories * 0.6 / 4); //g
			 int gramProtein = (int)(maxCalories * 0.1 / 4); //g
			 int gramFibre = (int) (maxCalories / 1000 * 11.5); //g
			 String saturatedFat = "20"; //g
			 String cholesterol = "300"; //mg
			 String sodium = "2400"; //mg
			 
			 ContentValues values = new ContentValues();
		     values.put(PersonalInformationTable.COLUMN_WEIGHT, myWeight );
		     values.put(PersonalInformationTable.COLUMN_MAX_CALORIES,  Integer.toString(maxCalories));
		     values.put(PersonalInformationTable.COLUMN_MAX_FATG,  Integer.toString(gramFat));
		     values.put(PersonalInformationTable.COLUMN_MAX_CARBSG,  Integer.toString(gramCarbs));
		     values.put(PersonalInformationTable.COLUMN_MAX_PROTEING,  Integer.toString(gramProtein));
		     values.put(PersonalInformationTable.COLUMN_MAX_FIBERG,  Integer.toString(gramFibre));
		     values.put(PersonalInformationTable.COLUMN_MAX_SFATG,  saturatedFat);
		     values.put(PersonalInformationTable.COLUMN_MAX_CHOLESTEROL,  cholesterol);
		     values.put(PersonalInformationTable.COLUMN_MAX_SODIUM,  sodium);
		     getContentResolver().insert(NutritionContentProvider.CONTENT_URI_PERSONAL, values);	  
		     
		     AlertDialog.Builder alertDialog = new AlertDialog.Builder(InitialInformation.this);
				alertDialog.setTitle("Thank you");
				alertDialog.setMessage("Weight successfully entered");
				
				alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int arg1) {
		                Intent intent = new Intent(InitialInformation.this,MainMenu.class);
		                startActivity(intent);
		            }});
				
				alertDialog.setCancelable(false);
				alertDialog.show();
		 }
		 else
		 {
			 TextView error = (TextView) findViewById(R.id.errorWeight);
			 error.setTextColor(Color.RED);
			 error.setText("Please enter your weight");
		 }
	}
	
	

	
}
