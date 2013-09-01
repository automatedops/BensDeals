package net.bensdeals.network;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageLoader {
    public void loadImage(ImageView imageView, String imageUrl) {
        imageView.setImageBitmap(null);
        Picasso.with(imageView.getContext()).load(imageUrl).into(imageView);
    }
}
