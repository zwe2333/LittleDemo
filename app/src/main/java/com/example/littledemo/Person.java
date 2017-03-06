package com.example.littledemo;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Asus on 2017/3/6.
 */

public class Person extends BmobObject{
    BmobFile mBmobFile;
    String name;
    String sign;
    public Person(BmobFile mBmobFile,String name,String sign){
        this.sign=sign;
        this.name=name;
        this.mBmobFile=mBmobFile;
    }
}
