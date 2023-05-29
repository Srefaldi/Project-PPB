package com.example.quiz;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityDetailBinding {
    private View rootView;
    public TextView detailName;
    public TextView detailTime;
    public TextView detailIngredients;
    public ImageView detailImage;
    public TextView detailDesc;

    private ActivityDetailBinding(View rootView) {
        this.rootView = rootView;
        detailName = rootView.findViewById(R.id.detailName);
        detailTime = rootView.findViewById(R.id.detailTime);
        detailIngredients = rootView.findViewById(R.id.detailIngredients);
        detailImage = rootView.findViewById(R.id.detailImage);
        detailDesc = rootView.findViewById(R.id.detailDesc);
    }

    public static ActivityDetailBinding inflate(LayoutInflater layoutInflater) {
        View rootView = layoutInflater.inflate(R.layout.activity_about_detail, null);
        return new ActivityDetailBinding(rootView);

    }

    public View getRoot() {
        return rootView;
    }
}
