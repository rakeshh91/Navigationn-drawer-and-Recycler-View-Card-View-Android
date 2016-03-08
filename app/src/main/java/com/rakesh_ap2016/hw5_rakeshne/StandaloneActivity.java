package com.rakesh_ap2016.hw5_rakeshne;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.konifar.fab_transformation.FabTransformation;
import com.plattysoft.leonids.ParticleSystem;

public class StandaloneActivity extends AppCompatActivity implements HandleNavigationListener, NavigationView.OnNavigationItemSelectedListener {

    MovieData movieData;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private VideoView videoView;
    private TextView text;
    SurfaceView surfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standalone);

        setTitle("Movies");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        final ViewGroup mAppBar = (ViewGroup)findViewById(R.id.appBarAnim);
        animateToolbarDroppingDown(mAppBar);



        final Toolbar standToolbar = (Toolbar)findViewById(R.id.standToolbar);
        standToolbar.inflateMenu(R.menu.standalone_menu);
        animateToolbarDroppingDown(standToolbar);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FabTransformation.with(fab).transformTo(standToolbar);
                animateToolbarDroppingDown(standToolbar);


            }
        });
        ImageView hideButton = (ImageView)standToolbar.findViewById(R.id.hideToolBar);
        hideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FabTransformation.with(fab).transformFrom(standToolbar);
            }
        });

        ImageView particle = (ImageView)standToolbar.findViewById(R.id.burst);


        particle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ParticleSystem(StandaloneActivity.this, 80, R.drawable.confeti2, 10000)
                        .setSpeedModuleAndAngleRange(0f, 0.3f, 180, 180)
                        .setRotationSpeed(144)
                        .setAcceleration(0.00005f, 90)
                        .emit(findViewById(R.id.emiter_top_right), 8,5000);

            new ParticleSystem(StandaloneActivity.this, 80, R.drawable.confeti3, 10000)
                    .setSpeedModuleAndAngleRange(0f, 0.3f, 0, 0)
                    .setRotationSpeed(144)
                    .setAcceleration(0.00005f, 90)
                    .emit(findViewById(R.id.emiter_top_left), 8, 5000);


            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        movieData = new MovieData();

        standToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.duplicate){

                    final Dialog dialog = new Dialog(StandaloneActivity.this);// add here your class name
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.popupmenu);//add your own xml with defied with and height of videoview
                    dialog.show();

                    WindowManager.LayoutParams a = dialog.getWindow().getAttributes();
                    a.dimAmount = 0;
                    dialog.getWindow().setAttributes(a);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                            GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    dialog.getWindow().setAttributes(lp);
                    String uriPath= "android.resource://" + getPackageName() + "/" + R.raw.videoone;
                    VideoView mVideoView = (VideoView)dialog.findViewById(R.id.videoView);
                    getWindow().setFormat(PixelFormat.TRANSLUCENT);
                    Log.v("Vidoe-URI", uriPath + "");
                    MediaController mediaController = new MediaController(StandaloneActivity.this);
                    mediaController.setAnchorView(mVideoView);
                    mVideoView.setMediaController(mediaController);

                    mVideoView.setVideoURI(Uri.parse(uriPath));
                    mVideoView.start();
                }
                return true;
            }
        });

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

