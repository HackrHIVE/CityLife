package com.chirag.rishav.citylife;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class homeFragment extends Fragment  {
    LinearLayout ho;
   // CardView hotel;
    View vv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         vv=inflater.inflate(R.layout.home_nav,container,false);
         ho = vv.findViewById(R.id.hoi);
       // hotel= vv.findViewById(R.id.hotels);
        ho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),Hotels.class);
                startActivity(intent);
            }
        });
        return vv;
    }
}
