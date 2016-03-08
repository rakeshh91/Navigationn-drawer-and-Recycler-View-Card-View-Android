package com.rakesh_ap2016.hw5_rakeshne;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.transition.Slide;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.easyandroidanimations.library.FadeInAnimation;
import com.easyandroidanimations.library.FadeOutAnimation;
import com.easyandroidanimations.library.FlipHorizontalAnimation;
import com.easyandroidanimations.library.ParallelAnimator;
import com.easyandroidanimations.library.RotationAnimation;
import com.easyandroidanimations.library.ScaleInAnimation;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        final ImageView imageView = (ImageView)findViewById(R.id.logoName);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ViewGroup mAppBar = (ViewGroup)findViewById(R.id.appBarAnim);
        mAppBar.setVisibility(View.INVISIBLE);
        final ImageView assignName = (ImageView)findViewById(R.id.assignName);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                new ParallelAnimator()
                        .add(new RotationAnimation(imageView))
                        .setDuration(5000)
                        .add(new FlipHorizontalAnimation(imageView))
                        .setDuration(5000)
                        .add(new FadeOutAnimation(imageView))
                        .setDuration(5000)
                        .animate();
                new ParallelAnimator()
                        .add(new RotationAnimation(assignName))
                        .setDuration(5000)
                        .add(new FlipHorizontalAnimation(assignName))
                        .setDuration(5000)
                        .add(new FadeInAnimation(assignName))
                        .setDuration(5000)
                        .animate();
                mAppBar.setVisibility(View.VISIBLE);
                animateToolbarDroppingDown(mAppBar);
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if(bundle!=null){
            int a = bundle.getInt("aboutMe");
            if(a==1){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, AboutMeFragmentActivity.newInstance(R.id.fragmentAboutMe))
                        .addToBackStack("store")
                        .commit();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                mAppBar.setVisibility(View.VISIBLE);
                setTitle("About Me");
            }
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.aboutMe) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, AboutMeFragmentActivity.newInstance(R.id.fragmentAboutMe))
                    .addToBackStack("store")
                    .commit();
            setTitle("About Me");
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
}
