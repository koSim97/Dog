package com.example.dog.protection;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dog.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {

    private Context mContext;
    // rcAdapter에 들어갈 list
    private final ArrayList<Item> listItem = new ArrayList<>();
    private ArrayList<String> kindCd = new ArrayList<>();
    private ArrayList<String> info = new ArrayList<>();
    private ArrayList<String> noticeNo = new ArrayList<>();
    private ArrayList<String> happenPlace = new ArrayList<>();
    private ArrayList<String> specialMark = new ArrayList<>();
    private ArrayList<String> happenDt = new ArrayList<>();
    private ArrayList<String> noticeDt = new ArrayList<>();
    private ArrayList<String> careNm = new ArrayList<>();
    private ArrayList<String> careTel = new ArrayList<>();
    private ArrayList<String> careAddr = new ArrayList<>();
    private ArrayList<String> popfile = new ArrayList<>();

    public RecyclerAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_protection_recyclerview, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(listItem.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수
        return listItem.size();
    }

    public void addItem(Item item) {
        // 외부에서 item을 추가
        listItem.add(item);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgpopfile;
        private final TextView tvkindCd, tvhappenPlace, tvcareAddr;


        ItemViewHolder(View itemView) {
            super(itemView);

            imgpopfile = itemView.findViewById(R.id.img_popfile);
            tvkindCd = itemView.findViewById(R.id.tv_kindCd);
            tvhappenPlace = itemView.findViewById(R.id.tv_happenPlace);
            tvcareAddr = itemView.findViewById(R.id.tv_careAddr);

            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(mContext, ProtectionItemActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        intent.putExtra("kindCd", kindCd.get(pos));
                        intent.putExtra("info", info.get(pos));
                        intent.putExtra("noticeNo", noticeNo.get(pos));
                        intent.putExtra("happenPlace", happenPlace.get(pos));
                        intent.putExtra("specialMark", specialMark.get(pos));
                        intent.putExtra("happenDt", happenDt.get(pos));
                        intent.putExtra("noticeDt", noticeDt.get(pos));
                        intent.putExtra("careNm", careNm.get(pos));
                        intent.putExtra("careTel", careTel.get(pos));
                        intent.putExtra("careAddr", careAddr.get(pos));
                        intent.putExtra("popfile", popfile.get(pos));

                        mContext.startActivity(intent);
                    }
                }
            });

        }

        void onBind(Item item) {

            kindCd.add(item.getKindCd());
            info.add(item.getSexCd() + " / " + item.getColorCd() + " / " + item.getAge() + " / " + item.getWeight());
            noticeNo.add(item.getNoticeNo());
            happenPlace.add(item.getHappenPlace());
            specialMark.add(item.getSpecialMark());
            happenDt.add(item.getHappenDt());
            noticeDt.add(item.getNoticeSdt() + " - " + item.getNoticeEdt());
            careNm.add(item.getCareNm());
            careTel.add(item.getCareTel());
            careAddr.add(item.getCareAddr());
            popfile.add(item.getPopfile());

            tvkindCd.setText(item.getKindCd());
            tvhappenPlace.setText(item.getHappenPlace());
            tvcareAddr.setText(item.getCareAddr());
            new DownloadFilesTask().execute(item.getPopfile());
        }

        private class DownloadFilesTask extends AsyncTask<String,Void, Bitmap> {
            @Override
            protected Bitmap doInBackground(String... strings) {
                Bitmap bmp = null;
                try {
                    String img_url = strings[0]; //url of the image
                    URL url = new URL(img_url);
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bmp;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(Bitmap result) {
                imgpopfile.setImageBitmap(result);
            }
        }
    }
}