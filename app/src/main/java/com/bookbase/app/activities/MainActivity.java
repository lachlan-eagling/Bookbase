package com.bookbase.app.activities;

import android.content.Context;
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

import com.bookbase.app.R;
import com.bookbase.app.database.AppDatabase;
import com.bookbase.app.aboutScreen.AboutFragment;
import com.bookbase.app.book.BooksFragment;
import com.bookbase.app.fragments.SettingsFragment;
import com.bookbase.app.fragments.StatsFragment;
import com.bookbase.app.wishlist.WishlistFragment;
import com.bookbase.app.model.entity.Author;
import com.bookbase.app.model.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        BooksFragment.OnFragmentInteractionListener,
        StatsFragment.OnFragmentInteractionListener,
        WishlistFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private View headerLayout;
    private TextView headerText;

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);

        // Get reference to and setup toolbar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                dummyBookFactory(20);
            }
        }).start();

        selectDrawerItem(nvDrawer.getMenu().getItem(0));
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

    // Inserts list of dummy book records for testing.
    synchronized private void dummyBookFactory(int num){

        // Generate a Book list of length == num param.
        List<Book> books = new ArrayList<>();
        for(int i = 1; i <= num; i++){
            books.add(new Book("Dummy Book " + i, new Author("Billy Bob")));
        }

        // Delete all existing book records and insert list.
        AppDatabase db = AppDatabase.getDatabase(this);
        List<Book> currBooks = db.bookDao().getBooks();
        if(currBooks.isEmpty()){
            db.bookDao().deleteAll();

            for(Book book:books){
                db.bookDao().insertAll(book);
            }
        }

    }

    @Override
    public void setTitle(CharSequence title) {
        toolbarTitle.setText(title);
    }

    public static Context getContext(){
        return context;
    }
}