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
import android.widget.EditText;
import android.widget.Toast;

import com.bookbase.app.R;
import com.bookbase.app.database.AppDatabase;
import com.bookbase.app.fragments.AddBooksFragmentAdvanced;
import com.bookbase.app.fragments.AddBooksFragmentBasic;
import com.bookbase.app.model.entity.Author;
import com.bookbase.app.model.entity.Book;
import com.bookbase.app.model.entity.Genre;

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
        Fragment basicFragment = AddBookPagerAdapter.BASIC_DETAILS_PAGE;
        boolean mandatoryDetailsComplete = true;

        EditText title = (EditText) findViewById(R.id.add_book_title_data);
        EditText author = (EditText) findViewById(R.id.add_book_author_data);
        EditText description = (EditText) findViewById(R.id.add_book_description_data);
        EditText genre = (EditText) findViewById(R.id.add_book_genre_data);

        if(title.getText().toString().trim().isEmpty()){
            title.setError("Title is required!");
            mandatoryDetailsComplete = false;
        }

        if(author.getText().toString().trim().isEmpty()){
            author.setError("Author is required!");
            mandatoryDetailsComplete = false;
        }

        if(description.getText().toString().trim().isEmpty()){
            description.setError("Description is required!");
            mandatoryDetailsComplete = false;
        }

        if(genre.getText().toString().trim().isEmpty()){
            genre.setError("Genre is required!");
            mandatoryDetailsComplete = false;
        }

        if(mandatoryDetailsComplete){
            final Book book = new Book(title.getText().toString(), new Author(author.getText().toString(), ""), description.getText().toString(), new Genre());
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    addBook(book);
//                }
//            }).run();
            finish();
            return true;
        } else{
            Toast.makeText(this, "Missing manadtory fields!", Toast.LENGTH_LONG).show();
            return  false;
        }
        //return true;
    }

    private synchronized void addBook(Book book){
        AppDatabase db = AppDatabase.getDatabase(this);
        db.bookDao().insert(book);
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
                    BASIC_DETAILS_PAGE = AddBooksFragmentBasic.newInstance(1, "Basic");
                    return BASIC_DETAILS_PAGE;
                case 1:
                    ADVANCED_DETAILS_PAGE = AddBooksFragmentAdvanced.newInstance(2, "Advanced");
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
