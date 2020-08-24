package com.maxiumman.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.input);
    }

    public void Calculate(View view) {
        Toast.makeText(this, Core.getFormat(input.getText().toString()), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ""+Core.Calculate(input.getText().toString()), Toast.LENGTH_SHORT).show();
    }
}