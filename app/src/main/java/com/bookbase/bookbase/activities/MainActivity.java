package com.bookbase.bookbase.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bookbase.bookbase.R;
import com.bookbase.bookbase.fragments.AboutFragment;
import com.bookbase.bookbase.fragments.BooksFragment;
import com.bookbase.bookbase.fragments.SettingsFragment;
import com.bookbase.bookbase.fragments.StatsFragment;
import com.bookbase.bookbase.fragments.WishlistFragment;

public class MainActivity extends AppCompatActivity {

    private String[] menuArray;
    private DrawerLayout navDrawerLayout;
    private ListView navDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuArray = getResources().getStringArray(R.array.manu_array);
        navDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
        navDrawerList = (ListView) findViewById(R.id.drawer);

        navDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.nav_drawer_item, menuArray));
        navDrawerList.setOnItemClickListener(new DrawerItemClickListener());

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){

            switch(position){
                case 0:
                    selectItem(new BooksFragment(), position);
                    break;
                case 1:
                    selectItem(new StatsFragment(), position);
                    break;
                case 2:
                    selectItem(new WishlistFragment(), position);
                    break;
                case 3:
                    selectItem(new SettingsFragment(), position);
                    break;
                case 4:
                    selectItem(new AboutFragment(), position);
                    break;
                default:
                    break;

            }
        }
    }

    private void selectItem(Fragment fragment, int position){

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        navDrawerList.setItemChecked(position, true);
        setTitle(menuArray[position]);
        navDrawerLayout.closeDrawer(navDrawerList);
    }

}