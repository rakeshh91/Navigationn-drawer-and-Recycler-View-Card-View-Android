package com.rakesh_ap2016.hw5_rakeshne;

import android.graphics.Color;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rakeshh91 on 2/23/2016.
 */
public class ActionBarCallBack implements ActionMode.Callback {

    int position;
    MyRecyclerViewAdapter myRecyclerViewAdapter;
    MovieData movieData;
    List<Map<String,?>> movieList;
    public ActionBarCallBack(int position, MyRecyclerViewAdapter myRecyclerViewAdapter,List<Map<String,?>> movieList){
        this.position = position;
        this.myRecyclerViewAdapter = myRecyclerViewAdapter;
        this.movieList = movieList;
    }
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.popup_menu,menu);

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        HashMap hm = (HashMap)movieList.get(position);
        mode.setTitle((String)hm.get("name"));
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.duplicate:
                movieList.add(position + 1, (HashMap) ((HashMap) movieList.get(position)).clone());
                myRecyclerViewAdapter.notifyItemInserted(position + 1);
                mode.finish();
                break;
            case R.id.delete:
                movieList.remove(position);
                myRecyclerViewAdapter.notifyItemRemoved(position);
                mode.finish();
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}
