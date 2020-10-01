package com.example.carnetmusculation.Controllers;


import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.carnetmusculation.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar
                ,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //startService(new Intent(this, LocalService.class));
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CarnetsFragment()).addToBackStack(null).commit();
            navigationView.setCheckedItem(R.id.nav_carnets);
        }
    }

    @Override
    public void onBackPressed(){
        //this.finish();
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    //si l'utilisateur appuie sur le bouton Home
    @Override
    protected void onUserLeaveHint()
    {
        Log.d("onUserLeaveHint","Home button pressed");
        super.onUserLeaveHint();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_carnets:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CarnetsFragment())
                        .addToBackStack(null).commit();
                break;
            case R.id.nav_exercices:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MesExercicesFragment())
                        .addToBackStack(null).commit();
                break;
            case R.id.nav_chronometre:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ChronometreFragment())
                        .addToBackStack(null).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
