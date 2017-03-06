package com.example.littledemo;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Asus on 2017/3/6.
 */

public class Student extends BmobObject{
    private String image;

    private BmobFile imgFile;
    public  Student(){

    }
    public Student(String image,BmobFile imgFile){
        this.image=image;
        this.imgFile=imgFile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BmobFile getImgFile() {
        return imgFile;
    }

    public void setImgFile(BmobFile imgFile) {
        this.imgFile = imgFile;
    }
}
