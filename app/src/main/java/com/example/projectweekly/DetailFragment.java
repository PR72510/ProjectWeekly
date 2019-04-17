package com.example.projectweekly;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.projectweekly.Model.Datum;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;

import static android.support.constraint.Constraints.TAG;

public class DetailFragment extends Fragment {

    Datum datum;
    TextView tvId, tvFname, tvLname;
    ImageView imageView;
    ImageButton btnShare;
    File file;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment,container,false);
        tvId = view.findViewById(R.id.tv_ID);
        tvFname = view.findViewById(R.id.tv_First_Name);
        tvLname = view.findViewById(R.id.tv_Last_Name);
        imageView = view.findViewById(R.id.imageView);
        btnShare = view.findViewById(R.id.imageButton);
        datum = new Datum(getArguments().getString("avatar"), getArguments().getString("fName"), getArguments().getInt("ID"), getArguments().getString("lName"));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: " + datum.getId());
        tvId.setText(datum.getId()+"");
        tvFname.setText(datum.getFirstName());
        tvLname.setText(datum.getLastName());
        Context context = imageView.getContext();
        if(context!=null){
            Glide.with(context)
                    .asBitmap()
                    .load(datum.getAvatar())
                    .into(imageView);

            Glide.with(context)
                    .asBitmap()
                    .load(datum.getAvatar())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            OutputStream outputStream;

                            try {
                                file = new File( getActivity().getFilesDir(), "img_Share.jpg");
                                boolean ifCreatedNewFile = file.createNewFile();

                                outputStream = new FileOutputStream(file);
                                resource.compress(Bitmap.CompressFormat.JPEG, 25, outputStream);
                                outputStream.flush();
                                outputStream.close();



                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(
                        getContext(), BuildConfig.APPLICATION_ID + ".provider", file));
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "share Using"));
            }
        });
    }
}
