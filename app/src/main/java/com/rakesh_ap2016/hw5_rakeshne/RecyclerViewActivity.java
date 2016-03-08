package com.rakesh_ap2016.hw5_rakeshne;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;

import java.lang.reflect.Method;

public class RecyclerViewActivity extends MainActivity implements HandleNavigationListener{
    MovieData movieData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recycler_view);

        setTitle("Movies");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        ViewGroup mAppBar = (ViewGroup)findViewById(R.id.appBarAnim);
        animateToolbarDroppingDown(mAppBar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        movieData = new MovieData();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recyclerContainer,RecyclerViewFragmentActivity.newInstance(1))
                .commit();
    }


    @Override
    public void navigateToItemSelected(int buttonId) {

    }

    @Override
    public void navigateToCardSelected(View v, int position) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recyclerContainer, Movie_Fragement_ViewPager.newInstance(movieData.getItem(position)))
                .addToBackStack("store")
                .commit();
    }


    public static void animateToolbarDroppingDown(View containerToolbar) {

        containerToolbar.setRotationX(-90);
        containerToolbar.setAlpha(0.2F);
        containerToolbar.setPivotX(0.0F);
        containerToolbar.setPivotY(0.0F);
        Animator alpha = ObjectAnimator.ofFloat(containerToolbar, "alpha", 0.2F, 0.4F, 0.6F, 0.8F, 1.0F).setDuration(4000);
        Animator rotationX = ObjectAnimator.ofFloat(containerToolbar, "rotationX", -90, 60, -45, 45, -10, 30, 0, 20, 0, 5, 0).setDuration(8000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(alpha, rotationX);
        animatorSet.start();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.aboutMe) {

            Intent i = new Intent(this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("aboutMe",1);
            i.putExtras(bundle);
            startActivity(i);

        } else if (id == R.id.task1) {
            Intent i = new Intent(this, RecyclerViewActivity.class);
            startActivity(i);

        } else if (id == R.id.task2) {
            Intent i = new Intent(this,StandaloneActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
