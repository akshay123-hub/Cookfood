package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawer;
    RecyclerView mRecyclerView;
    ActionBarDrawerToggle mToggle;
    NavigationView nav;
    Toolbar toolbar;
    Adapter adapter;
    List<Model> userModel;
    FirebaseAuth auth;
    boolean check=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("CookBook");
        auth=FirebaseAuth.getInstance();
        mDrawer = (DrawerLayout)findViewById(R.id.drawer);

        nav = (NavigationView) findViewById(R.id.navHeader);
        toolbar = findViewById(R.id.common_toolbar);
        setSupportActionBar(toolbar);

        Menu menu = nav.getMenu();
        MenuItem login = menu.findItem(R.id.login);
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            login.setIcon(R.drawable.logout);
            login.setTitle("Log out");
        }

        mToggle = new ActionBarDrawerToggle(this,mDrawer,toolbar,R.string.open,R.string.close);
        mToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:

                        mDrawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.login:
                        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
                            login.setIcon(R.drawable.login);
                            login.setTitle("Log in");
                            auth.signOut();
                            mDrawer.closeDrawer(GravityCompat.START);
                        }else{
                            startActivity(new Intent(getApplicationContext(),Login.class));
                        }
                        break;
                }
                return true;
            }

        });

        userModel=new ArrayList<>();

        userModel.add(new Model("Chicken Parm",R.drawable.chicken_parm,"Rs. 400"));
        userModel.add(new Model("Marinara Sauce",R.drawable.marinara_sauce,"Rs. 420"));
        userModel.add(new Model("Spaghetti meatballs",R.drawable.spaghetti_meatballs,"Rs. 350"));
        userModel.add(new Model("Image",R.drawable.img_1,"Rs. 150"));
        userModel.add(new Model("Garlic Bread",R.drawable.garlicbread,"Rs. 700"));
        userModel.add(new Model("Pepperoni Pizza",R.drawable.pepperoni_pizza,"Rs. 465"));
        userModel.add(new Model("Shrimp Scampi",R.drawable.shrimpscampi,"Rs. 850"));
        userModel.add(new Model("Italian Dressing Salad",R.drawable.italian_dressing_salad,"Rs. 100"));
        userModel.add(new Model("Italian Sub Sandwich",R.drawable.italian_sub_sandwich,"Rs. 590"));
        userModel.add(new Model("Chicken Parm",R.drawable.chicken_parm,"Rs. 860"));



        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new Adapter(userModel,getApplicationContext());
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setBackgroundColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}