package com.example.tabtest;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    static final int NUM_ITEMS = 4;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private String[] strings = new String[]{Constants.getAllNewsPath()[1],Constants.getGeneralNewsPath1()[1],Constants.getGeneralNewsPath2()[1],Constants.getGeneralNewsPath3()[1]};

    private ListView lvNewsList;
    private List<News> newsData;
    private int page = 1;
    private int mCurrentColIndex = 0;
    private NewsAdapter adapter;
    private int[] mCols = new int[]{5,
            7, 8,
            10, 11};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentList.add(new A());
        fragmentList.add(new B());
        fragmentList.add(new C());
        fragmentList.add(new D());
        initViewtab();
    }

    public class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return strings[position];
        }
    }
    public void test(){
        Toast.makeText(MainActivity.this, "提示的内容", Toast.LENGTH_LONG).show();
    }
    private void initViewtab(){
        TabLayout tab_layout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        MyAdapter fragmentAdapter = new  MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);
        tab_layout.setupWithViewPager(viewPager);
    }
}