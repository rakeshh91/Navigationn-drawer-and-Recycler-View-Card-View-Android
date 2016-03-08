package com.rakesh_ap2016.hw5_rakeshne;

import android.view.View;

/**
 * Created by rakeshh91 on 2/22/2016.
 */
public interface HandleNavigationListener {
    public void navigateToItemSelected(int buttonId);
    public void navigateToCardSelected(View v,int position);
}
