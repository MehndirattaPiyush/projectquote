package com.the.project.quotes;


import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import github.nisrulz.screenshott.ScreenShott;


public class Main2Activity extends AppCompatActivity  {
private TextView title;
private TextView quote;
private FloatingActionButton bb;
private LinearLayout linearLayout;
private TextView name;
private Bitmap bitmap;
    private final static String[] requestWritePermission =
            { Manifest.permission.WRITE_EXTERNAL_STORAGE };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();


        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        title=(TextView)findViewById(R.id.title2);
        quote=(TextView)findViewById(R.id.quote2);
        name=(TextView)findViewById(R.id.name2);
        bb=(FloatingActionButton) findViewById(R.id.bb);

        LinearLayout layout = (LinearLayout)findViewById(R.id.ll);

        String a,b,c,d;
        SharedPreferences prefs = getSharedPreferences("mysharedPref", MODE_PRIVATE);
        a=prefs.getString("name","name");
        b=prefs.getString("quote","name");
        c=prefs.getString("title","name");
        d=String.format("[%s]",c.toUpperCase());
        name.setText(a+" ");
        quote.setText(b.toUpperCase());
        title.setText(d);


        final boolean hasWritePermission = RuntimePermissionUtil.checkPermissonGranted(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

bb.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        bb.setVisibility(View.GONE);
        bitmap = ScreenShott.getInstance().takeScreenShotOfRootView(view);
        if (bitmap != null) {
            if (hasWritePermission) {
                saveScreenshot();
            }
            else {
                RuntimePermissionUtil.requestPermission(Main2Activity.this, requestWritePermission, 100);
            }
        }
        bb.setVisibility(View.VISIBLE);

    }
});

    }

    private void saveScreenshot() {
        // Save the screenshot

        try {
            File file = ScreenShott.getInstance()
                    .saveScreenshotToPicturesFolder(Main2Activity.this, bitmap, "my_screenshot");
            // Display a toast
            Toast.makeText(Main2Activity.this, "Image Saved" /*+ file.getAbsolutePath()*/,
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {
        switch (requestCode) {
            case 100: {

                RuntimePermissionUtil.onRequestPermissionsResult(grantResults, new RPResultListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            saveScreenshot();
                        }
                    }

                    @Override
                    public void onPermissionDenied() {
                        Toast.makeText(Main2Activity.this, "Permission Denied! You cannot save image!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
    }

    }
