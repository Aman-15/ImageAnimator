package com.amanagarwal.imageanimator.imageanimator.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amanagarwal.imageanimator.imageanimator.R;
import com.amanagarwal.imageanimator.imageanimator.Utils.TextUtil;
import com.amanagarwal.imageanimator.imageanimator.fragment.ImageOnClickListener;
import com.amanagarwal.imageanimator.imageanimator.network.models.ImageItem;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private static String LOG_TAG = ImageAdapter.class.getName();

    private List<ImageItem> imageList;
    private ImageOnClickListener imageOnClickListener;

    public ImageAdapter(List<ImageItem> imageList, ImageOnClickListener imageOnClickListener) {
        this.imageOnClickListener = imageOnClickListener;
        this.imageList = imageList;
    }

    public void setData(List<ImageItem> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.image_list, null);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder imageViewHolder, int i) {
        final ImageItem item = imageList.get(i);

        String title = item.getTitle();
        int maxLength = title.length() < 40 ? title.length() : 40;
        imageViewHolder.imageTextView.setText(item.getTitle().substring(0, maxLength));

        Picasso.with(imageViewHolder.itemView.getContext())
                .load(TextUtil.formatURL(item.getMedia().getLink()))
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(imageViewHolder.imageHolderImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap imageBitmap = ((BitmapDrawable) imageViewHolder.imageHolderImageView.getDrawable()).getBitmap();
                        RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(null, imageBitmap);
                        imageDrawable.setCircular(true);
                        imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                        imageViewHolder.imageHolderImageView.setImageDrawable(imageDrawable);
                    }

                    @Override
                    public void onError() {

                    }
                });

        imageViewHolder.imageHolderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                imageOnClickListener.onImageClick(imageViewHolder.imageHolderImageView, TextUtil.formatURL(item.getMedia().getLink()));
                imageOnClickListener.zoomImageFromThumb(imageViewHolder.imageHolderImageView, imageViewHolder.imageHolderImageView.getDrawable(), TextUtil.formatURL(item.getMedia().getLink()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        TextView imageTextView;
        ImageView imageHolderImageView;
        CardView imageItemCardView;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            imageTextView = itemView.findViewById(R.id.imageTextView);
            imageHolderImageView = itemView.findViewById(R.id.imageHolderImageView);
            imageItemCardView = itemView.findViewById(R.id.imageItemCardView);
        }
    }

}
