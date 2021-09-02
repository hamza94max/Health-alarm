package com.example.healthalarm.DataSets;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import com.example.healthalarm.Models.ViewpagerModel;
import com.example.healthalarm.R;

import java.util.ArrayList;
import java.util.List;

public class PhotoDataSet {

    public static List<ViewpagerModel> getPhotos() {
    List <ViewpagerModel>  photos;
    photos = new ArrayList<>();


        photos.add(new ViewpagerModel(R.drawable.f));
        photos.add(new ViewpagerModel(R.drawable.fresh));
        photos.add(new ViewpagerModel(R.drawable.breathe));
        photos.add(new ViewpagerModel(R.drawable.live));
        photos.add(new ViewpagerModel(R.drawable.leg));
        photos.add(new ViewpagerModel(R.drawable.relax));
        photos.add(new ViewpagerModel(R.drawable.headace));
        photos.add(new ViewpagerModel(R.drawable.taketime));


        return photos;
    }
}
