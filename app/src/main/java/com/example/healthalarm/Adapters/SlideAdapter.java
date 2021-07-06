package com.example.healthalarm.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import com.example.healthalarm.Models.ViewpagerModel;
import java.util.List;

public class SlideAdapter extends PagerAdapter {

    public List <ViewpagerModel> photoslist ;
    final Context context ;


    public SlideAdapter(List<ViewpagerModel> photoslist, Context context) {
        this.photoslist = photoslist;
        this.context = context;
    }

    @Override
    public Object instantiateItem( ViewGroup container, int position) {

        ImageView photo = new ImageView(context);
        photo.setImageResource(photoslist.get(position).getPhoto());
        container.addView(photo,0);

        return photo ;
    }
    @Override
    public int getCount() {
        return photoslist.size(); }

    @Override
    public boolean isViewFromObject(View view,  Object object) {
        return view == object;
    }

    @Override
    public void destroyItem( ViewGroup container, int position,  Object object) {
        container.removeView((View)object);
    }}
