package com.android.ld.snakebartest;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class Main2Activity extends AppCompatActivity {

    private File myCaptureFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


    }

    public void copy(View view) {
        //检查权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //如果没有权限就去申请  回调在onRequestPermissionsResult方法中
            ActivityCompat.requestPermissions(Main2Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);//1是回调码switch中判断
        } else {
            //如果有权限开始操作
            copyFile();
        }


    }

    private void copyFile() {
        myCaptureFile = new File(dst);
        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion < 24) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(myCaptureFile));
            startActivityForResult(intent, 1);
        } else {
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, myCaptureFile.getAbsolutePath());
            Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, 1);
        }


        //startActivityForResult(intent, 1);


    }

    private void copypath(final String path1, final String path2) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*String sroce= Environment.getExternalStorageDirectory()+"/xsj1.7.5.apk";
                String dst=Environment.getExternalStorageDirectory()+"/xsj1.7.6.apk";*/
                try {
                    Log.e("tag", "开始");
                    String data = FileUtils.encodeBase64File(path1);
                    Log.e("tag", "回写");
                    FileUtils.decoderBase64File(data, path2);
                } catch (Exception e) {
                    Log.e("tag", "错误");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
//是否同意了权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//同意
                    copyFile();
                } else {
//拒绝 已经拒绝过也走这边
                    Toast.makeText(this, "拒绝了权限", Toast.LENGTH_SHORT).show();
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    String dst = Environment.getExternalStorageDirectory() + "/xsj1.7.6.apk";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
            Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            String img_path = actualimagecursor.getString(actual_image_column_index);
            copypath(img_path, dst);
            File file = new File(img_path);

            Toast.makeText(Main2Activity.this, file.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}

