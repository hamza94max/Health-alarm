package com.example.healthalarm.DataSets;


import com.example.healthalarm.Models.Model;
import com.example.healthalarm.R;

import java.util.ArrayList;
import java.util.List;

public class PhotoDataSet {




    public static List<Model> getPhotos() {

        List <Model>  photos ;

        photos = new ArrayList<>();

        photos.add(new Model(R.drawable.focus));
        photos.add(new Model(R.drawable.f));
        photos.add(new Model(R.drawable.leg));
        photos.add(new Model(R.drawable.headace));


        return photos;
    }
}
