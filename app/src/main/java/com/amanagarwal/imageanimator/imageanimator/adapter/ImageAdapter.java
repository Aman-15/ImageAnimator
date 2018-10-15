package com.amanagarwal.imageanimator.imageanimator.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amanagarwal.imageanimator.imageanimator.R;
import com.amanagarwal.imageanimator.imageanimator.Utils.TextUtil;
import com.amanagarwal.imageanimator.imageanimator.activity.MainActivity;
import com.amanagarwal.imageanimator.imageanimator.network.models.ImageItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private static String LOG_TAG = ImageAdapter.class.getName();

    private Context context;
    private List<ImageItem> imageList;

    public ImageAdapter(Context context, List<ImageItem> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    public void setData(List<ImageItem> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.image_list, null);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder imageViewHolder, int i) {
        final ImageItem item = imageList.get(i);

        String title = item.getTitle();
        int maxLength = title.length() < 40 ? title.length() : 40;
        imageViewHolder.imageTextView.setText(item.getTitle().substring(0, maxLength));

        Picasso.with(context)
                .load(TextUtil.formatURL(item.getMedia().getLink()))
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(imageViewHolder.imageHolderImageView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        TextView imageTextView;
        ImageView imageHolderImageView;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            imageTextView = itemView.findViewById(R.id.imageTextView);
            imageHolderImageView = itemView.findViewById(R.id.imageHolderImageView);
        }
    }

}
