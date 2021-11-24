package com.example.nonusenum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button buttonOne;
    Button buttonTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonOne = findViewById(R.id.moveToConsoleOne);
        buttonTwo = findViewById(R.id.moveToConsoleTwo);

        setButtonFunction();
    }

    private void setButtonFunction() {
        buttonOne.setOnClickListener(view -> {
            Intent intent = new Intent(this, VersionCodeActivity.class);
            startActivity(intent);
        });

        buttonTwo.setOnClickListener(view -> {
            Intent intent = new Intent(this, OcrActivity.class);
            startActivity(intent);
        });
    }
}