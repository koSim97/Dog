package com.example.dog;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.Holder> {
    Context mContext;
    ArrayList<BoardData> mData = new ArrayList<>();
    private int count = 0;

    public BoardAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void setItem(ArrayList<BoardData> list){
        mData = list;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =(LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.board_item,parent,false);
        Log.d("test12"," ");
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.title.setText(mData.get(position).getTitle());
        holder.content.setText(mData.get(position).getContent());
        Log.d("test123"," "+mData.get(position).getTitle() +" z "+ mData.size());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;

        public Holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title1);
            content = itemView.findViewById(R.id.content1);
        }
    }
}
