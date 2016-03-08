package com.rakesh_ap2016.hw5_rakeshne;

import android.view.View;

/**
 * Created by rakeshh91 on 2/22/2016.
 */
public interface RecyclerItemClickListener {
    public void onItemClick(View v,int position);
    public void onItemLongClick(View v,int position);
    public void onOverflowMenuClick(View v,int position);
}
