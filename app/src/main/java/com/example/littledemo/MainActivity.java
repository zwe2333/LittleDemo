package com.example.littledemo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int IMAGE_CODE = 1001;
    private Button btnAdd,btnDel,btnSelect,btnAlter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "08c0c1e50926506394c59c28ed07fd1f");
        initViews();
    }

    private void initViews() {
        btnAdd= (Button) findViewById(R.id.btnAdd);
        btnDel= (Button) findViewById(R.id.btnDel);
        btnSelect= (Button) findViewById(R.id.btnSelect);
        btnAlter= (Button) findViewById(R.id.btnAlter);
        btnAdd.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
        btnAlter.setOnClickListener(this);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==IMAGE_CODE){
            if (data==null){
                return;
            }
            Uri uri=data.getData();

                String[] proj = { MediaStore.Images.Media.DATA };

                // 好像是android多媒体数据库的封装接口，具体的看Android文档
                Cursor cursor = managedQuery(uri, proj, null, null, null);

                // 按我个人理解 这个是获得用户选择的图片的索引值
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                cursor.moveToFirst();
                // 最后根据索引值获取图片路径
                final String path = cursor.getString(column_index);
                if (path!=null){


                    final BmobFile bmobFile=new BmobFile(new File(path));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            bmobFile.uploadblock(new UploadFileListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e==null){
                                        Person person=new Person(bmobFile,"哈哈","学生");
                                        person.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String s, final BmobException e) {
                                                if (e==null){
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                        }
                                                    });

                                                }else {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                }

                            });
                        }
                    }).start();


                }else {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
    //获取Uri
    private static String getRealPathFromUri(Context context, Uri uri){
        String filePath=null;
        String wholeID= DocumentsContract.getDocumentId(uri);

        String id=wholeID.split(":")[1];

        String[] projection={MediaStore.Images.Media.DATA};
        String selection=MediaStore.Images.Media._ID+"=?";
        String[] selectionArgs={id};
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                selection, selectionArgs, null);
        int columnIndex = cursor.getColumnIndex(projection[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAdd:
                Intent intent1=new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent1);
                break;
            case R.id.btnDel:
                break;
            case R.id.btnSelect:
                Intent intent2=new Intent(MainActivity.this,RecyclerViewActivity.class);
                startActivity(intent2);
                break;
            case R.id.btnAlter:
                break;
            default:
                break;
        }
    }
}
