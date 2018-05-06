package com.bookbase.app.mainscreen;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.bookbase.app.R;
import com.bookbase.app.library.BooksFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeScreen extends AppCompatActivity {

    // TODO: This needs to be removed but will require rewrite of Converters class to removed dependency on this.
    public static Context context;

    @BindView(R.id.navigation_drawer) DrawerLayout drawer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_title) TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        
        // Setup toolbar.
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Insert fragment.
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.content_frame, BooksFragment.newInstance())
                .commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}