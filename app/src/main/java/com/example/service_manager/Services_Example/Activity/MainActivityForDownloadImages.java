package com.example.service_manager.Services_Example.Activity;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Parcelable;
import android.os.ResultReceiver;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;



import com.example.service_manager.R;
import com.example.service_manager.Services_Example.Services.DownloadService;

import java.io.Serializable;


public class MainActivityForDownloadImages extends AppCompatActivity {

//    String urls = "https://media.glassdoor.com/sqll/2009086/techsevin-squarelogo-1540798819705.png";
//    String urls = "https://stimg.cardekho.com/images/carexteriorimages/930x620/Kia/Kia-Seltos/6232/1562746799300/front-left-side-47.jpg";
    String urls = "https://images5.alphacoders.com/609/609173.jpg";
    Button button;
     public final Context context = this;
    ProgressDialog progressDialog ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_for_download_images);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }


        progressDialog = new ProgressDialog(context);
        button = findViewById(R.id.downloadImagebtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // initialize the progress dialog like in the first example
                // this is how you fire the downloader

                Intent intent = new Intent(context, DownloadService.class);
                intent.putExtra("url", urls);
                intent.putExtra("receiver", new DownloadReceiver(new Handler()));
                startService(intent);


            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {


            }
        }
    }

    private class DownloadReceiver extends ResultReceiver {

        public DownloadReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            super.onReceiveResult(resultCode, resultData);

            if (resultCode == DownloadService.UPDATE_PROGRESS) {

                int progress = resultData.getInt("progress"); //get the progress
                progressDialog.setProgress(progress);
                progressDialog.setMessage("Images Is Downloading");
                progressDialog.show();

                if (progress == 100) {

                    progressDialog.dismiss();

                }
            }
        }
    }

}
