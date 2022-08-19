package com.oakspro.truelaw;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.oakspro.truelaw.databinding.ActivityDashBinding;

public class DashActivity extends AppCompatActivity  {
        //implements NavigationView.OnNavigationItemSelectedListener
    private ActivityDashBinding binding;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

//        drawerLayout=findViewById(R.id.drawerLayout);
//        navigationView=findViewById(R.id.navigation_view);
//
//        toggle=new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        navigationView.setNavigationItemSelectedListener(this);
//        Passing each menu ID as a set of Ids because each
//        menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_feed,R.id.navigation_service, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_dash);
       // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }


//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (toggle.onOptionsItemSelected(item)){
//            return true;
//        }
//        return true;
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()){
//            case R.id.navigation_discussion:
//                Toast.makeText(this, "Discussion", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.navigation_more:
//                Toast.makeText(this, "More", Toast.LENGTH_SHORT).show();
//                break;
//        }
//
//        return true;
//    }

}