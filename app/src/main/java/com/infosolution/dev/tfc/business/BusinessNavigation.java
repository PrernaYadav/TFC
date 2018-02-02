package com.infosolution.dev.tfc.business;

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
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.activities.LoginMailActivity;
import com.infosolution.dev.tfc.business_fragments.EditProfileBusiFragment;
import com.infosolution.dev.tfc.business_fragments.NewOrderdFragment;
import com.infosolution.dev.tfc.business_fragments.OrderHisBusinessFragment;
import com.infosolution.dev.tfc.business_fragments.UploadMenuFragment;
import com.infosolution.dev.tfc.business_fragments.UploadedMenuFragment;
import com.infosolution.dev.tfc.user.Navigation;

public class BusinessNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String Imagee,Namee;
    private  TextView tv;
    private  ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarbusi);
        setSupportActionBar(toolbar);




        final SharedPreferences prefs = getSharedPreferences("LogindataB", MODE_PRIVATE);
        Imagee = prefs.getString("proimg", null);
        Namee = prefs.getString("name", null);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viewbusi);


        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        tv = (TextView)header.findViewById(R.id.tv_namebusiii);
        iv = (ImageView) header.findViewById(R.id.imageViewbusi);


        tv.setText(Namee);
        if (TextUtils.isEmpty(Imagee)){
            iv.setImageResource(R.drawable.icon);
        }else {
            Glide.with(this).load(Imagee).into(iv);
        }
        navigationView.setNavigationItemSelectedListener(this);


        setTitle(Html.fromHtml("<small>The Food Circle - Tonight Plates</small>"));
       // setTitle("The Food Circle");
        UploadMenuFragment fragment =new UploadMenuFragment();
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
        getMenuInflater().inflate(R.menu.business_navigation, menu);
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

        if (id == R.id.nav_upldmenu) {

            setTitle(Html.fromHtml("<small>The Food Circle - Tonight Plates</small>"));
            UploadMenuFragment fragment =new UploadMenuFragment();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment," ");
            fragmentTransaction.commit();


            // Handle the camera action
        } else if (id == R.id.nav_upldHistory) {

            setTitle("Uploaded Menus");
            UploadedMenuFragment fragment =new UploadedMenuFragment();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment," ");
            fragmentTransaction.commit();



        } else if (id == R.id.nav_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Here is the share content body";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));


        } else if (id == R.id.nav_neworder) {
            setTitle("New Order");
            NewOrderdFragment fragment =new NewOrderdFragment();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment," ");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_orderhis) {
            setTitle("Order History");
            OrderHisBusinessFragment fragment =new OrderHisBusinessFragment();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment," ");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_editprofile) {
            setTitle("Edit Profile");
            EditProfileBusiFragment fragment =new EditProfileBusiFragment();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment," ");
            fragmentTransaction.commit();

        }
        else if (id == R.id.logout) {

Alertt();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void Alertt() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(BusinessNavigation.this);
        builder1.setMessage("Do You Want to Logout?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        SharedPreferences preferences =getSharedPreferences("Login Busi", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        finish();

                        Intent intent=new Intent(BusinessNavigation.this,LoginBusinessActivity.class);
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
