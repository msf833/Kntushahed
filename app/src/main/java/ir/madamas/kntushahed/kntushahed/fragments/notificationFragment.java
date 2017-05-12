package ir.madamas.kntushahed.kntushahed.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.madamas.kntushahed.kntushahed.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class notificationFragment extends Fragment {


    public notificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View notificationFragmentView =  inflater.inflate(R.layout.fragment_notification, container, false);

        // Inflate the layout for this fragment
        return notificationFragmentView;
    }

}
