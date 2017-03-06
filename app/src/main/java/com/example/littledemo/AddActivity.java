package com.example.littledemo;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Asus on 2017/3/6.
 */

public class AddActivity extends AppCompatActivity{
    private static final int IMAGE_CODE = 1001;
    private Button mButton;
    private EditText mEditText;
    private ImageView mImageView;
    private String imgUrl=null;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initViews();
        onClick();
    }

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, IMAGE_CODE);
    }

    private void onClick() {
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=mEditText.getText().toString().trim();
                if (checkNameAndImgUrl(name,imgUrl)){
                    upload(name,imgUrl);
                }
            }
        });
    }

    private void upload(final String name, String imgUrl) {
        final BmobFile bmobFile=new BmobFile(new File(imgUrl));
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        bmobFile.uploadblock(new UploadFileListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    Person person=new Person(bmobFile,name,"学生");
                                    person.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if (e==null){
                                                Toast.makeText(AddActivity.this,"success",Toast.LENGTH_SHORT).show();
                                                finish();
                                            }else {
                                                Toast.makeText(AddActivity.this,"fail",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
        ).start();
    }

    private boolean checkNameAndImgUrl(String name, String imgUrl) {
        if (name!=null&&imgUrl!=null){
            return true;
        }
        return false;
    }

    private void initViews() {
        mEditText= (EditText) findViewById(R.id.edt_name);
        mImageView= (ImageView) findViewById(R.id.imgFile);
        mButton= (Button) findViewById(R.id.btnSubmit);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==IMAGE_CODE) {
            if (data == null) {
                return;
            }
            Uri uri = data.getData();

            String[] proj = {MediaStore.Images.Media.DATA};

            // 好像是android多媒体数据库的封装接口，具体的看Android文档
            Cursor cursor = managedQuery(uri, proj, null, null, null);

            // 按我个人理解 这个是获得用户选择的图片的索引值
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            // 将光标移至开头 ，这个很重要，不小心很容易引起越界
            cursor.moveToFirst();
            // 最后根据索引值获取图片路径
            imgUrl = cursor.getString(column_index);
            Bitmap bm = null;

            // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口

            ContentResolver resolver = getContentResolver();

            try {
                bm = MediaStore.Images.Media.getBitmap(resolver, uri);

                mImageView.setImageBitmap(ThumbnailUtils.extractThumbnail(bm, 200, 200));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            super.onActivityResult(requestCode, resultCode, data);
    }
}
