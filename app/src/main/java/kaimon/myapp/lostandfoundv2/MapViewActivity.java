package kaimon.myapp.lostandfoundv2;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class MapViewActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    String location;
    ItemViewModel itemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    private LatLng coords(String location){

        Geocoder geocoder = new Geocoder(this);
        LatLng latLng = null;

        try {
            List<Address> addresses = geocoder.getFromLocationName(location, 3);

            if(addresses != null && !addresses.isEmpty()){
                Address address = addresses.get(0);
                latLng = new LatLng(address.getLatitude(), address.getLongitude());
            }
        } catch (IOException e) {
//            throw new RuntimeException(e);
        }
        return latLng;
    }

    public void add(GoogleMap googleMap){

        LiveData<List<Item>> itemLiveData = itemViewModel.getAllItems();

        itemLiveData.observe(this, items -> {
                for(Item item : items){

                    location = item.getLocation();
                    LatLng place = coords(location);
                    String itemName = item.getItemName();
                    String name = item.getName();

                    if(place != null){
                        googleMap.addMarker(new MarkerOptions().position(place).title(name + " - " + itemName));
                    }
                }
            });
        }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng test = new LatLng(-37.81, 144.96);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(test, 8));

        add(googleMap);
    }
}

