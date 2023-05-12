package kaimon.myapp.lostandfoundv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    ItemViewModel itemViewModel;
    Button viewListings, createListing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewListings = findViewById(R.id.viewListings);
        createListing = findViewById(R.id.createAd);

        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        createListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        viewListings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewIntent = new Intent(MainActivity.this, ViewAdActivity.class);
                startActivity(viewIntent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Item item = new Item(0,
                        data.getStringExtra("value1"),
                        data.getStringExtra("value2"),
                        data.getStringExtra("value3"),
                        data.getStringExtra("value4"));
                itemViewModel.insert(item);
            }
        }
    }
}