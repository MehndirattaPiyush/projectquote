package com.the.project.quotes;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import github.nisrulz.screenshott.ScreenShott;


public class Main2Activity extends AppCompatActivity  {
private TextView title;
private TextView quote;
private Button bb;
private LinearLayout linearLayout;
private TextView name;
private Bitmap bitmap;
private Button submit;
    private final static String[] requestWritePermission =
            { Manifest.permission.WRITE_EXTERNAL_STORAGE };

    String a,b,c,d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //getSupportActionBar().hide();
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
          //      .getColor(R.color.bg_color)));


      /*  getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                       | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                       // | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
*/


        title=(TextView)findViewById(R.id.title2);
        quote=(TextView)findViewById(R.id.quote2);
        name=(TextView)findViewById(R.id.name2);
        bb=(Button) findViewById(R.id.bb);
        submit=(Button)findViewById(R.id.email);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendEmail();
            }
        });

        LinearLayout layout = (LinearLayout)findViewById(R.id.ll);
        //ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) layout.getLayoutParams();
        //lp.height = layout.getWidth();

        SharedPreferences prefs = getSharedPreferences("mysharedPref", MODE_PRIVATE);
        a=prefs.getString("name","name");
        b=prefs.getString("quote","name");
        c=prefs.getString("title","name");
        d=String.format("[%s]",c.toUpperCase());
       //String output = a.substring(0, 1).toUpperCase() + a.substring(1);
       String output= capitalizeFirstLetter(a);
       name.setText(output+" ");
        quote.setText(b.toUpperCase());
        title.setText(d);


        final boolean hasWritePermission = RuntimePermissionUtil.checkPermissonGranted(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

bb.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        bb.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);
        View v = findViewById(R.id.ss);
        bitmap = getScreenshot(v);
        if (bitmap != null) {
            if (hasWritePermission) {
                saveScreenshot();
            }
            else {
                RuntimePermissionUtil.requestPermission(Main2Activity.this, requestWritePermission, 100);
            }
        }
        bb.setVisibility(View.VISIBLE);
        submit.setVisibility(View.VISIBLE);

    }
});

    }

    public static Bitmap getScreenshot(View v){
        v.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        return bitmap;

    }
    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
    private void SendEmail() {

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"Theprojectquote@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Send quotes");
        intent.putExtra(Intent.EXTRA_TEXT, "Title- "+c+"\n\nQuote- "+b
                +"\n\nName- "+a);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);

        }

    }
    private void saveScreenshot() {
        // Save the screenshot

        try {
            File file = ScreenShott.getInstance()
                    .saveScreenshotToPicturesFolder(Main2Activity.this, bitmap, "TheProjectQuote");
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
