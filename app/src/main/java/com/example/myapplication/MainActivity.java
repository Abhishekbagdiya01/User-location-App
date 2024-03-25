package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {
    Intent service;
    TextView lat;
    TextView lng;
    TextView add;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

       setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

       navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
               int id = menuItem.getItemId();
               Log.d("NavigationDrawer", "Logout Button Clicked");
               if (id == R.id.nav_logout) {
                   Toast.makeText(MainActivity.this,"Logout success",Toast.LENGTH_LONG).show();
                   Intent logoutIntent =  new Intent(MainActivity.this, IntroActivity.class);
                   logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                   startActivity(logoutIntent);

               }
               return true;
           }
       });
         service= new  Intent(this,LocationService.class);
         lat = findViewById(R.id.lat);
         lng = findViewById(R.id.lng);
        add = findViewById(R.id.address);
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    checkPermission();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    void checkPermission(){
    if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED  || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 1);

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"GRANTED",Toast.LENGTH_LONG ).show();
            //Manifest.permission.ACCESS_BACKGROUND_LOCATION
        }

    }else{
        startService(service);

    }
    }
    @Subscribe
   public void receiveLocationEvent(LocationEvent locationEvent){
            String latitude = String.valueOf(locationEvent.getLat());
        String longitude = String.valueOf(locationEvent.getLng());
        String address = locationEvent.getAddress();
        lat.setText(latitude) ;
        lng.setText(longitude) ;
        add.setText(address);

        Log.d("Location Update", "Latitude: " + latitude + ", Longitude: " + longitude + "Address : " + address);
        Toast.makeText(this,"LAT : "+ latitude + " LNG : "+ longitude + " Address : " +address ,Toast.LENGTH_SHORT ).show();

    }
}