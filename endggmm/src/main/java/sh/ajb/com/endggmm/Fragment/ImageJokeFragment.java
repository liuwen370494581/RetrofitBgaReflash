package sh.ajb.com.endggmm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sh.ajb.com.endggmm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageJokeFragment extends Fragment {


    public ImageJokeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_joke, container, false);
    }

}