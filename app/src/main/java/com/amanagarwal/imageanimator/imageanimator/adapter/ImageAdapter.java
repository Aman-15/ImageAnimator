package com.amanagarwal.imageanimator.imageanimator.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amanagarwal.imageanimator.imageanimator.R;
import com.amanagarwal.imageanimator.imageanimator.Utils.TextUtil;
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
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {
        String title = imageList.get(i).getTitle();
        int maxLength = title.length() < 20 ? title.length() : 20;
        imageViewHolder.imageTextView.setText(imageList.get(i).getTitle().substring(0, maxLength));

        Picasso picasso = new Picasso.Builder(context).listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Log.e(LOG_TAG, "onImageLoadFailed");
                exception.printStackTrace();
            }
        }).build();

        picasso.with(context).setLoggingEnabled(true);

        picasso.with(context)
                .load(TextUtil.formatURL(imageList.get(i).getMedia().getLink()))
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
