package com.example.dog.protection;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dog.R;

import java.util.ArrayList;

public class RecyclerAdapterPetMark extends RecyclerView.Adapter<RecyclerAdapterPetMark.petMarkViewHolder> {

    private ArrayList<petMark> arrayList;
    private Context context;

    public RecyclerAdapterPetMark(ArrayList<petMark> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public petMarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_petmark_recyclerview, parent, false);
        petMarkViewHolder holder = new petMarkViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull petMarkViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getPopfile())
                .into(holder.iv_popfile);
        holder.tv_kindCd.setText(arrayList.get(position).getKindCd());
        holder.tv_happenPlace.setText(arrayList.get(position).getHappenPlace());
        holder.tv_careAddr.setText(arrayList.get(position).getCareAddr());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class petMarkViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_popfile;
        TextView tv_kindCd;
        TextView tv_happenPlace;
        TextView tv_careAddr;

        public petMarkViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_popfile = itemView.findViewById(R.id.img_popfile_petmark);
            this.tv_kindCd = itemView.findViewById(R.id.tv_kindCd_petmark);
            this.tv_happenPlace = itemView.findViewById(R.id.tv_happenPlace_petmark);
            this.tv_careAddr = itemView.findViewById(R.id.tv_careAddr_petmark);

            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, PetMarkItemActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        intent.putExtra("kindCd", arrayList.get(position).getKindCd());
                        intent.putExtra("info", arrayList.get(position).getInfo());
                        intent.putExtra("noticeNo", arrayList.get(position).getNoticeNo());
                        intent.putExtra("happenPlace", arrayList.get(position).getHappenPlace());
                        intent.putExtra("specialMark", arrayList.get(position).getSpecialMark());
                        intent.putExtra("happenDt", arrayList.get(position).getHappenDt());
                        intent.putExtra("noticeDt", arrayList.get(position).getNoticeDt());
                        intent.putExtra("careNm", arrayList.get(position).getCareNm());
                        intent.putExtra("careTel", arrayList.get(position).getCareTel());
                        intent.putExtra("careAddr", arrayList.get(position).getCareAddr());
                        intent.putExtra("popfile", arrayList.get(position).getPopfile());

                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}