package com.example.autocompleteplacedemo;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Initialize Variables
    EditText editText;
    TextView textView1, textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign Variables
        editText = findViewById(R.id.edit_text);
        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);

        //Initialize Places
        Places.initialize(getApplicationContext(),"");

        //Set edit text nonFocusable
         editText.setFocusable(false);
         editText.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                //Initialize place field list
                 List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);

                 //Create Intent
                 Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(MainActivity.this);

                 //Start activity result
                 startActivityForResult(intent, 100);
             }
         });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK){
            //When success
            //Initialize Place
            Place place = Autocomplete.getPlaceFromIntent(data);
            //Set address on edit text
            editText.setText(place.getAddress());
            //Set Locality Name
            textView1.setText(String.format("Locality name: " + place.getName()));
            //Set Log Lat
            textView2.setText(String.valueOf(place.getLatLng()));

        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            //When error
            //Initialize Status
            Status status = Autocomplete.getStatusFromIntent(data);
            //Display toast
            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}