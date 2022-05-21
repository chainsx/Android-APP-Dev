package com.example.ggnews;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    static final int NUM_ITEMS = 4;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private String[] strings = new String[]{"A","B","C","D"};

    private ListView lvNewsList;
    private List<News> newsData;
    private int page = 1;
    private int mCurrentColIndex = 0;
    private NewsAdapter adapter;
    private int[] mCols = new int[]{5,
            7, 8,
            10, 11};
    private RefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

        swipe = (RefreshLayout)findViewById(R.id.swipe);

        swipe.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        page=1;
                        initData();
                        swipe.setRefreshing(false);
                    }
                });
        swipe.setOnLoadListener(new RefreshLayout.OnLoadListener() {

            @Override
            public void onLoad() {
                page=page+1;

                refreshData(page);

                swipe.setLoading(false);
            }
        });
        fragmentList.add(new A());
        fragmentList.add(new B());
        fragmentList.add(new C());
        fragmentList.add(new D());
        initViewtab();
    }

    private void initView() {
        lvNewsList = findViewById(R.id.lv_news_list);

        lvNewsList.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView,
                                        View view, int i, long l) {

                    Intent intent = new Intent(MainActivity.this,
                            Detail.class);

                    News news = adapter.getItem(i);
                    intent.putExtra(Constants.NEWS_DETAIL_URL_KEY,
                            news.getContentUrl());

                    startActivity(intent);
                }
        });
    }

    private okhttp3.Callback callback = new okhttp3.Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
//            Log.e(TAG, "Failed to connect server!");
            e.printStackTrace();
        }

        @Override
        public void onResponse(Call call, Response response)
                throws IOException {
            if (response.isSuccessful()) {
                final String body = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        Type jsonType =
                                new TypeToken<BaseResponse<List<News>>>() {}.getType();
                        BaseResponse<List<News>> newsListResponse =
                                gson.fromJson(body, jsonType);
                        for (News news:newsListResponse.getData()) {
                            adapter.add(news);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            } else {
            }
        }
    };

    private void initData() {
        refreshData(page);

        newsData = new ArrayList<>();

        adapter = new NewsAdapter(MainActivity.this,
                R.layout.list_item, newsData);
        lvNewsList.setAdapter(adapter);
    }

    private void refreshData(final int page) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                NewsRequest requestObj = new NewsRequest();

                requestObj.setCol(mCols[mCurrentColIndex]);
                requestObj.setNum(Constants.NEWS_NUM);
                requestObj.setPage(page);
                String urlParams = requestObj.toString();

                Request request = new Request.Builder()
                        .url(Constants.GENERAL_NEWS_URL + urlParams)
                        .get().build();
                try {
                    OkHttpClient client = new OkHttpClient();
                    client.newCall(request).enqueue(callback);
                } catch (NetworkOnMainThreadException ex) {

                    ex.printStackTrace();
                }
            }
        }).start();
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