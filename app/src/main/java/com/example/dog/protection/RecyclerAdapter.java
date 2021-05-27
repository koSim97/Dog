package com.example.dog.protection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dog.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {

    // rcAdapter에 들어갈 list
    private ArrayList<Item> listItem = new ArrayList<Item>();



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

        private ImageView imgpopfile;
        private TextView tvkindCd, tvhappenPlace, tvcareAddr;


        ItemViewHolder(View itemView) {
            super(itemView);

            imgpopfile = itemView.findViewById(R.id.img_popfile);
            tvkindCd = itemView.findViewById(R.id.tv_kindCd);
            tvhappenPlace = itemView.findViewById(R.id.tv_happenPlace);
            tvcareAddr = itemView.findViewById(R.id.tv_careAddr);

        }

        void onBind(Item item) {
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