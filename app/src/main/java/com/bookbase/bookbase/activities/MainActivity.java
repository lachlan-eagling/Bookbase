package com.bookbase.bookbase.activities;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bookbase.bookbase.R;

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
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){ selectItem(position); }
    }

    private void selectItem(int position){

    }

    @Override
    public void setTitle(CharSequence title){

    }
}