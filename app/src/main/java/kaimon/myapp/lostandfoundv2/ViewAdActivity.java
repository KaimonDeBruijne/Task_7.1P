package kaimon.myapp.lostandfoundv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewAdActivity extends AppCompatActivity {

    RecyclerView recyclerView2;
    ItemViewModel itemViewModel;

    ItemListAdapter itemListAdapter;

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ad);

        back = findViewById(R.id.back2);
        recyclerView2 = findViewById(R.id.recyclerView2);

        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        itemListAdapter = new ItemListAdapter(new ItemListAdapter.ItemDiff(), this, itemViewModel);

        recyclerView2.setAdapter(itemListAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();

        itemViewModel.getAllItems().observe(this,items ->{
                    itemListAdapter.submitList(items);
                }
        );
    }
}
