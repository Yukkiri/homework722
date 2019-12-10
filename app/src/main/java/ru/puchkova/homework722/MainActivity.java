package ru.puchkova.homework722;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText phone;
    private EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {
        phone = findViewById(R.id.phone);
        message = findViewById(R.id.message);

        makeCall();
        sendMessage();
    }

    private void makeCall() {
        final Intent intent = new Intent(Intent.ACTION_CALL);

        findViewById(R.id.call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "tel:" + phone.getText().toString();
                Uri uri = Uri.parse(number);
                intent.setData(uri);
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.CALL_PHONE)) {
                        //не знаю что тут написать
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                10);
                    }
                } else {
                    startActivity(intent);
                }
            }
        });
    }

    private void sendMessage() {
        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = phone.getText().toString();
                String textMessage = message.getText().toString();

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.SEND_SMS)) {
                        //не знаю что тут написать
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.SEND_SMS},
                                10);
                    }
                } else {
                    SmsManager smgr = SmsManager.getDefault();
                    if (textMessage.equals("")) {
                        textMessage = "meow message";
                    }
                    smgr.sendTextMessage(number, null, textMessage, null, null);
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Сообщение отправлено.", Toast.LENGTH_SHORT);
                    toast.show();
                }


            }
        });
    }
}
