package com.example.hitesh.project1_1;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();
        if( intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
            String movieDetails = intent. getStringExtra(Intent.EXTRA_TEXT);
            Movie movie = intent.getExtras().getParcelable("movieinfo");
            ((TextView)v.findViewById(R.id.textView2) ).setText(movie.getTitle());
        }
        return v;
    }
}
