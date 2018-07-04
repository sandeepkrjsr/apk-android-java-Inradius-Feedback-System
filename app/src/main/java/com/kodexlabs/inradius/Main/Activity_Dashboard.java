package com.kodexlabs.inradius.Main;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.kodexlabs.inradius.General.Fragment_General;
import com.kodexlabs.inradius.R;

import java.lang.reflect.Field;

public class Activity_Dashboard extends AppCompatActivity {

    private Activity_Dashboard.SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private BottomNavigationView bottomNavigation;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mSectionsPagerAdapter = new Activity_Dashboard.SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.beginFakeDrag();
        bottomNavigation = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.navbar);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();
                switch (id){
                    case R.id.feedback:
                        fragmentChange(0);
                        break;
                    case R.id.discussion:
                        fragmentChange(1);
                        break;
                    case R.id.subscriptions:
                        fragmentChange(2);
                        break;
                    case R.id.profile:
                        fragmentChange(3);
                        break;
                }
                return true;
            }
        });
        disableShiftMode(bottomNavigation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                break;
            case R.id.item2:
                break;
            case R.id.item3:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Fragment_General tab0 = new Fragment_General();
                    return tab0;
                case 1:
                    Fragment_Dummy tab1 = new Fragment_Dummy();
                    return tab1;
                case 2:
                    Fragment_Dummy tab2 = new Fragment_Dummy();
                    return tab2;
                case 3:
                    Fragment_Dummy tab3 = new Fragment_Dummy();
                    return tab3;
                default:
                return null;
            }
        }
        @Override
        public int getCount() {
            return 4;
        }
    }
    void fragmentChange(int position) {
        mViewPager.setCurrentItem(position, true);
    }
}
