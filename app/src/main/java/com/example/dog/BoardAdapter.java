package com.example.dog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.Holder> {
    Context mContext;
    ArrayList<BoardDao> mData = new ArrayList<>();
    private int count = 0;

    public BoardAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void setItem(ArrayList<BoardDao> list){
        mData = list;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =(LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.board_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(mData.get(position).getTitle());
        holder.content.setText(mData.get(position).getContent());
        holder.email.setText(mData.get(position).getEmail());

        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,BoardContentActivity.class);
                intent.putExtra("title",mData.get(position).getTitle());
                intent.putExtra("content",mData.get(position).getContent());
                intent.putExtra("id",mData.get(position).getId());
                mContext.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        TextView email;

        public Holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title1);
            content = itemView.findViewById(R.id.content1);
            email = itemView.findViewById(R.id.con_email);
        }
    }
}
