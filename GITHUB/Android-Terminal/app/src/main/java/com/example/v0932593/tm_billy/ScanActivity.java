package com.example.v0932593.tm_billy;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.ServerSocket;


public class ScanActivity extends AppCompatActivity implements View.OnClickListener {
   // DecoratedBarcodeView barcodeView;
    private Toolbar mTopToolbar = null;
    EditText contentSend;
    TextView contentRespond;
    TextView tvStatus;
    TextView IPClinet;
    TextView IPServer;
    ScanActivity activity;
    ServerSocket serverSocket;
    String message = "";
    ImageView btnSend;
    ImageView btnRefresh;
    String logW = "";
    public int count_log = 0;
    public home act_home;

   // private static ScanActivity _instance;
    
    private SocketCom socketCom ;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        setSupportActionBar(mTopToolbar);

        InitViews();
        InitEvent();
        InitPerms();

        //barcodeView.setStatusText("");
        socketCom = new SocketCom();
        socketCom.SendRequest(ScanActivity.this, "Readly");
        //tvStatus.getText();
//        if(socketCom.temp == 1){
//            contentRespond.setBackgroundColor(getResources().getColor(android.R.color.white));
//        }


    }

    private void InitPerms() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //btnFaceId.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    private void InitViews(){
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar_scan);
//        barcodeView = (DecoratedBarcodeView) findViewById(R.id.barcode_scanner);
//        barcodeView.decodeContinuous(barcodeCallback);
        contentSend = (EditText) findViewById(R.id.contentSend);
        contentRespond = (TextView) findViewById(R.id.contentRespond);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        btnSend = (ImageView) findViewById(R.id.btnSend);
        btnRefresh = (ImageView) findViewById(R.id.btnRefresh);
        IPClinet = (TextView) findViewById(R.id.IPClient);
        IPServer = (TextView) findViewById(R.id.IPServer);
    }

    private void InitEvent(){
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Click send success",Toast.LENGTH_LONG).show();
                //contentRespond.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                if (contentSend.getText().toString() !=""){
                    socketCom.SendRequest(ScanActivity.this, contentSend.getText().toString());
                    //Toast.makeText(getApplicationContext(),"Click send success",Toast.LENGTH_LONG).toString();
                    setEditTextFocus(false);
                }
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Click F5 success",Toast.LENGTH_LONG).show();
                contentRespond.setBackgroundColor(getResources().getColor(android.R.color.white));
                socketCom.SendRequest(ScanActivity.this,"UNDO");
                contentSend.setText("UNDO" +"");
                contentSend.selectAll();

                if (contentSend.getText().toString() !="")  {
                    socketCom.SendRequest(ScanActivity.this, contentSend.getText().toString());
                    setEditTextFocus(false);
                }
            }
        });
    }

    public void setEditTextFocus(boolean isFocused) {
        contentRespond.setCursorVisible(isFocused);
        contentRespond.setFocusable(isFocused);
        contentRespond.setFocusableInTouchMode(isFocused);

        if (isFocused) {
            contentRespond.requestFocus();
        }
    }

    private void InitSocket(String barcode) {
        socketCom.SendRequest(this, barcode);
    }

    static final int socketServerPORT = 55962;
//    private BarcodeCallback barcodeCallback = new BarcodeCallback() {
//
//
//        @Override
//        public void barcodeResult(BarcodeResult result) {
//            //Toast.makeText(getApplicationContext(),result.toString(),Toast.LENGTH_SHORT).show();
//            contentSend.setText(result.toString());
//            if (contentSend.toString() != "") {
//                InitSocket(result.toString());
//            }
//        }
//
//
//        @Override
//        public void possibleResultPoints(List<ResultPoint> resultPoints) {
//     }
//    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPause() {
        super.onPause();
        //barcodeView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //barcodeView.resume();
    }


    @Override
    public void onClick(View view) {
        if (contentSend.getText().toString() !=""){
            setEditTextFocus(false);
            socketCom.SendRequest(ScanActivity.this, contentSend.getText().toString());
       }

    }
}
