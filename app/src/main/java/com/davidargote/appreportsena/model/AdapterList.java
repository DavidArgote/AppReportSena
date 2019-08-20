package com.davidargote.appreportsena.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidargote.appreportsena.R;

import java.util.ArrayList;

public class AdapterList extends BaseAdapter {

    Context context;
    ArrayList<Report> list;

    public AdapterList(Context context, ArrayList<Report> list) {
        this.context = context;
        this.list = new ArrayList<>(list);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ImageView imgPhoto, btnWatch;
        TextView tvTitle;

        view = LayoutInflater.from(context).inflate(R.layout.item_list, viewGroup, false);

        imgPhoto = view.findViewById(R.id.ivPhotoItem);
        btnWatch = view.findViewById(R.id.btnWatchItem);
        tvTitle = view.findViewById(R.id.tvTitleItem);

        /*Convert byte[] to bitmap*/
        if (list.get(i).getImageReport() != null) {
            byte[] photo = list.get(i).getImageReport();
            Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);

            imgPhoto.setImageBitmap(bitmap); // set bitmap to view
        }

        /*Fill in views*/
        tvTitle.setText(list.get(i).getTitleReport());

        btnWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Make intent to description
            }
        });

        return view;
    }
}
