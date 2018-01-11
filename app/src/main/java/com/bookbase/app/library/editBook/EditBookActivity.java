package com.bookbase.app.library.editBook;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bookbase.app.R;

public class EditBookActivity extends AppCompatActivity {

    FragmentPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        final ViewPager addBooksPager = (ViewPager) findViewById(R.id.addBookPager);
        viewPagerAdapter = new AddBookPagerAdapter(getSupportFragmentManager());
        addBooksPager.setAdapter(viewPagerAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Edit Book");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        TabLayout tabs = (TabLayout) findViewById(R.id.tab_slider);
        tabs.setupWithViewPager(addBooksPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    public static class AddBookPagerAdapter extends FragmentPagerAdapter{

        private static int NUM_ITEMS = 2;
        private static Fragment BASIC_DETAILS_PAGE;
        private static Fragment ADVANCED_DETAILS_PAGE;

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
                    BASIC_DETAILS_PAGE = EditBooksFragmentBasic.newInstance(1, "Basic");
                    return BASIC_DETAILS_PAGE;
                case 1:
                    ADVANCED_DETAILS_PAGE = EditBooksFragmentAdvanced.newInstance(2, "Advanced");
                    return ADVANCED_DETAILS_PAGE;
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
