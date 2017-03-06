package com.example.littledemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Asus on 2017/3/6.
 */

public class RecyclerViewActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;
    private List<Person> mPersons=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        mPersons.clear();
        getData();
        initViews();
    }

    private void getData() {
                        BmobQuery<Person> query=new BmobQuery<>();
                        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                        query.addWhereEqualTo("sign", "学生");
                        query.setLimit(50);
                        query.findObjects(new FindListener<Person>() {
                            @Override
                            public void done(List<Person> list, BmobException e) {
                                if (e==null){
                                    ((TextView)findViewById(R.id.tmp)).setText(list.size()+"");
                                    for (int i = 0; i <list.size() ; i++) {
                                        Person person=list.get(i);
                                        mPersons.add(person);
                                    }
                                }else {
                                    finish();
                                }
                            }
                        });

    }

    private void initViews() {
        mRecyclerView= (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(RecyclerViewActivity.this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mMyAdapter=new MyAdapter(mPersons,RecyclerViewActivity.this);
        mRecyclerView.setAdapter(mMyAdapter);
        if (mPersons==null){
            ((TextView)findViewById(R.id.tmp)).setText("null");
        }
    }


}
