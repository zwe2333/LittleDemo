package com.example.littledemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Asus on 2017/3/6.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<Person> mPersons;
    private Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgHead;
        TextView mTextView;
        ImageView imgDel;
        public ViewHolder(View itemView) {
            super(itemView);
            imgHead= (ImageView) itemView.findViewById(R.id.img_head);
            mTextView= (TextView) itemView.findViewById(R.id.tv_name);
            imgDel= (ImageView) itemView.findViewById(R.id.imgDel);
        }
    }
    public MyAdapter(List<Person> mPersons,Context mContext){
        this.mPersons=mPersons;
        this.mContext=mContext;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Person person=mPersons.get(position);
        Glide.with(mContext).load(person.mBmobFile.getFileUrl()).into(holder.imgHead);
        holder.mTextView.setText(person.name);
        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPersons.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPersons.size();
    }
}
