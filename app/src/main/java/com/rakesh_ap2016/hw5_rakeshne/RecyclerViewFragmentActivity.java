package com.rakesh_ap2016.hw5_rakeshne;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.PopupMenu;

import java.lang.reflect.Method;
import java.util.HashMap;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerViewFragmentActivity extends Fragment {


    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    MyRecyclerViewAdapter myRecyclerViewAdapter;
    MovieData movieData;
    Button clearButton;
    Button selectAllButton;
    Button deleteButton;

    private static final String ARG_BUTTON_NUMBER = "button_number";

    public static RecyclerViewFragmentActivity newInstance(int buttonNumber) {
        RecyclerViewFragmentActivity recyclerViewFragmentActivity = new RecyclerViewFragmentActivity();
        Bundle args = new Bundle();
        args.putInt(ARG_BUTTON_NUMBER, buttonNumber);
        recyclerViewFragmentActivity.setArguments(args);
        return recyclerViewFragmentActivity;
    }

    public RecyclerViewFragmentActivity() {
        // Required empty public constructor
    }

    private void itemAnimation(){
        FlipInTopXAnimator animator = new FlipInTopXAnimator();

        animator.setAddDuration(300);
        animator.setRemoveDuration(300);
        mRecyclerView.setItemAnimator(animator);
    }

    private void adapterAnimation(){
        AlphaInAnimationAdapter adapter = new AlphaInAnimationAdapter(myRecyclerViewAdapter);

        adapter.setInterpolator(new OvershootInterpolator());
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(adapter);
        SlideInRightAnimationAdapter slideInRightAnimationAdapter = new SlideInRightAnimationAdapter(scaleAdapter);
        slideInRightAnimationAdapter.setDuration(500);
        mRecyclerView.setAdapter(slideInRightAnimationAdapter);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView =  inflater.inflate(R.layout.fragment_recycler_view, container, false);
        setHasOptionsMenu(true);
        movieData = new MovieData();
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.cardList);
        //clearButton = (Button)rootView.findViewById(R.id.clearAllButton);
       // selectAllButton = (Button)rootView.findViewById(R.id.selectAllButton);
       // deleteButton = (Button)rootView.findViewById(R.id.deleteButton);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(),movieData.getMoviesList());
        mRecyclerView.setAdapter(myRecyclerViewAdapter);
        final HandleNavigationListener handleNavigationListener;
        try{
            handleNavigationListener = (HandleNavigationListener)rootView.getContext();
        }catch(ClassCastException e){
            throw new ClassCastException("Hosting activity of the fragment forget to implement OnFragmentInteractionListener");
        }
        myRecyclerViewAdapter.SetOnItemClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                handleNavigationListener.navigateToCardSelected(v, position);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                getActivity().startActionMode(new ActionBarCallBack(position,myRecyclerViewAdapter,movieData.getMoviesList()));

            }

            @Override
            public void onOverflowMenuClick(View v, final int position) {
                android.support.v7.widget.PopupMenu popupMenu = new android.support.v7.widget.PopupMenu(getActivity(),v);
                popupMenu.setOnMenuItemClickListener(new android.support.v7.widget.PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.duplicate:
                                movieData.getMoviesList().add(position + 1, (HashMap) ((HashMap) movieData.getItem(position)).clone());
                                myRecyclerViewAdapter.notifyItemInserted(position + 1);
                                return true;
                            case R.id.delete:
                                movieData.getMoviesList().remove(position);
                                myRecyclerViewAdapter.notifyItemRemoved(position);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                MenuInflater inflater1 = popupMenu.getMenuInflater();

                inflater1.inflate(R.menu.popup_menu ,popupMenu.getMenu());
                popupMenu.show();
            }
        });


        itemAnimation();
        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

       if(menu.findItem(R.id.action_search)==null){
           inflater.inflate(R.menu.search_view, menu);
       }

       SearchView searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
        if(searchView!=null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    int position = movieData.findFirst(query);
                    if(position>=0){
                        mRecyclerView.scrollToPosition(position);
                    }

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        super.onCreateOptionsMenu(menu,inflater);
    }
}
