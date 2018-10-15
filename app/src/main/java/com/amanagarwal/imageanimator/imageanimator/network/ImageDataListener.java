package com.amanagarwal.imageanimator.imageanimator.network;

import com.amanagarwal.imageanimator.imageanimator.network.models.Image;

public interface ImageDataListener {
    void onFailure();
    void onDataFetch(Image image);
}
