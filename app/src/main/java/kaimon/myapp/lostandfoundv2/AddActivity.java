package kaimon.myapp.lostandfoundv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    EditText name, itemName, location, date;
    Button submit, back;

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
}