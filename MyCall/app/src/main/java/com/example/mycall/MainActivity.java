package com.example.mycall;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.telephony.SmsManager;

public class MainActivity extends AppCompatActivity {

    private static final int call_request = 1;
    private static final int text_request = 2;
    private EditText PhoneNumber;
    private Button callBtn;
    private Button txtBtn;
    private EditText msg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PhoneNumber = findViewById(R.id.edit_number);
        msg = findViewById(R.id.txt);


        callBtn = findViewById(R.id.buttonCall);
        txtBtn = findViewById(R.id.buttonText);


        callBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                makePhoneCall();
            }
        });
        txtBtn.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
                sendText();
           }
        });

    }

    private void sendText(){
        String number = PhoneNumber.getText().toString();
        String message = msg.getText().toString();
        if(number.trim().length()>0){
            if(ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.SEND_SMS},text_request);
            }else{
                String dial = "tel:"+number;
                //Intent textIntent = new Intent(Intent.ACTION_VIEW);
                SmsManager manager = SmsManager.getDefault();
                manager.sendTextMessage(dial,null,message,null,null);

                //startActivity(textIntent);
            }

        }else{
            Toast.makeText(MainActivity.this,"No Phone Number Entered", Toast.LENGTH_SHORT).show();
        }
    }

    private void makePhoneCall(){
        String number = PhoneNumber.getText().toString();
        if(number.trim().length()>0){
            if(ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, call_request);
            }else{
                String dial = "tel:"+number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        }else{
            Toast.makeText(MainActivity.this,"No Phone Number Entered", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == call_request){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            }else{
                Toast.makeText(this,"Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}