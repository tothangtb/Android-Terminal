package com.example.v0932593.tm_billy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

public class history extends AppCompatActivity {
    ScanActivity activity;
    TextView showlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toast.makeText(getApplicationContext(),"Click history success !!!",Toast.LENGTH_SHORT).show();
        String data = Utils.ReadFile("ThangCoder/Log_5.txt");

        showlog = (TextView) findViewById(R.id.log);
        showlog.setMovementMethod(new ScrollingMovementMethod());
        showlog.setText(data);
    }

}
