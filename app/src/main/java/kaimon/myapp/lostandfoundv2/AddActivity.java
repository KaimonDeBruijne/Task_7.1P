package kaimon.myapp.lostandfoundv2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import com.google.android.libraries.places.api.net.PlacesClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    EditText name, itemName, location, date;
    Button submit, back;

    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name = findViewById(R.id.editName);
        itemName = findViewById(R.id.editItem);
        location = findViewById(R.id.editLocation);
        date = findViewById(R.id.editDate);

        submit = findViewById(R.id.submit);
        back = findViewById(R.id.back);

        if(!Places.isInitialized()){
            Places.initialize(getApplicationContext(), BuildConfig.MAPS_API_KEY);

        }

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(getApplicationContext());
                startAutocomplete.launch(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();


                String nameValue = name.getText().toString();
                String itemValue = itemName.getText().toString();
                String locationValue = location.getText().toString();
                String dateValue = date.getText().toString();

                intent.putExtra("value1", nameValue);
                intent.putExtra("value2", itemValue);
                intent.putExtra("value3", locationValue);
                intent.putExtra("value4", dateValue);

                setResult(RESULT_OK, intent);

                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
    private final ActivityResultLauncher<Intent> startAutocomplete = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        Place place = Autocomplete.getPlaceFromIntent(intent);
                        Log.i("TAG", "Place: ${place.getName()}, ${place.getId()}");

                        location.setText(place.getName());
                    };
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    // The user canceled the operation.
                    Log.i("TAG", "User canceled autocomplete");
                }
            });
}