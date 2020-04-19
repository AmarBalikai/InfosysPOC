package com.infosyspoc.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.infosyspoc.R;

public class ModelInformation {
    String title;
    String description;
    String imageHref;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }

    // important code for loading image here
    @BindingAdapter({ "avatar" })
    public static void loadImage(final ImageView imageView, String imageURL) {

        Glide.with(imageView.getContext())

                .setDefaultRequestOptions(new RequestOptions()
                        )
                .load(imageURL)
                .error(R.drawable.thump)
                .placeholder(R.drawable.thump)
                .into(imageView);


    }
}
