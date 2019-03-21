package com.necistudio.vigerpdf.adapter;

/**
 * Created by jarod on 12/15/14.
 */

import android.content.Context;
import android.graphics.Bitmap;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.necistudio.pdfvigerengine.R;

import java.util.ArrayList;

public class VigerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<Bitmap> itemList;
    private LayoutInflater mLayoutInflater;

    public VigerAdapter(Context context, ArrayList<Bitmap> itemList) {
        this.context = context;
        this.itemList = itemList;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.itemList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pdf_item, container, false);

        ImageView imageView = itemView.findViewById(R.id.imgData);
        imageView.setImageBitmap(itemList.get(position));
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}