package com.example.maldroid;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn1);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent malIntent = getPackageManager().
                        getLaunchIntentForPackage("com.example.mycall");
                if (malIntent != null) {
                    startActivity(malIntent);
                }

            }
        });
    }
}