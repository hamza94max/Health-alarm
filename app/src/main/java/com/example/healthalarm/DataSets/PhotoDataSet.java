package com.example.healthalarm.DataSets;


import com.example.healthalarm.Models.ViewpagerModel;
import com.example.healthalarm.R;

import java.util.ArrayList;
import java.util.List;

public class PhotoDataSet {

    public static List<ViewpagerModel> getPhotos() {
    List <ViewpagerModel>  photos;
    photos = new ArrayList<>();

        photos.add(new ViewpagerModel(R.drawable.focus));
        photos.add(new ViewpagerModel(R.drawable.f));
        photos.add(new ViewpagerModel(R.drawable.leg));
        photos.add(new ViewpagerModel(R.drawable.headace));

        return photos;
    }
}