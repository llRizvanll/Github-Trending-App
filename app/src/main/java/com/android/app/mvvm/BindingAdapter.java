package com.android.app.mvvm;

import android.view.View;
import android.widget.ImageView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class BindingAdapter {

    @androidx.databinding.BindingAdapter("imageUrl")
    public static void loadImage(CircularImageView view, String url){
        Picasso.get().load(url).into(view);
    }

    @androidx.databinding.BindingAdapter("imageVisibility")
    public static void setImageVisibility(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.VISIBLE);
    }
}
