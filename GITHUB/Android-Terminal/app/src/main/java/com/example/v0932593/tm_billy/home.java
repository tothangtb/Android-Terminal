package com.example.v0932593.tm_billy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

;


public class home extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mTopToolbar;
    private CardView scan_barcode,click_search,click_setting,click_connect,click_history;
    private ScanActivity scanActivity;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        //Run app from Halo
        /*Intent halo = getIntent();
        String action ="";
        action = halo.getAction();
        String type = halo.getType();

        if(Intent.ACTION_SEND.equals(action) && type != null){
            if("text/plain".equals(type)){
                try {
                    decrypt.decrypt(action);
                    Intent  n =new Intent(getApplicationContext(), home.class);
                    startActivity(n);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }*/


        scan_barcode = (CardView) findViewById(R.id.scan_barcode);
        scan_barcode.setOnClickListener(this);
        click_connect = (CardView) findViewById(R.id.click_connect);
        click_connect.setOnClickListener(this);
        click_history = (CardView) findViewById(R.id.click_history);
        click_history.setOnClickListener(this);
        click_search = (CardView) findViewById(R.id.click_search);
        click_search.setOnClickListener(this);
        click_setting = (CardView) findViewById(R.id.click_setting);
        click_setting.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onClick(View v) {
      Intent i;
        switch (v.getId()){
            case R.id.scan_barcode: i = new Intent(this,ScanActivity.class);
            startActivity(i);
            break;
            case R.id.click_search: i = new Intent(this,search.class);
            startActivity(i);
            break;
            case R.id.click_connect: i = new Intent(this,connect.class);
            startActivity(i);
            break;
            case R.id.click_setting: i = new Intent(this,setting.class);
            startActivity(i);
            break;
            case R.id.click_history: i = new Intent(this,history.class);
            startActivity(i);
            break;
            default: break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
