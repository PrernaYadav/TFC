package com.infosolution.dev.tfc.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.fragment.EditProfileFragment;
import com.infosolution.dev.tfc.activities.LoginMailActivity;
import com.infosolution.dev.tfc.business_fragments.OrderHistoryFragment;
import com.infosolution.dev.tfc.fragment.FavFragment;
import com.infosolution.dev.tfc.fragment.HomeFragment;



public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


   private ImageView ivimg;
   private TextView tvnavheader;
   private String Imagee,Namee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

   /*ivimg=findViewById(R.id.imageViewnavheader);
   tvnavheader=findViewById(R.id.tv_navheader);*/

        final SharedPreferences prefs = getSharedPreferences("useriddsign", MODE_PRIVATE);
        Imagee = prefs.getString("profileimage", null);
        Namee = prefs.getString("usernamesign", null);


       /* tvnavheader.setText(Name);

        if (Imagee.equals("")){
            ivimg.setImageResource(R.drawable.icon);
        }else {
            Glide.with(this).load(Imagee).into(ivimg);
        }*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        tvnavheader = (TextView)header.findViewById(R.id.tv_navheader);
        ivimg = (ImageView) header.findViewById(R.id.imageViewnavheader);
      tvnavheader.setText(Namee);




        navigationView.setNavigationItemSelectedListener(this);

        HomeFragment fragment =new HomeFragment();
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment," ");
        fragmentTransaction.commit();
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_dashboard){

            HomeFragment fragment =new HomeFragment();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment," ");
            fragmentTransaction.commit();

        }

    else    if (id == R.id.nav_camera) {

            FavFragment fragment =new FavFragment();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment," ");
            fragmentTransaction.commit();


            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

            OrderHistoryFragment fragment =new OrderHistoryFragment();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment," ");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_slideshow) {

            EditProfileFragment fragment =new EditProfileFragment();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment," ");
            fragmentTransaction.commit();



        } else if (id == R.id.nav_manage) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Here is the share content body";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        } else if (id == R.id.nav_share) {

            Alert();


         /*   SharedPreferences preferences =getSharedPreferences("Login Credentials", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            finish();

            Intent intent=new Intent(this,LoginMailActivity.class);
            startActivity(intent);*/

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void Alert() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(Navigation.this);
        builder1.setMessage("Do You Want to Logout?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences preferences =getSharedPreferences("Login Credentials", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        finish();

                        Intent intent=new Intent(Navigation.this,LoginMailActivity.class);
                        startActivity(intent);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
