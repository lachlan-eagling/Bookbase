package com.bookbase.bookbase.activities;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bookbase.bookbase.R;
import com.bookbase.bookbase.fragments.AboutFragment;
import com.bookbase.bookbase.fragments.BooksFragment;
import com.bookbase.bookbase.fragments.SettingsFragment;
import com.bookbase.bookbase.fragments.StatsFragment;
import com.bookbase.bookbase.fragments.WishlistFragment;

public class MainActivity extends AppCompatActivity implements
        BooksFragment.OnFragmentInteractionListener,
        StatsFragment.OnFragmentInteractionListener,
        WishlistFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private View headerLayout;
    private TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get reference to and setup toolbar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Reference main screen nav drawer layout.
        mDrawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        drawerToggle = setUpDrawerToggle();


        // Get reference to nav drawer.
        nvDrawer = (NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent(nvDrawer);

        // Inflate nav drawer header and get reference to headerText.
        headerLayout = nvDrawer.inflateHeaderView(R.layout.nav_header);
        headerText = (TextView) headerLayout.findViewById(R.id.header_text);

        // Bind nav drawer to ActionBarToggle.
        mDrawer.addDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    private void setupDrawerContent(NavigationView navView){
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        selectDrawerItem(item);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem item){

        // Create fragment class and specify which fragment is instantiated based on menu option.
        Fragment fragment = null;
        Class fragmentClass;

        switch(item.getItemId()){
            case R.id.nav_books:
                fragmentClass = BooksFragment.class;
                break;
            case R.id.nav_stats:
                fragmentClass = StatsFragment.class;
                break;
            case R.id.nav_wishlist:
                fragmentClass = WishlistFragment.class;
                break;
            case R.id.nav_settings:
                fragmentClass = SettingsFragment.class;
                break;
            case R.id.nav_about:
                fragmentClass = AboutFragment.class;
                break;
            default:
                fragmentClass = BooksFragment.class;
        }

        try{
            fragment = (Fragment) fragmentClass.newInstance();
        } catch(InstantiationException e1){
            e1.printStackTrace();
        } catch(IllegalAccessException e2){
            e2.printStackTrace();;
        }

        // Insert fragment.
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // Update UI to indicate currently selected item.
        item.setChecked(true);
        setTitle(item.getTitle());
        mDrawer.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ActionBarDrawerToggle setUpDrawerToggle(){
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}