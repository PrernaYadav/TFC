package com.infosolution.dev.tfc.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.business.LoginBusinessActivity;
import com.infosolution.dev.tfc.user.Navigation;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private Button btnmailid,btnuserreg,btnbusilogin;
    private TextView tvguest;
    LocationManager locationManager;
    String mprovider;
    // String lat,longi;
    String fulladd;
    TextView textview;
    Geocoder geocoder;
    List<Address> addresses;
    Double latitde;
    Double longitude;
    String area;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        mprovider = locationManager.getBestProvider(criteria, false);

        if (mprovider != null && !mprovider.equals("")) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(mprovider);
    //           locationManager.requestLocationUpdates(mprovider, 15000, 1, this);

            if (location != null)
                onLocationChanged(location);
else
                Toast.makeText(getBaseContext(), "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();


        }




        setContentView(R.layout.activity_main);
        btnmailid=findViewById(R.id.btn_mailid);
        btnuserreg=findViewById(R.id.btn_userregister);
        btnbusilogin=findViewById(R.id.btn_busilogin);
        tvguest=findViewById(R.id.tv_guest);

        btnmailid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginMailActivity.class);
                startActivity(intent);
            }
        });

        btnbusilogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginBusinessActivity.class);
                startActivity(intent);
            }
        });


        btnuserreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginMailActivity.class);
         //       intent.putExtra("citttyy",area);
                startActivity(intent);
            }
        });

        tvguest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Navigation.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onLocationChanged(Location location) {
       /* TextView longitudee = (TextView) findViewById(R.id.textView);
        TextView latitudee = (TextView) findViewById(R.id.textView1);

        longitudee.setText(""+ location.getLongitude());
        latitudee.setText("" + location.getLatitude());*/

        longitude=location.getLongitude();
        latitde=location.getLatitude();


      //  Toast.makeText(MainActivity.this,""+longitude+""+latitde,Toast.LENGTH_LONG).show();




        geocoder = new Geocoder(this, Locale.getDefault());


        try {
            addresses = geocoder.getFromLocation(latitde,longitude,1);
            String address= addresses.get(0).getAddressLine(0);
            area= addresses.get(0).getLocality();
            String city= addresses.get(0).getAdminArea();
            String country= addresses.get(0).getCountryName();


          /*  SharedPreferences sharedPreferences = getSharedPreferences("City", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("city", area);
            editor.commit();*/

            fulladd= address+","+area+","+city+","+country;

            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("usercityy", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("citttyy", area);
            editor.commit();

//            textview.setText(fulladd);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {




    }
}
