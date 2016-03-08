package com.rakesh_ap2016.hw5_rakeshne;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.FadeOutAnimation;
import com.easyandroidanimations.library.FlipVerticalAnimation;
import com.easyandroidanimations.library.FoldAnimation;
import com.easyandroidanimations.library.FoldLayout;
import com.easyandroidanimations.library.PuffInAnimation;
import com.easyandroidanimations.library.SlideOutUnderneathAnimation;
import com.easyandroidanimations.library.UnfoldAnimation;

import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutMeFragmentActivity extends Fragment {

    private static final String ARG_BUTTON_NUMBER = "button_number";

    public static AboutMeFragmentActivity newInstance(int buttonNumber) {
        AboutMeFragmentActivity aboutMeFragmentActivityFragment = new AboutMeFragmentActivity();
        Bundle args = new Bundle();
        args.putInt(ARG_BUTTON_NUMBER, buttonNumber);
        aboutMeFragmentActivityFragment.setArguments(args);
        return aboutMeFragmentActivityFragment;
    }

    public AboutMeFragmentActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_about_me, container, false);
        ImageView name = (ImageView) rootView.findViewById(R.id.name);
        ImageView subject = (ImageView) rootView.findViewById(R.id.androidsubject);

        new PuffInAnimation(name)
                .setDuration(1000)
                .animate();
        new PuffInAnimation(subject)
                .setDuration(1000)
                .animate();

        return rootView;
    }

}
