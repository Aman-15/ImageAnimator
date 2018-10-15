package com.amanagarwal.imageanimator.imageanimator.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amanagarwal.imageanimator.imageanimator.R;
import com.amanagarwal.imageanimator.imageanimator.adapter.ImageAdapter;
import com.amanagarwal.imageanimator.imageanimator.network.models.Image;
import com.amanagarwal.imageanimator.imageanimator.network.ImageDataListener;
import com.amanagarwal.imageanimator.imageanimator.network.NetworkInformation;
import com.amanagarwal.imageanimator.imageanimator.network.RetrofitClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageListFragment extends Fragment implements ImageDataListener {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.emptyTextView)
    TextView emptyTextView;

    @BindView(R.id.imageListRecyclerView)
    RecyclerView imageListRecyclerView;

    private ImageAdapter adaptor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_list_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        imageListRecyclerView = getActivity().findViewById(R.id.imageListRecyclerView);
        imageListRecyclerView.setHasFixedSize(true);

        adaptor = new ImageAdapter(getContext(), null);

        if (NetworkInformation.isConnectedToNetwork(getContext())) {

            RetrofitClient.buildImages(this);
        }
        else {
            emptyTextView.setText("No Internet!");
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure() {
        progressBar.setVisibility(View.INVISIBLE);
        emptyTextView.setText("Error Fetching data!");
    }

    @Override
    public void onDataFetch(Image image) {
        adaptor.setData(image.getItems());
        imageListRecyclerView.setAdapter(adaptor);
        imageListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar.setVisibility(View.GONE);
    }
}
