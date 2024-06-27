package com.example.currencyconverterpro;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class MainActivity extends AppCompatActivity {

    ListView listView;

    DrawerLayout drawerLayout;

    Button btn_cancel, btn_back, btn_dot;
    Toolbar toolbar;
    NavigationView navigationView;


    TextView amount;
    public List<MyModel> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);
        loadFragment(new Main_Fragment());
        data = new ArrayList<>();
        amount = findViewById(R.id.amount);
        listView = findViewById(R.id.listView);

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_view);

        btn_dot = findViewById(R.id.btn_dot);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_back = findViewById(R.id.btn_back);

        //Step 1
        navigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE)); // change the text Color of navigation items
        navigationView.setItemIconTintList(ColorStateList.valueOf(Color.WHITE));  //change the icon Color of navigation items
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toogle =new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);

        // Optional: Set a listener for the navigation icon to open the drawer
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
//        Add Click on items

        navigationView.setNavigationItemSelectedListener(item -> {
            int id =item.getItemId();
            Fragment selectedFragment = null;

            if (id == R.id.settings) {
                selectedFragment = new Setting_Fragment();

            }
//            else if (id == R.id.theme) {
//                selectedFragment = new Theme_Fragment();
////                loadFragment(new Theme_Fragment());
//                Toast.makeText(MainActivity.this, "Theme is open", Toast.LENGTH_SHORT).show();
//            }
            else if (id == R.id.feedback) {
                selectedFragment = new Feedback_Fragment();

            } else if (id == R.id.about) {
                selectedFragment = new About_Fragment();

            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        Main_Fragment mainFragment = new Main_Fragment();
//        getMenuInflater().inflate(R.menu.menu, menu);
//        MenuItem menuItem = menu.findItem(R.id.actionSearch);
//        SearchView searchView = (SearchView) menuItem.getActionView();
//        searchView.setQueryHint("Type here");
//
//        try {
//            // Access the SearchAutoComplete field within the SearchView
//            Field searchField = SearchView.class.getDeclaredField("mSearchSrcTextView");
//            searchField.setAccessible(true);
//            TextView searchTextView = (TextView) searchField.get(searchView);
//            if (searchTextView != null) {
//                searchTextView.setTextColor(Color.WHITE); // Change text color to white
//                searchTextView.setHintTextColor(Color.LTGRAY); // Change hint text color to light gray
//            }
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                mainFragment.filteredList(newText);
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }


    public void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
       FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
       if(fragment instanceof Main_Fragment){
           fragmentTransaction.add(R.id.fragmentContainerView, fragment);
       }
       else {
           fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
           fragmentTransaction.setReorderingAllowed(true);
           fragmentTransaction.addToBackStack("name"); // Name can be null

       }
            fragmentTransaction.commit();


    }

}