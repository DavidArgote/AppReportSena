package com.davidargote.appreportsena.control;

import android.content.Intent;
import android.os.Bundle;

import com.davidargote.appreportsena.R;
import com.davidargote.appreportsena.fragments.AboutFragment;
import com.davidargote.appreportsena.fragments.ListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private ListFragment listFragment;
    private AboutFragment aboutFragment;

    private FragmentTransaction ft;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_camera:
                    Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_list:

                    ft = getSupportFragmentManager().beginTransaction();
                    listFragment = new ListFragment();

                    ft.replace(R.id.consContainer, listFragment, null).commit();

                    return true;
                case R.id.navigation_about:

                    ft = getSupportFragmentManager().beginTransaction();
                    aboutFragment = new AboutFragment();

                    ft.replace(R.id.consContainer, aboutFragment, null).commit();


                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ft = getSupportFragmentManager().beginTransaction();
        listFragment = new ListFragment();
        ft.replace(R.id.consContainer, listFragment, null).commit();

    }


}
