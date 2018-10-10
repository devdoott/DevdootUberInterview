package com.devdoot.androidInterview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.devdoot.androidInterview.myModel.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter2 extends BaseAdapter {
    private Context mContext;
    private Picasso mPicasso;
    List<Photo>resultList=new ArrayList<>();

    public ImageAdapter2(Context c,Picasso picasso) {
        mContext = c;
        mPicasso=picasso;
    }


    public int getCount() {
        return resultList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
    public void setItems(List<Photo> results) {
        resultList = results;

        Log.d("DBG : "+results.size(),results.toString());
        this.notifyDataSetChanged();
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(240, 240));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
            imageView.setAdjustViewBounds(false);
        } else {
            imageView = (ImageView) convertView;
        }
        String string= "http://farm"+resultList.get(position).getFarm()+".static.flickr.com/"
                +resultList.get(position).getServer()+"/"+resultList.get(position).getId()+"_"
                +resultList.get(position).getSecret()+".jpg";
        Log.d("DBG ",string);
        mPicasso.with(imageView.getContext()).load(string).into(imageView);

        return imageView;
    }

}