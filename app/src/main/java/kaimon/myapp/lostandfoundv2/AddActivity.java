package kaimon.myapp.lostandfoundv2;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    EditText name, itemName, location, date;
    Button submit, back, getLocation;
    Location current;
    LocationManager place;
    LocationListener check;

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
        getLocation = findViewById(R.id.getLocation);

        place = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        check = location -> {};

        if (!Places.isInitialized()) {
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

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(AddActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                current = place.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (current != null) {
                    Geocoder geocoder = new Geocoder(AddActivity.this, Locale.getDefault());

                    try {
                        List<Address> addresses = geocoder.getFromLocation(current.getLatitude(), current.getLongitude(), 1);
                        if (addresses != null && !addresses.isEmpty()) {
                            Address address = addresses.get(0);
                            location.setText(address.getAddressLine(0));
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
//                    String test = current.toString();
//                    Log.i("TAG", test);
//                    location.setText(test);
//                }
//                else {
//                    location.setText("Issue Connecting to GPS");
//                    Log.i("TAG", "Failing to get location");
//                }
                }
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
                        location.setText(place.getName());
                    };
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {}
            });
}