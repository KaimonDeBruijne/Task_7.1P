package kaimon.myapp.lostandfoundv2;


import static java.lang.System.in;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapViewActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    ItemViewModel itemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;
        LiveData<List<Item>> items = itemViewModel.getAllItems();

        for(int i = 0; i < items.getValue().size(); i++){
            Item current = itemViewModel.itemRepository.itemList.getValue().get(i);
            try {
                addresses = geocoder.getFromLocationName(current.getLocation(), 1);

                if(addresses != null){
                    double lat = addresses.get(0).getLatitude();
                    double lng = addresses.get(0).getLongitude();

                    LatLng place = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions().position(place));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


    }
}

