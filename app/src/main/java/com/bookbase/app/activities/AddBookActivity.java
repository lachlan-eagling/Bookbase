package com.bookbase.app.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bookbase.app.R;
import com.bookbase.app.fragments.AddBooksFragmentAdvanced;
import com.bookbase.app.fragments.AddBooksFragmentBasic;

public class AddBookActivity extends AppCompatActivity {

    FragmentPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        final ViewPager addBooksPager = (ViewPager) findViewById(R.id.addBookPager);
        viewPagerAdapter = new AddBookPagerAdapter(getSupportFragmentManager());
        addBooksPager.setAdapter(viewPagerAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Book");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TabLayout tabs = (TabLayout) findViewById(R.id.tab_slider);
        tabs.setupWithViewPager(addBooksPager);

//        final ActionBar actionBar = getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
//            @Override
//            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
//                addBooksPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
//
//            }
//
//            @Override
//            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
//                // Ignore this event.
//            }
//        };


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    public static class AddBookPagerAdapter extends FragmentPagerAdapter{

        private static int NUM_ITEMS = 2;

        public AddBookPagerAdapter(android.support.v4.app.FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public int getCount(){
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {

            switch(position){
                case 0:
                    return AddBooksFragmentBasic.newInstance(1, "Basic");
                case 1:
                    return AddBooksFragmentAdvanced.newInstance(2, "Advanced");
                default:
                    return null;
            }
        }


        @Override
        public CharSequence getPageTitle(int position){
            return getItem(position).getArguments().getString("title");
        }
    }

}
