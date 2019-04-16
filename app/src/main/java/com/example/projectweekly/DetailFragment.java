package com.example.projectweekly;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projectweekly.Model.Datum;

import static android.support.constraint.Constraints.TAG;

public class DetailFragment extends Fragment {

    Datum datum;
    TextView tvId, tvFname, tvLname;
    ImageView imageView;
    ImageButton btnShare;

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
                    .load(datum.getAvatar())
                    .into(imageView);
        }

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
